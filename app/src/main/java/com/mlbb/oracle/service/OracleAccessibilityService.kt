package com.mlbb.oracle.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import com.mlbb.oracle.capture.FrameProcessor
import com.mlbb.oracle.ai.GameRecognizer
import kotlinx.coroutines.*

class OracleAccessibilityService : AccessibilityService() {

    companion object {
        private const val TAG = "OracleAccessibility"
        private const val CAPTURE_INTERVAL_MS = 500L // 2 FPS
        private var instance: OracleAccessibilityService? = null
        fun getInstance(): OracleAccessibilityService? = instance
    }

    private var captureJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private lateinit var frameProcessor: FrameProcessor
    private lateinit var gameRecognizer: GameRecognizer
    private var isCapturing = false

    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this

        // Configure service
        serviceInfo = serviceInfo.apply {
            eventTypes = AccessibilityEvent.TYPES_ALL_MASK
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS or
                    AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
            notificationTimeout = 100
        }

        // Initialize processors
        frameProcessor = FrameProcessor(this)
        gameRecognizer = GameRecognizer(this)

        Log.d(TAG, "Oracle Accessibility Service connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // We don't need accessibility events for screen capture
        // The service runs in background capturing frames
    }

    override fun onInterrupt() {
        Log.d(TAG, "Oracle Accessibility Service interrupted")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopCapture()
        scope.cancel()
        instance = null
        Log.d(TAG, "Oracle Accessibility Service destroyed")
    }

    fun startCapture() {
        if (isCapturing) return

        isCapturing = true
        captureJob = scope.launch {
            while (isActive && isCapturing) {
                try {
                    captureFrame()
                } catch (e: Exception) {
                    Log.e(TAG, "Frame capture error", e)
                }
                delay(CAPTURE_INTERVAL_MS)
            }
        }
        Log.d(TAG, "Frame capture started")
    }

    fun stopCapture() {
        isCapturing = false
        captureJob?.cancel()
        captureJob = null
        Log.d(TAG, "Frame capture stopped")
    }

    private fun captureFrame() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11+ takeScreenshot API
            val display = display
            if (display != null) {
                takeScreenshot(
                    display.displayId,
                    mainExecutor,
                    object : TakeScreenshotCallback {
                        override fun onSuccess(result: ScreenshotResult) {
                            val bitmap = Bitmap.wrapHardwareBuffer(
                                result.hardwareBuffer,
                                result.colorSpace
                            )
                            result.hardwareBuffer.close()

                            bitmap?.let { processFrame(it) }
                        }

                        override fun onFailure(errorCode: Int) {
                            Log.e(TAG, "Screenshot failed with code: $errorCode")
                        }
                    }
                )
            }
        } else {
            // Fallback for older Android versions
            captureWithWindowManager()
        }
    }

    private fun captureWithWindowManager() {
        try {
            val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            val display = windowManager.getDefaultDisplay()
            val metrics = android.util.DisplayMetrics()
            display.getRealMetrics(metrics)

            val bitmap = Bitmap.createBitmap(
                metrics.widthPixels,
                metrics.heightPixels,
                Bitmap.Config.ARGB_8888
            )

            val canvas = android.graphics.Canvas(bitmap)
            val paint = android.graphics.Paint()
            paint.color = android.graphics.Color.TRANSPARENT
            canvas.drawPaint(paint)

            // This is a simplified version - real implementation would use
            // SurfaceFlinger or MediaProjection for actual screen capture
            processFrame(bitmap)
        } catch (e: Exception) {
            Log.e(TAG, "Window capture failed", e)
        }
    }

    private fun processFrame(bitmap: Bitmap) {
        scope.launch(Dispatchers.Default) {
            try {
                // Preprocess frame
                val processedFrame = frameProcessor.process(bitmap)

                // Run AI recognition
                val result = gameRecognizer.recognize(processedFrame)

                // Broadcast result
                sendBroadcast(Intent("com.mlbb.oracle.FRAME_PROCESSED").apply {
                    putExtra("gold", result.gold)
                    putExtra("kills", result.kills)
                    putExtra("deaths", result.deaths)
                    putExtra("assists", result.assists)
                    putExtra("items", result.items.toTypedArray())
                    putExtra("hero_id", result.heroId)
                    putExtra("game_time", result.gameTime)
                    setPackage(packageName)
                })

                // Recycle bitmap
                bitmap.recycle()
            } catch (e: Exception) {
                Log.e(TAG, "Frame processing error", e)
            }
        }
    }
}
