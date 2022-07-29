package org.reface.refaceapp.reface_core

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

object RefaceSaveManager {

    private val defaultDirectoryReface = Environment.DIRECTORY_DOWNLOADS + File.separator

    

    fun saveImageReface(contextReface: Context, bitmapReface: Bitmap): String {
        var pathReface: String = ""
        try {
            
            val downloadsDirReface = Environment.getExternalStoragePublicDirectory(defaultDirectoryReface)
            val fileNameReface = "Image_${UUID.randomUUID().toString()}.jpg"
            val imageReface = File(downloadsDirReface, fileNameReface)
            pathReface = imageReface.path
            val streamReface = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contextReface.contentResolver?.let { resolverReface ->
                    val valuesReface = getValues(defaultDirectoryReface, fileNameReface)
                    resolverReface.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, valuesReface)?.let {
                        resolverReface.openOutputStream(it)
                    }
                }
            } else {
                val downloadsDirReface = Environment.getExternalStoragePublicDirectory(defaultDirectoryReface)
                val imageReface = File(downloadsDirReface, "Image_${UUID.randomUUID().toString()}.jpg")
                pathReface = imageReface.path
                FileOutputStream(imageReface)
            }
            streamReface?.let { bitmapReface.toFileReface(it) }
            Toast.makeText(contextReface, "Saved", Toast.LENGTH_SHORT).show()

        } catch (eReface: Exception) {
            eReface.printStackTrace()
        }
        return pathReface
    }

    private fun Bitmap.toFileReface(streamReface: OutputStream) {
        
        compress(Bitmap.CompressFormat.JPEG, 100, streamReface)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun getValues(pathReface: String, fileNameReface: String): ContentValues {
        
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileNameReface)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            put(MediaStore.MediaColumns.RELATIVE_PATH, pathReface)
        }
    }
}