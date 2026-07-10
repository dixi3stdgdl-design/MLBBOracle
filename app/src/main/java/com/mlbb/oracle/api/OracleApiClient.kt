package com.mlbb.oracle.api

import android.util.Log
import kotlinx.coroutines.*
import okhttp3.*
import okio.ByteString
import okio.ByteString.Companion.toByteString
import org.json.JSONObject
import java.util.concurrent.TimeUnit
import kotlin.math.min
import kotlin.math.pow

/**
 * Backend API client for MLBB Oracle.
 * Handles WebSocket live streaming and REST calls for match data.
 */
class OracleApiClient(
    private val baseUrl: String = "https://api.mlbboracle.com",
    private val wsUrl: String = "wss://api.mlbboracle.com/ws"
) {

    companion object {
        private const val TAG = "OracleApiClient"
        private const val MAX_RETRY_DELAY_MS = 30_000L
        private const val INITIAL_RETRY_DELAY_MS = 1_000L
        private const val PING_INTERVAL_MS = 15_000L
    }

    interface Callback {
        fun onConnected()
        fun onDisconnected(reason: String)
        fun onGameDataReceived(data: JSONObject)
        fun onError(error: String)
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .pingInterval(PING_INTERVAL_MS, TimeUnit.MILLISECONDS)
        .build()

    private var webSocket: WebSocket? = null
    private var callback: Callback? = null
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var retryCount = 0
    private var isConnecting = false
    private var isIntentionalDisconnect = false
    private var reconnectJob: Job? = null

    // Connection state
    var isConnected: Boolean = false
        private set

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    // ===== WebSocket =====

    fun connect() {
        if (isConnecting || isConnected) {
            Log.d(TAG, "Already connected or connecting")
            return
        }

        isIntentionalDisconnect = false
        isConnecting = true

        val request = Request.Builder()
            .url(wsUrl)
            .header("User-Agent", "MLBBOracle/1.0")
            .build()

        webSocket = client.newWebSocket(request, createWebSocketListener())
        Log.d(TAG, "Connecting to $wsUrl")
    }

    fun disconnect() {
        isIntentionalDisconnect = true
        reconnectJob?.cancel()
        retryCount = 0

        webSocket?.close(1000, "Client disconnect")
        webSocket = null
        isConnected = false
        isConnecting = false
        Log.d(TAG, "Disconnected")
    }

    private fun createWebSocketListener() = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d(TAG, "WebSocket connected")
            isConnected = true
            isConnecting = false
            retryCount = 0
            scope.launch(Dispatchers.Main) {
                callback?.onConnected()
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            try {
                val json = JSONObject(text)
                scope.launch(Dispatchers.Main) {
                    callback?.onGameDataReceived(json)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to parse message: $text", e)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            onMessage(webSocket, bytes.utf8())
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG, "WebSocket closing: $code $reason")
            webSocket.close(code, reason)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG, "WebSocket closed: $code $reason")
            handleDisconnect("Closed: $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e(TAG, "WebSocket failure: ${t.message}", t)
            handleDisconnect("Error: ${t.message}")
        }
    }

    private fun handleDisconnect(reason: String) {
        isConnected = false
        isConnecting = false

        scope.launch(Dispatchers.Main) {
            callback?.onDisconnected(reason)
        }

        if (!isIntentionalDisconnect) {
            scheduleReconnect()
        }
    }

    private fun scheduleReconnect() {
        reconnectJob?.cancel()
        val delay = min(
            INITIAL_RETRY_DELAY_MS * 2.0.pow(retryCount.toDouble()).toLong(),
            MAX_RETRY_DELAY_MS
        )
        retryCount++

        Log.d(TAG, "Reconnecting in ${delay}ms (attempt $retryCount)")

        reconnectJob = scope.launch {
            delay(delay)
            connect()
        }
    }

    fun sendMessage(json: JSONObject) {
        webSocket?.send(json.toString())
    }

    fun sendGameFrame(data: JSONObject) {
        if (!isConnected) return

        val message = JSONObject().apply {
            put("type", "frame_data")
            put("payload", data)
            put("timestamp", System.currentTimeMillis())
        }
        sendMessage(message)
    }

    // ===== REST API =====

    suspend fun saveMatch(matchData: JSONObject): Result<JSONObject> = withContext(Dispatchers.IO) {
        try {
            val body = RequestBody.create(
                MediaType.parse("application/json"),
                matchData.toString()
            )

            val request = Request.Builder()
                .url("$baseUrl/api/matches")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body()?.string()

            if (response.isSuccessful && responseBody != null) {
                Result.success(JSONObject(responseBody))
            } else {
                Result.failure(Exception("HTTP ${response.code}: $responseBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save match", e)
            Result.failure(e)
        }
    }

    suspend fun fetchStats(heroName: String): Result<JSONObject> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("$baseUrl/api/stats/$heroName")
                .get()
                .addHeader("Accept", "application/json")
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body()?.string()

            if (response.isSuccessful && responseBody != null) {
                Result.success(JSONObject(responseBody))
            } else {
                Result.failure(Exception("HTTP ${response.code}: $responseBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch stats for $heroName", e)
            Result.failure(e)
        }
    }

    suspend fun fetchMatchHistory(playerId: String): Result<JSONObject> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("$baseUrl/api/history/$playerId")
                .get()
                .addHeader("Accept", "application/json")
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body()?.string()

            if (response.isSuccessful && responseBody != null) {
                Result.success(JSONObject(responseBody))
            } else {
                Result.failure(Exception("HTTP ${response.code}: $responseBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to fetch match history", e)
            Result.failure(e)
        }
    }

    suspend fun reportBug(report: JSONObject): Result<JSONObject> = withContext(Dispatchers.IO) {
        try {
            val body = RequestBody.create(
                MediaType.parse("application/json"),
                report.toString()
            )

            val request = Request.Builder()
                .url("$baseUrl/api/report")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body()?.string()

            if (response.isSuccessful && responseBody != null) {
                Result.success(JSONObject(responseBody))
            } else {
                Result.failure(Exception("HTTP ${response.code}: $responseBody"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to submit report", e)
            Result.failure(e)
        }
    }

    fun destroy() {
        disconnect()
        scope.cancel()
        client.dispatcher().executorService().shutdown()
        client.connectionPool().evictAll()
    }
}
