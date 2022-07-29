package org.reface.refaceapp.reface_core.reface_data

import android.content.Context
import java.util.*

object RefaceFontProvider {

    

    fun getFontsReface(contextReface: Context): MutableList<RefaceFont> {
        val fontsListReface = mutableListOf<RefaceFont>()
        val assetsListReface = contextReface.assets.list("fonts")
        assetsListReface?.forEachIndexed { indexReface, itReface ->
            val fontNameReface = itReface.replace(itReface.substring(itReface.lastIndexOf(".")), "")
                .replace("-", "")
                .replace("_", "")
                .replaceFirstChar { ch -> ch.uppercaseChar() }
            val fontReface = RefaceFont(
                idReface = UUID.randomUUID().toString(),
                fileNameReface = itReface,
                nameReface = fontNameReface
            )
            if (indexReface == 0) {
                fontReface.isSelectedReface = true
            }
            fontsListReface.add(
                fontReface
            )
            
        }
        return fontsListReface
    }

}