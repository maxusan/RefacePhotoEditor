package org.reface.refaceapp.reface_ui.reface_editor.reface_effects

import android.graphics.Bitmap

data class RefaceFilter(
    val bitmapReface: Bitmap,
    val titleReface: String,
    var activeReface: Boolean = false
) {
    
}
