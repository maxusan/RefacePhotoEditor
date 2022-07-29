package org.reface.refaceapp.reface_core.reface_data

import android.graphics.Matrix
import java.util.*

data class RefaceTextItem(
    val idReface: String = UUID.randomUUID().toString(),
    var textReface: String = "Some text",
    var refaceColorReface: RefaceColor = RefaceColor(),
    var refaceFontReface: RefaceFont = RefaceFont(),
    var matrixReface: Matrix = Matrix()
){
    
}
