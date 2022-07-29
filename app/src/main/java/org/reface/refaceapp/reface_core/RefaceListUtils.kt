package org.reface.refaceapp.reface_core

import android.graphics.Bitmap

object RefaceListUtils {

    fun List<Bitmap>.redoAvailableReface(currentIndexReface: Int): Boolean{
        if(currentIndexReface in 0 until size && currentIndexReface + 1 < size){
            return true
        }
        
        return false
    }

    fun List<Bitmap>.undoAvailableReface(currentIndexReface: Int): Boolean{
        if(currentIndexReface in 1 until size  + 1){
            return true
        }
        
        return false
    }

    

}