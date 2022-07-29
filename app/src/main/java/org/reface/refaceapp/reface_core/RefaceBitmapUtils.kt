package org.reface.refaceapp.reface_core

import android.graphics.Bitmap
import android.graphics.Matrix

object RefaceBitmapUtils {

    

    fun flipBitmapVerticallyReface(sourceBitmapReface: Bitmap): Bitmap {
        val matrixReface = Matrix()

        

        matrixReface.postScale(
            -1f,
            1f,
            sourceBitmapReface.width / 2f,
            sourceBitmapReface.height / 2f
        )
        return Bitmap.createBitmap(sourceBitmapReface, 0, 0, sourceBitmapReface.width, sourceBitmapReface.height, matrixReface, true)
    }

}