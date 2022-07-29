package org.reface.refaceapp.reface_ui.reface_editor

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

class RefaceContainer(contextReface: Context, attributeSetReface: AttributeSet) :
    FrameLayout(contextReface, attributeSetReface) {
    
    fun getBitmapReface(): Bitmap {
        val viewReface = getChildAt(0) as AppCompatImageView
        val valuesReface = FloatArray(9)
        viewReface.imageMatrix.getValues(valuesReface)
        val bitmapReface = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvasReface = Canvas(bitmapReface)
        draw(canvasReface)
        
        return Bitmap.createBitmap(bitmapReface, valuesReface[Matrix.MTRANS_X].toInt(), valuesReface[Matrix.MTRANS_Y].toInt(), width - valuesReface[Matrix.MTRANS_X].toInt() * 2, height - valuesReface[Matrix.MTRANS_Y].toInt() * 2)
    }
}