package org.reface.refaceapp.reface_core.reface_data

import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.reface.refaceapp.BR
import org.reface.refaceapp.R
import java.util.*

data class RefaceShape(
    val idReface: String = UUID.randomUUID().toString(),
    @DrawableRes val previewReface: Int = 0,
    @DrawableRes val maskReface: Int = 0
) : BaseObservable() {

    

    @Bindable
    var shapeSelectedReface: Boolean = false
        set(value) {
            field = value
            
            notifyPropertyChanged(BR.shapeSelectedReface)
        }

    companion object {

        

        fun getShapesListReface(): List<RefaceShape> {
            val shapesListReface = mutableListOf<RefaceShape>()
            val firstShapeReface = RefaceShape(
                previewReface = R.drawable.shape_1_preview,
                maskReface = R.drawable.shape_1
            )
            firstShapeReface.shapeSelectedReface = true
            shapesListReface.add(
                firstShapeReface
            )
            
            shapesListReface.add(
                RefaceShape(
                    previewReface = R.drawable.shape_2_preview,
                    maskReface = R.drawable.shape_2
                )
            )
            shapesListReface.add(
                RefaceShape(
                    previewReface = R.drawable.shape_3_preview,
                    maskReface = R.drawable.shape_3
                )
            )
            shapesListReface.add(
                RefaceShape(
                    previewReface = R.drawable.shape_4_preview,
                    maskReface = R.drawable.shape_4
                )
            )
            shapesListReface.add(
                RefaceShape(
                    previewReface = R.drawable.shape_5_preview,
                    maskReface = R.drawable.shape_5
                )
            )
            shapesListReface.add(
                RefaceShape(
                    previewReface = R.drawable.shape_6_preview,
                    maskReface = R.drawable.shape_6
                )
            )
            return shapesListReface
        }

    }

}