package org.reface.refaceapp.reface_ui.reface_editor

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.util.TypedValue
import android.widget.SeekBar
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.uvstudio.him.photofilterlibrary.PhotoFilter
import org.reface.refaceapp.reface_ui.reface_editor.reface_effects.RefaceFilter

@BindingAdapter("setImageBitmap")
fun ShapeableImageView.setImageBitmap(bitmapReface: Bitmap) {
    Glide.with(this).load(bitmapReface).into(this)
    
}

fun getFilter(conteRefacext: Context, bitmapReface: Bitmap): MutableList<RefaceFilter> {
    val photoFilterReface = PhotoFilter()

    return mutableListOf(
        RefaceFilter(
            bitmapReface,
            "Original",
            true
        ),
        RefaceFilter(
            photoFilterReface.one(conteRefacext, bitmapReface),
            "Negative",
            false
        ),
        RefaceFilter(
            photoFilterReface.two(conteRefacext, bitmapReface),
            "BW",
            false
        ),
        RefaceFilter(
            photoFilterReface.three(conteRefacext, bitmapReface),
            "Blue",
            false
        ),
        RefaceFilter(
            photoFilterReface.four(conteRefacext, bitmapReface),
            "Green",
            false
        ),
        RefaceFilter(
            photoFilterReface.five(conteRefacext, bitmapReface),
            "Saturate",
            false
        ),
        RefaceFilter(
            photoFilterReface.six(conteRefacext, bitmapReface),
            "Lemon",
            false
        ),
        RefaceFilter(
            photoFilterReface.seven(conteRefacext, bitmapReface),
            "Sky",
            false
        ),
        RefaceFilter(
            photoFilterReface.eight(conteRefacext, bitmapReface),
            "Natural",
            false
        ),
        RefaceFilter(
            photoFilterReface.nine(conteRefacext, bitmapReface),
            "Dark",
            false
        ),
        RefaceFilter(
            photoFilterReface.ten(conteRefacext, bitmapReface),
            "Lilac",
            false
        ),
        RefaceFilter(
            photoFilterReface.eleven(conteRefacext, bitmapReface),
            "Bright",
            false
        ),
        RefaceFilter(
            photoFilterReface.twelve(conteRefacext, bitmapReface),
            "Retro",
            false
        ),
        RefaceFilter(
            photoFilterReface.thirteen(conteRefacext, bitmapReface),
            "Grain",
            false
        ),
        RefaceFilter(
            photoFilterReface.fourteen(conteRefacext, bitmapReface),
            "Shine",
            false
        ),
        RefaceFilter(
            photoFilterReface.fifteen(conteRefacext, bitmapReface),
            "Radiance",
            false
        ),
        RefaceFilter(
            photoFilterReface.sixteen(conteRefacext, bitmapReface),
            "Warm",
            false
        )
    )
}

abstract class OnSeekCustomChangeListener : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(p0Reface: SeekBar?, p1Reface: Int, p2Reface: Boolean) {
        
    }

    override fun onStartTrackingTouch(p0Reface: SeekBar?) {
        
    }

    override fun onStopTrackingTouch(p0Reface: SeekBar?) {
        
    }

}


val Number.toPxReface get() = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics)