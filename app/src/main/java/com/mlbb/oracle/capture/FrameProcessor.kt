package com.mlbb.oracle.capture

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Rect

/**
 * Preprocesses captured frames for AI recognition.
 * Crops regions of interest and resizes for model input.
 */
class FrameProcessor(private val context: Context) {

    companion object {
        const val MODEL_INPUT_SIZE = 320 // YOLO input size

        // MLBB screen regions (landscape mode, 2400x1080)
        // These are approximate - needs calibration per device
        val HERO_PORTRAIT_REGION = Rect(0, 800, 400, 1080) // Bottom-left
        val ITEM_SLOTS_REGION = Rect(1400, 900, 2400, 1080) // Bottom-right
        val GOLD_DISPLAY_REGION = Rect(1800, 0, 2400, 150) // Top-right
        val MINIMAP_REGION = Rect(0, 0, 400, 400) // Top-left
        val TIMER_REGION = Rect(1000, 0, 1400, 100) // Top-center
        val KDA_REGION = Rect(500, 0, 900, 100) // Top area
    }

    private val cropPaint = Paint().apply {
        isAntiAlias = true
    }

    data class ProcessedFrame(
        val original: Bitmap,
        val heroPortrait: Bitmap?,
        val itemSlots: Bitmap?,
        val goldDisplay: Bitmap?,
        val minimap: Bitmap?,
        val timer: Bitmap?,
        val kda: Bitmap?,
        val timestamp: Long = System.currentTimeMillis()
    )

    /**
     * Process a captured frame and extract regions of interest.
     */
    fun process(bitmap: Bitmap): ProcessedFrame {
        val width = bitmap.width
        val height = bitmap.height

        // Scale regions based on actual screen size
        val scaleX = width.toFloat() / 2400f
        val scaleY = height.toFloat() / 1080f

        return ProcessedFrame(
            original = bitmap,
            heroPortrait = cropRegion(bitmap, HERO_PORTRAIT_REGION, scaleX, scaleY),
            itemSlots = cropRegion(bitmap, ITEM_SLOTS_REGION, scaleX, scaleY),
            goldDisplay = cropRegion(bitmap, GOLD_DISPLAY_REGION, scaleX, scaleY),
            minimap = cropRegion(bitmap, MINIMAP_REGION, scaleX, scaleY),
            timer = cropRegion(bitmap, TIMER_REGION, scaleX, scaleY),
            kda = cropRegion(bitmap, KDA_REGION, scaleX, scaleY)
        )
    }

    /**
     * Crop a specific region from the bitmap.
     */
    private fun cropRegion(
        source: Bitmap,
        region: Rect,
        scaleX: Float,
        scaleY: Float
    ): Bitmap? {
        return try {
            val left = (region.left * scaleX).toInt().coerceIn(0, source.width)
            val top = (region.top * scaleY).toInt().coerceIn(0, source.height)
            val right = (region.right * scaleX).toInt().coerceIn(0, source.width)
            val bottom = (region.bottom * scaleY).toInt().coerceIn(0, source.height)

            if (right <= left || bottom <= top) return null

            val width = right - left
            val height = bottom - top

            Bitmap.createBitmap(source, left, top, width, height)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Resize bitmap to model input size while maintaining aspect ratio.
     */
    fun resizeForModel(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        val scale = MODEL_INPUT_SIZE.toFloat() / maxOf(bitmap.width, bitmap.height)
        matrix.postScale(scale, scale)

        return Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )
    }

    /**
     * Convert bitmap to float array for TFLite input.
     */
    fun bitmapToFloatArray(bitmap: Bitmap): FloatArray {
        val resized = resizeForModel(bitmap)
        val width = resized.width
        val height = resized.height
        val pixels = IntArray(width * height)
        resized.getPixels(pixels, 0, width, 0, 0, width, height)

        val floatArray = FloatArray(width * height * 3) // RGB
        var pixelIndex = 0

        for (i in pixels.indices) {
            val pixel = pixels[i]
            floatArray[pixelIndex++] = ((pixel shr 16) and 0xFF) / 255.0f // R
            floatArray[pixelIndex++] = ((pixel shr 8) and 0xFF) / 255.0f  // G
            floatArray[pixelIndex++] = (pixel and 0xFF) / 255.0f          // B
        }

        resized.recycle()
        return floatArray
    }
}
