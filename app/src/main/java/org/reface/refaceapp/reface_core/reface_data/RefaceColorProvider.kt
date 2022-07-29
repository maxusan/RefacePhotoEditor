package org.reface.refaceapp.reface_core.reface_data

import android.content.Context
import androidx.core.content.ContextCompat
import org.reface.refaceapp.R

object RefaceColorProvider {

    

    fun getColorsReface(contextReface: Context): List<RefaceColor> {
        val colorsReface = mutableListOf<RefaceColor>()
        val firstColorReface = RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_1))
        firstColorReface.isSelectedReface = true
        colorsReface.add(
            firstColorReface
        )
        
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_2))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_3))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_4))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_5))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_6))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_7))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_8))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_9))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_10))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_11))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_12))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_13))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_14))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_15))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_16))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_17))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_18))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_19))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_20))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_21))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_22))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_23))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_24))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_25))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_26))
        )
        colorsReface.add(
            RefaceColor(colorReface = ContextCompat.getColor(contextReface, R.color.reface_color_27))
        )
        colorsReface.add(
            RefaceColor(
                colorReface = 0
            )
        )
        return colorsReface
    }
}