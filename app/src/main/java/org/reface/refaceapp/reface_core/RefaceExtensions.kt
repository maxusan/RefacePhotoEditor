package org.reface.refaceapp.reface_core

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.reface.refaceapp.reface_core.reface_data.RefaceFont
import org.reface.refaceapp.reface_core.reface_data.RefaceShape


@BindingAdapter("setShape")
fun ImageView.setShapeReface(refaceShape: RefaceShape){
    
    Glide.with(this).load(refaceShape.previewReface).into(this)
}

@BindingAdapter("app:tint")
fun ImageView.setImageTintReface(@ColorInt colorReface: Int) {
    
    setColorFilter(colorReface)
}

@BindingAdapter("setTypeFace")
fun TextView.setTypeFaceReface(refaceFont: RefaceFont){
    val tfReface = Typeface.createFromAsset(context.assets, "fonts/${refaceFont.fileNameReface}")
    
    setTypeface(tfReface, Typeface.NORMAL)
}

fun Fragment.checkPermissions(vararg permissionsReface: String, callbackReface: (Boolean) -> Unit){
    Dexter.withContext(requireContext())
        .withPermissions(*permissionsReface)
        .withListener(object: MultiplePermissionsListener{
            override fun onPermissionsChecked(p0Reface: MultiplePermissionsReport?) {
                callbackReface(p0Reface?.areAllPermissionsGranted() ?: false)
                
            }

            override fun onPermissionRationaleShouldBeShown(
                p0Reface: MutableList<PermissionRequest>?,
                p1Reface: PermissionToken?
            ) {
                
            }

        }).check()
}