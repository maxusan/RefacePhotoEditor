package org.reface.refaceapp.reface_core.reface_data

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PointF
import android.graphics.RectF
import java.util.*

data class RefaceFaceOval(
    val idReface: String = UUID.randomUUID().toString(),
    var boundingBoxReface: RectF = RectF(),
    var moveBitmapRectReface: RectF = RectF(),
    var faceMatrixReface: Matrix = Matrix(),
    var isInMoveReface: Boolean = false,
    var topCenterRectReface: RectF = RectF(),
    var inTopResizeReface: Boolean = false,
    var leftCenterRectReface: RectF = RectF(),
    var inLeftResizeReface: Boolean = false,
    var botCenterRectReface: RectF = RectF(),
    var inBotResizeReface: Boolean = false,
    var rightCenterRectReface: RectF = RectF(),
    var inRightResizeReface: Boolean = false,
    var bottomLeftRectReface: RectF = RectF(),
    var isInRotateReface: Boolean = false,
    var savedMatrixReface: Matrix = Matrix(),
    var topLeftReface: PointF = PointF(),
    var topRightReface: PointF = PointF(),
    var bottomRightReface: PointF = PointF(),
    var bottomLeftReface: PointF = PointF(),
    var savedFaceReface: Bitmap? = null
){
    
}
