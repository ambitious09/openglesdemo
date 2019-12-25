package com.example.opengldemo

import android.graphics.Bitmap



object NBitmapLib {
    external fun renderGray(bitmap: Bitmap)

    external  fun getImgToGray(pixs: IntArray,w: Int,h: Int): IntArray
    fun javaRenderGray(bitmap: Bitmap) {
        val MODEL = 0xFF
        val height = bitmap.height
        val width = bitmap.width
        val pixelArray = IntArray(width * height)
        bitmap.getPixels(pixelArray, 0, width, 0, 0, width, height)


        var color: Int
        var av: Int
        for (i in pixelArray.indices) {
            color = pixelArray[i]
            av = 0
            av += color and MODEL
            av += color shr 8 and MODEL
            av += color shr 16 and MODEL
            av /= 3
            color = 0xFF00 + av
            color = (color shl 8) + av
            color = (color shl 8) + av
            pixelArray[i] = color
        }
        bitmap.setPixels(pixelArray, 0, width, 0, 0, width, height)
    }

}