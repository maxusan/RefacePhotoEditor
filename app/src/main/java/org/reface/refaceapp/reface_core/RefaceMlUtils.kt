package org.reface.refaceapp.reface_core

import android.graphics.Bitmap
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions

object RefaceMlUtils {

    

    fun Fragment.runFaceContourDetectionReface(bitmapReface: Bitmap, facesReface: (List<Face>) -> Unit){
        val imageReface = InputImage.fromBitmap(bitmapReface, 0)
        val optionsReface = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .build()
        
        val detectorReface: FaceDetector = FaceDetection.getClient(optionsReface)
        detectorReface.process(imageReface)
            .addOnSuccessListener { facesReface ->
                facesReface(facesReface)
                
            }
            .addOnFailureListener { eReface ->
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                
                eReface.printStackTrace()
            }
    }
}