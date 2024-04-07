package com.lernib.pagescanner.ui.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.lernib.pagescanner.R


object BitmapUtils {
    /**
     * Will return null if resource does not exist. It should
     * be safe to use `!!`.
     */
    fun getDefaultBitmap(context: Context): Bitmap {
        val d = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background)
        val bit = Bitmap.createBitmap(
            d!!.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bit)

        d.setBounds(0, 0, canvas.width, canvas.height)
        d.draw(canvas)

        return bit
    }
}
