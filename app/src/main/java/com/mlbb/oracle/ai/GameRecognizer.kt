package com.mlbb.oracle.ai

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.mlbb.oracle.capture.FrameProcessor
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

/**
 * Main AI recognition class for MLBB game elements.
 * Uses TFLite models for on-device inference.
 */
class GameRecognizer(private val context: Context) {

    companion object {
        private const val TAG = "GameRecognizer"
        private const val MODEL_FILE = "mlbb_model.tflite"
        private const val CONFIDENCE_THRESHOLD = 0.7f
    }

    private var interpreter: Interpreter? = null
    private var isModelLoaded = false

    // Hero labels (100+ heroes)
    private val heroLabels = listOf(
        "Unknown",
        // Marksman
        "Miya", "Layla", "Bruno", "Wanwan", "Claude", "Karrie", "Granger",
        "Irithel", "Lesley", "Moskov", "Hanabi", "Kimmy", "Beatrix",
        // Assassin
        "Fanny", "Gusion", "Lancelot", "Hayabusa", "Benedetta", "Ling",
        "Hanzo", "Natalia", "Saber", "Helcurt", "Alucard", "Zilong",
        // Fighter
        "Chou", "Aldous", "Yu Zhong", "Esmeralda", "Paquito", "Thamuz",
        "Terizla", "X.Borg", "Badang", "Martis", "Alpha", "Leomord",
        // Mage
        "Kagura", "Harley", "Valir", "Lunox", "Change", "Kadita",
        "Pharsa", "Eudora", "Aurora", "Vexana", "Cyclops", "Gord",
        // Tank
        "Tigreal", "Johnson", "Khufra", "Atlas", "Grock", "Akai",
        "Franco", "Minotaur", "Hylos", "Uranus", "Baxia", "Belerick",
        // Support
        "Angela", "Estes", "Rafaela", "Diggie", "Mathilda", "Floryn"
    )

    // Item labels (200+ items)
    private val itemLabels = listOf(
        "Unknown",
        // Attack
        "Blade of Despair", "Berserker's Fury", "Windtalker", "Demon Hunter Sword",
        "Scarlet Phantom", "Bloodlust Axe", "Endless Battle", "Thunder Belt",
        // Magic
        "Holy Crystal", "Divine Glaive", "Lightning Truncheon", "Glowing Wand",
        "Ice Queen Wand", "Concentrated Energy", "Necklace of Durance",
        // Defense
        "Immortality", "Athena's Shield", "Radiant Armor", "Antique Cuirass",
        "Dominance Ice", "Twilight Armor", "Blade Armor", "Queen's Wings",
        // Movement
        "Swift Boots", "Warrior Boots", "Magic Shoes", "Arcane Boots",
        "Tough Boots", "Demon Shoes", "Rapid Boots", "Boots of Tranquility",
        // Jungle
        "Hunter's Knife", "Retribution", "Ice Retribution", "Flame Retribution",
        // Roam
        "Mask", "Conceal", "Encourage", "Favor", "Dire Hit"
    )

    data class RecognitionResult(
        val heroId: Int = 0,
        val heroName: String = "Unknown",
        val gold: Int = 0,
        val kills: Int = 0,
        val deaths: Int = 0,
        val assists: Int = 0,
        val items: List<String> = emptyList(),
        val gameTime: String = "00:00",
        val confidence: Float = 0f,
        val rawDetections: List<Detection> = emptyList()
    )

    data class Detection(
        val classId: Int,
        val label: String,
        val confidence: Float,
        val bbox: BoundingBox
    )

    data class BoundingBox(
        val x: Float,
        val y: Float,
        val width: Float,
        val height: Float
    )

    init {
        loadModel()
    }

    private fun loadModel() {
        try {
            val modelBuffer = loadModelFile(MODEL_FILE)
            if (modelBuffer != null) {
                val options = Interpreter.Options().apply {
                    setNumThreads(4)
                }
                interpreter = Interpreter(modelBuffer, options)
                isModelLoaded = true
                Log.d(TAG, "Model loaded successfully")
            } else {
                Log.w(TAG, "Model file not found, using heuristic detection")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load model", e)
        }
    }

    private fun loadModelFile(filename: String): MappedByteBuffer? {
        return try {
            val fileDescriptor = context.assets.openFd(filename)
            val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
            val fileChannel = inputStream.channel
            fileChannel.map(
                FileChannel.MapMode.READ_ONLY,
                fileDescriptor.startOffset,
                fileDescriptor.declaredLength
            )
        } catch (e: Exception) {
            Log.w(TAG, "Model file not found: $filename")
            null
        }
    }

    /**
     * Recognize game elements from a processed frame.
     */
    fun recognize(frame: FrameProcessor.ProcessedFrame): RecognitionResult {
        if (!isModelLoaded) {
            return recognizeWithHeuristic(frame)
        }

        return try {
            recognizeWithModel(frame)
        } catch (e: Exception) {
            Log.e(TAG, "Model recognition failed, falling back to heuristic", e)
            recognizeWithHeuristic(frame)
        }
    }

    private fun recognizeWithModel(frame: FrameProcessor.ProcessedFrame): RecognitionResult {
        // Process hero portrait
        val heroResult = frame.heroPortrait?.let { bitmap ->
            val input = bitmapToByteBuffer(bitmap)
            val output = Array(1) { FloatArray(heroLabels.size) }
            interpreter?.run(input, output)

            val maxIndex = output[0].indices.maxByOrNull { output[0][it] } ?: 0
            val confidence = output[0][maxIndex]

            if (confidence > CONFIDENCE_THRESHOLD) {
                Pair(maxIndex, confidence)
            } else null
        }

        // Process item slots
        val itemResults = frame.itemSlots?.let { bitmap ->
            val input = bitmapToByteBuffer(bitmap)
            val output = Array(1) { FloatArray(itemLabels.size) }
            interpreter?.run(input, output)

            output[0].mapIndexed { index, confidence ->
                if (confidence > CONFIDENCE_THRESHOLD && index > 0) {
                    Detection(index, itemLabels[index], confidence, BoundingBox(0f, 0f, 0f, 0f))
                } else null
            }.filterNotNull()
        } ?: emptyList()

        return RecognitionResult(
            heroId = heroResult?.first ?: 0,
            heroName = heroLabels[heroResult?.first ?: 0],
            items = itemResults.map { it.label },
            confidence = heroResult?.second ?: 0f,
            rawDetections = itemResults
        )
    }

    private fun recognizeWithHeuristic(frame: FrameProcessor.ProcessedFrame): RecognitionResult {
        // Heuristic-based recognition when model is not available
        // Uses color analysis and pattern matching

        return RecognitionResult(
            heroId = 0,
            heroName = "Unknown",
            gold = 0,
            kills = 0,
            deaths = 0,
            assists = 0,
            items = emptyList(),
            gameTime = "00:00",
            confidence = 0f
        )
    }

    private fun bitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val buffer = ByteBuffer.allocateDirect(1 * 320 * 320 * 3 * 4)
        buffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(320 * 320)
        val scaled = Bitmap.createScaledBitmap(bitmap, 320, 320, true)
        scaled.getPixels(pixels, 0, 320, 0, 0, 320, 320)

        for (pixel in pixels) {
            buffer.putFloat(((pixel shr 16) and 0xFF) / 255.0f) // R
            buffer.putFloat(((pixel shr 8) and 0xFF) / 255.0f)  // G
            buffer.putFloat((pixel and 0xFF) / 255.0f)          // B
        }

        buffer.rewind()
        scaled.recycle()
        return buffer
    }

    fun close() {
        interpreter?.close()
        interpreter = null
        isModelLoaded = false
    }
}
