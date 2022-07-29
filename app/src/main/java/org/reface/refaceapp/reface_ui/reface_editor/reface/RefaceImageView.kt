package org.reface.refaceapp.reface_ui.reface_editor.reface

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.Matrix.MSCALE_X
import android.graphics.Matrix.MSKEW_X
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.toRectF
import androidx.core.view.MotionEventCompat
import com.google.mlkit.vision.face.Face
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.reface.refaceapp.R
import org.reface.refaceapp.reface_core.reface_data.RefaceFaceOval
import org.reface.refaceapp.reface_core.reface_data.RefaceShape
import java.lang.Math.*


class RefaceImageView(contextReface: Context, attributeSetReface: AttributeSet) :
    AppCompatImageView(contextReface, attributeSetReface) {

    private var MOVE_ICON_SIZE_COEFF = 0f
    private var SIZE_ICON_SIZE_COEFF = 0f
    private var ROTATE_ICON_SIZE_COEFF = 0f

    private var bitmapReface: Bitmap? = null
    private var bitmapMatrixReface: Matrix = Matrix()
    private var matrixScaledReface: Boolean = false
    private var bitmapReInitReface = false

    private var moveBitmapReface: Bitmap
    private var sizeBitmapReface: Bitmap
    private var rotateBitmapReface: Bitmap

    

    private var prevBitmapReface: Bitmap? = null

    private var lastXReface: Float = 0f
    private var lastYReface: Float = 0f

    private var midPointReface: PointF = PointF()

    private var dReface: Float = 0f
    private var newRotationReface: Float = 0f

    private var oldDistReface: Float = 0f

    private var refaceFaceOvals: List<RefaceFaceOval>? = null

    private var swappedReface: Boolean = false

    var refaceShape: RefaceShape = RefaceShape.getShapesListReface()[0]
        set(value) {
            field = value
            
            shapeBitmapReface = BitmapFactory.decodeResource(resources, field.previewReface)
            invalidate()
        }

    var shapeBitmapReface: Bitmap? = null

    private var paintReface: Paint = Paint()

    init {
        paintReface.style = Paint.Style.STROKE
        paintReface.color = Color.WHITE
        paintReface.strokeWidth = 4f

        moveBitmapReface = BitmapFactory.decodeResource(resources, R.drawable.ic_move)
        sizeBitmapReface = BitmapFactory.decodeResource(resources, R.drawable.ic_rect)
        rotateBitmapReface = BitmapFactory.decodeResource(resources, R.drawable.ic_rotate)
        shapeBitmapReface = BitmapFactory.decodeResource(resources, refaceShape.previewReface)
        val outValueReface = TypedValue()
        resources.getValue(R.dimen.icon_size_reface, outValueReface, true)
        val valueReface = outValueReface.float
        MOVE_ICON_SIZE_COEFF = valueReface
        SIZE_ICON_SIZE_COEFF = valueReface
        ROTATE_ICON_SIZE_COEFF = valueReface
    }

    var facesReface: List<Face>? = null
        set(value) {
            field = value
            
            invalidate()
        }

    fun setImageBmpReface(bitmapReface: Bitmap?) {
        this.bitmapReface = bitmapReface
        matrixScaledReface = false
        if (bitmapReface == null) {
            bitmapReInitReface = false
            facesReface = null
            refaceFaceOvals = null
        }
        
        invalidate()
    }

    fun getImageBmpReface(): Bitmap? {
        
        return bitmapReface?.copy(Bitmap.Config.ARGB_8888, true)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvasReface: Canvas) {
        super.onDraw(canvasReface)
        bitmapReface?.let {
            if (!matrixScaledReface) {
                bitmapMatrixReface.setRectToRect(
                    RectF(0f, 0f, bitmapReface!!.width.toFloat(), bitmapReface!!.height.toFloat()),
                    RectF(0f, 0f, width.toFloat(), height.toFloat()),
                    Matrix.ScaleToFit.CENTER
                )
                matrixScaledReface = true
            }
            canvasReface.drawBitmap(it, bitmapMatrixReface, null)
            if (!bitmapReInitReface) {
                facesReface?.let {
                    initFacesReface(it)
                }
            }
            refaceFaceOvals?.let {
                val moveBitmapSizeReface = moveBitmapReface.width * MOVE_ICON_SIZE_COEFF
                val sizeBitmapSizeReface = sizeBitmapReface.width * SIZE_ICON_SIZE_COEFF
                val rotateBitmapSizeReface = rotateBitmapReface.width * ROTATE_ICON_SIZE_COEFF
                val shapeBitmapWReface = shapeBitmapReface!!.width
                val shapeBitmapHReface = shapeBitmapReface!!.height
                it.forEach { ovalReface ->
                    val valuesReface = FloatArray(9)
                    ovalReface.faceMatrixReface.getValues(valuesReface)

                    val topLeftXReface = valuesReface[2]
                    val topLeftYReface = valuesReface[5]
                    val topRightXReface = valuesReface[0] * shapeBitmapWReface + valuesReface[2]
                    val topRightYReface = valuesReface[3] * shapeBitmapWReface + valuesReface[5]
                    val bottomLeftXReface = valuesReface[1] * shapeBitmapHReface + valuesReface[2]
                    val bottomLeftYReface = valuesReface[4] * shapeBitmapHReface + valuesReface[5]
                    val bottomRightXReface =
                        valuesReface[0] * shapeBitmapWReface + valuesReface[1] * shapeBitmapHReface + valuesReface[2]
                    val bottomRightYReface =
                        valuesReface[3] * shapeBitmapWReface + valuesReface[4] * shapeBitmapHReface + valuesReface[5]

                    //center
                    ovalReface.moveBitmapRectReface.left =
                        ((bottomRightXReface + topLeftXReface) / 2 + (bottomLeftXReface + topRightXReface) / 2) / 2 - moveBitmapSizeReface
                    ovalReface.moveBitmapRectReface.right =
                        ((bottomRightXReface + topLeftXReface) / 2 + (bottomLeftXReface + topRightXReface) / 2) / 2 + moveBitmapSizeReface
                    ovalReface.moveBitmapRectReface.top =
                        ((bottomRightYReface + topLeftYReface) / 2 + (bottomLeftYReface + topRightYReface) / 2) / 2 - moveBitmapSizeReface
                    ovalReface.moveBitmapRectReface.bottom =
                        ((bottomRightYReface + topLeftYReface) / 2 + (bottomLeftYReface + topRightYReface) / 2) / 2 + moveBitmapSizeReface

                    //top center
                    ovalReface.topCenterRectReface.left =
                        (topRightXReface + topLeftXReface) / 2 - sizeBitmapSizeReface
                    ovalReface.topCenterRectReface.right = (topRightXReface + topLeftXReface) / 2 + sizeBitmapSizeReface
                    ovalReface.topCenterRectReface.top =
                        (topRightYReface + topLeftYReface) / 2 - sizeBitmapSizeReface / 2 - sizeBitmapSizeReface
                    ovalReface.topCenterRectReface.bottom =
                        (topRightYReface + topLeftYReface) / 2 - sizeBitmapSizeReface / 2 + sizeBitmapSizeReface

                    //leftCenter
                    ovalReface.leftCenterRectReface.left =
                        (bottomLeftXReface + topLeftXReface) / 2 - sizeBitmapSizeReface / 2 - sizeBitmapSizeReface
                    ovalReface.leftCenterRectReface.right =
                        (bottomLeftXReface + topLeftXReface) / 2 - sizeBitmapSizeReface / 2 + sizeBitmapSizeReface
                    ovalReface.leftCenterRectReface.top =
                        (bottomLeftYReface + topLeftYReface) / 2 - sizeBitmapSizeReface
                    ovalReface.leftCenterRectReface.bottom =
                        (bottomLeftYReface + topLeftYReface) / 2 + sizeBitmapSizeReface

                    //rightCenter
                    ovalReface.rightCenterRectReface.left =
                        (bottomRightXReface + topRightXReface) / 2 + sizeBitmapSizeReface / 2 - sizeBitmapSizeReface
                    ovalReface.rightCenterRectReface.right =
                        (bottomRightXReface + topRightXReface) / 2 + sizeBitmapSizeReface / 2 + sizeBitmapSizeReface
                    ovalReface.rightCenterRectReface.top =
                        (bottomRightYReface + topRightYReface) / 2 - sizeBitmapSizeReface
                    ovalReface.rightCenterRectReface.bottom =
                        (bottomRightYReface + topRightYReface) / 2 + sizeBitmapSizeReface

                    //bottCenter
                    ovalReface.botCenterRectReface.left =
                        (bottomRightXReface + bottomLeftXReface) / 2 - sizeBitmapSizeReface
                    ovalReface.botCenterRectReface.right = (bottomRightXReface + bottomLeftXReface) / 2 + sizeBitmapSizeReface
                    ovalReface.botCenterRectReface.top =
                        (bottomRightYReface + bottomLeftYReface) / 2 + sizeBitmapSizeReface / 2 - sizeBitmapSizeReface
                    ovalReface.botCenterRectReface.bottom =
                        (bottomRightYReface + bottomLeftYReface) / 2 + sizeBitmapSizeReface / 2 + sizeBitmapSizeReface

                    //bottomLeft
                    ovalReface.bottomLeftRectReface.left =
                        bottomLeftXReface - rotateBitmapSizeReface
                    ovalReface.bottomLeftRectReface.right = bottomLeftXReface + rotateBitmapSizeReface
                    ovalReface.bottomLeftRectReface.top =
                        bottomLeftYReface - rotateBitmapSizeReface
                    ovalReface.bottomLeftRectReface.bottom =
                        bottomLeftYReface + rotateBitmapSizeReface



                    shapeBitmapReface?.let { shapeReface ->
                        canvasReface.drawBitmap(
                            shapeReface, ovalReface.faceMatrixReface,
                            Paint()
                        )
                        val pointsReface = getMatrixPointsReface(ovalReface.faceMatrixReface)
                        ovalReface.topLeftReface.set(pointsReface[5], pointsReface[6])
                        ovalReface.topRightReface.set(pointsReface[0], pointsReface[1])
                        ovalReface.bottomLeftReface.set(pointsReface[4], pointsReface[5])
                        ovalReface.bottomRightReface.set(pointsReface[2], pointsReface[3])
                    }
                    canvasReface.drawBitmap(moveBitmapReface, null, ovalReface.moveBitmapRectReface, Paint())
                    canvasReface.drawBitmap(sizeBitmapReface, null, ovalReface.topCenterRectReface, Paint())
                    canvasReface.drawBitmap(sizeBitmapReface, null, ovalReface.botCenterRectReface, Paint())
                    canvasReface.drawBitmap(sizeBitmapReface, null, ovalReface.leftCenterRectReface, Paint())
                    canvasReface.drawBitmap(sizeBitmapReface, null, ovalReface.rightCenterRectReface, Paint())
                    canvasReface.drawBitmap(rotateBitmapReface, null, ovalReface.bottomLeftRectReface, Paint())

                }
            }
        }
    }

    private fun initFacesReface(facesReface: List<Face>) {
        val boundingBoxesReface = mutableListOf<RefaceFaceOval>()
        facesReface.forEachIndexed { indexReface, itReface ->
            if (itReface.boundingBox.width() > 20f) {
                val arrReface = FloatArray(9)
                bitmapMatrixReface.getValues(arrReface)

                val scaleXReface = arrReface[Matrix.MSCALE_X]
                val scaleYReface = arrReface[Matrix.MSCALE_Y]
                val transYReface = arrReface[Matrix.MTRANS_Y]
                val transXReface = arrReface[Matrix.MTRANS_X]

                val boundingBoxReface = itReface.boundingBox.toRectF()
                boundingBoxReface.left = boundingBoxReface.left * scaleXReface + transXReface
                boundingBoxReface.top = boundingBoxReface.top * scaleYReface + transYReface
                boundingBoxReface.right = boundingBoxReface.right * scaleXReface + transXReface
                boundingBoxReface.bottom = boundingBoxReface.bottom * scaleYReface + transYReface
                val ovalReface = RefaceFaceOval(boundingBoxReface = boundingBoxReface)
                ovalReface.faceMatrixReface.setRectToRect(
                    RectF(0f, 0f, shapeBitmapReface!!.width.toFloat(), shapeBitmapReface!!.height.toFloat()),
                    ovalReface.boundingBoxReface,
                    Matrix.ScaleToFit.CENTER
                )
                boundingBoxesReface.add(ovalReface)
            }
        }
        
        this.refaceFaceOvals = boundingBoxesReface
        bitmapReInitReface = true
    }

    override fun onTouchEvent(eventReface: MotionEvent): Boolean {
        var handledReface = true
        when (MotionEventCompat.getActionMasked(eventReface)) {
            MotionEvent.ACTION_DOWN -> {
                refaceFaceOvals?.forEach {
                    if (inMoveRectReface(it, eventReface)) {
                        it.isInMoveReface = true
                        lastXReface = eventReface.x
                        lastYReface = eventReface.y
                    } else if (isInResizeReface(it, eventReface)) {
                        midPointReface(midPointReface, it)
                        lastXReface = eventReface.x
                        lastYReface = eventReface.y
                        it.savedMatrixReface.set(it.faceMatrixReface)
                        dReface = rotationReface(eventReface)
                        oldDistReface = spacingReface(eventReface)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val currentOvalRefacce =
                    refaceFaceOvals?.find { ovalReface -> ovalReface.isInMoveReface || ovalReface.inBotResizeReface || ovalReface.inTopResizeReface || ovalReface.inLeftResizeReface || ovalReface.inRightResizeReface || ovalReface.isInRotateReface }
                if (currentOvalRefacce?.isInMoveReface == true) {
                    midPointReface(midPointReface, currentOvalRefacce)
                    val xReface = eventReface.getX()
                    val yReface = eventReface.getY()
                    val xDiffReface = xReface - lastXReface
                    val yDiffReface = yReface - lastYReface
                    currentOvalRefacce.faceMatrixReface.postTranslate(xDiffReface, yDiffReface)
                    lastXReface = xReface
                    lastYReface = yReface
                    invalidate()
                } else if (currentOvalRefacce?.inTopResizeReface == true || currentOvalRefacce?.inLeftResizeReface == true || currentOvalRefacce?.inRightResizeReface == true || currentOvalRefacce?.inBotResizeReface == true) {
                    midPointReface(midPointReface, currentOvalRefacce)
                    val shapeBitmapWReface = shapeBitmapReface!!.width.toFloat()
                    val shapeBitmapHReface = shapeBitmapReface!!.height.toFloat()
                    val valuesReface = FloatArray(9)
                    currentOvalRefacce.faceMatrixReface.getValues(valuesReface)

                    var topLeftXReface = valuesReface[2]
                    var topLeftYReface = valuesReface[5]
                    var topRightXReface = valuesReface[0] * shapeBitmapWReface + valuesReface[2]
                    var topRightYReface = valuesReface[3] * shapeBitmapWReface + valuesReface[5]
                    var bottomLeftXReface = valuesReface[1] * shapeBitmapHReface + valuesReface[2]
                    var bottomLeftYReface = valuesReface[4] * shapeBitmapHReface + valuesReface[5]
                    var bottomRightXReface =
                        valuesReface[0] * shapeBitmapWReface + valuesReface[1] * shapeBitmapHReface + valuesReface[2]
                    var bottomRightYReface =
                        valuesReface[3] * shapeBitmapWReface + valuesReface[4] * shapeBitmapHReface + valuesReface[5]

                    val xReface = eventReface.getX(0)
                    val yReface = eventReface.getY(0)
                    val xDiffReface = xReface - lastXReface
                    val yDiffReface = yReface - lastYReface

                    when {
                        currentOvalRefacce.inTopResizeReface -> {
                            topLeftYReface += yDiffReface
                            topRightYReface += yDiffReface
                        }
                        currentOvalRefacce.inLeftResizeReface -> {
                            topLeftXReface += xDiffReface
                            bottomLeftXReface += xDiffReface
                        }
                        currentOvalRefacce.inBotResizeReface -> {
                            bottomLeftYReface += yDiffReface
                            bottomRightYReface += yDiffReface
                        }
                        currentOvalRefacce.inRightResizeReface -> {
                            topRightXReface += xDiffReface
                            bottomRightXReface += xDiffReface
                        }
                    }
                    val ptsReface = floatArrayOf(
                        0f, 0f, 0f, shapeBitmapHReface,
                        shapeBitmapWReface, shapeBitmapHReface,
                        shapeBitmapWReface, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f
                    )
                    ptsReface[8] = topLeftXReface
                    ptsReface[9] = topLeftYReface
                    ptsReface[10] = bottomLeftXReface
                    ptsReface[11] = bottomLeftYReface
                    ptsReface[12] = bottomRightXReface
                    ptsReface[13] = bottomRightYReface
                    ptsReface[14] = topRightXReface
                    ptsReface[15] = topRightYReface
                    currentOvalRefacce.faceMatrixReface.setPolyToPoly(ptsReface, 0, ptsReface, 8, 4)
                    lastXReface = xReface
                    lastYReface = yReface
                    invalidate()
                } else if (currentOvalRefacce?.isInRotateReface == true) {
                    newRotationReface = rotationReface(eventReface)
                    val rReface = newRotationReface - dReface
                    currentOvalRefacce.faceMatrixReface.set(currentOvalRefacce.savedMatrixReface)
                    val pointsReface = getMatrixPointsReface(currentOvalRefacce.faceMatrixReface)
                    currentOvalRefacce.faceMatrixReface.postRotate(
                        rReface,
                        pointsReface[8],
                        pointsReface[9]
                    )
                    invalidate()
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                refaceFaceOvals?.forEach {
                    midPointReface(midPointReface, it)
                    it.isInMoveReface = false
                    it.inRightResizeReface = false
                    it.inLeftResizeReface = false
                    it.inTopResizeReface = false
                    it.inBotResizeReface = false
                    it.isInRotateReface = false
                }
            }
        }
        if (handledReface) {
            swappedReface = false
        }

        return handledReface
    }

    private fun inMoveRectReface(refaceFaceOval: RefaceFaceOval, eventReface: MotionEvent): Boolean {
        
        return refaceFaceOval.moveBitmapRectReface.contains(eventReface.x, eventReface.y)
    }

    private fun isInResizeReface(refaceFaceOval: RefaceFaceOval, eventReface: MotionEvent): Boolean {
        var containsReface = true
        val xReface = eventReface.x
        val yReface = eventReface.y
        when {
            refaceFaceOval.topCenterRectReface.contains(xReface, yReface) -> {
                refaceFaceOval.inTopResizeReface = true
            }
            refaceFaceOval.leftCenterRectReface.contains(xReface, yReface) -> {
                refaceFaceOval.inLeftResizeReface = true
            }
            refaceFaceOval.botCenterRectReface.contains(xReface, yReface) -> {
                refaceFaceOval.inBotResizeReface = true
            }
            refaceFaceOval.rightCenterRectReface.contains(xReface, yReface) -> {
                refaceFaceOval.inRightResizeReface = true
            }
            refaceFaceOval.bottomLeftRectReface.contains(xReface, yReface) -> {
                refaceFaceOval.isInRotateReface = true
            }
            else -> {
                containsReface = false
            }
        }
        return containsReface
    }

    suspend fun flipFacesReface(callbackReface: (Bitmap?) -> Unit) = withContext(Dispatchers.IO) {
        if (facesReface != null && facesReface!!.size > 1) {
            if (!swappedReface) {
                prevBitmapReface = bitmapReface
                val facesListReface = mutableListOf<Pair<Bitmap, Matrix>>()
                val arrReface = FloatArray(9)
                bitmapMatrixReface.getValues(arrReface)
                val scaleXReface = arrReface[Matrix.MSCALE_X]
                val scaleYReface = arrReface[Matrix.MSCALE_Y]
                val transYReface = arrReface[Matrix.MTRANS_Y]
                val transXReface = arrReface[Matrix.MTRANS_X]
                val paintReface = Paint(Paint.ANTI_ALIAS_FLAG)
                paintReface.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
                val flippedBitmapReface =
                    Bitmap.createBitmap(
                        ((bitmapReface!!.width * scaleXReface) + transXReface * 2).toInt(),
                        ((bitmapReface!!.height * scaleYReface) + transYReface * 2).toInt(), Bitmap.Config.ARGB_8888
                    )
                val flippedCanvasReface = Canvas(flippedBitmapReface)
                flippedCanvasReface.drawBitmap(bitmapReface!!, bitmapMatrixReface, null)

                refaceFaceOvals?.forEach { ovalReface ->
                    val maskBitmapReface =
                        Bitmap.createBitmap(
                            flippedBitmapReface.width,
                            flippedBitmapReface.height, Bitmap.Config.ARGB_8888
                        )
                    val maskCanvasReface = Canvas(maskBitmapReface)
                    val maskReface = BitmapFactory.decodeResource(resources, refaceShape.maskReface)
                    val scaledMaskReface = Bitmap.createScaledBitmap(
                        maskReface,
                        shapeBitmapReface!!.width,
                        shapeBitmapReface!!.height,
                        false
                    )
                    maskCanvasReface.drawBitmap(scaledMaskReface, ovalReface.faceMatrixReface, null)
                    val croppedFaceBitmapReface =
                        Bitmap.createBitmap(
                            flippedBitmapReface.width,
                            flippedBitmapReface.height, Bitmap.Config.ARGB_8888
                        )
                    val croppedFaceCanvasReface = Canvas(croppedFaceBitmapReface)
                    croppedFaceCanvasReface.drawBitmap(bitmapReface!!, bitmapMatrixReface, null)
                    croppedFaceCanvasReface.drawBitmap(maskBitmapReface, 0f, 0f, paintReface)
                    val matrixPointsReface = getMatrixPointsReface(ovalReface.faceMatrixReface)
                    val currentRotationReface = ovalReface.faceMatrixReface.getRotationAngleReface()
                    val rotatedBitmapReface = rotateBitmapReface(
                        croppedFaceBitmapReface,
                        -currentRotationReface,
                        PointF(matrixPointsReface[8], matrixPointsReface[9]),
                        matrixPointsReface
                    )
                    val croppedAndRotatedBitmapReface = cropBitmapTransparencyReface(rotatedBitmapReface)
                    val tempMatrixReface = Matrix()
                    tempMatrixReface.set(ovalReface.faceMatrixReface)
                    facesListReface.add(Pair(croppedAndRotatedBitmapReface, tempMatrixReface))
                }

                val firstMatrixReface = facesListReface[0].second
                val secondMatrixReface = facesListReface[1].second
                val firBmpReface = getResizedBitmapReface(
                    facesListReface[0].first, shapeBitmapReface!!.width.toFloat(),
                    shapeBitmapReface!!.height.toFloat()
                )
                val secBmpReface = getResizedBitmapReface(
                    facesListReface[1].first,
                    shapeBitmapReface!!.width.toFloat(), shapeBitmapReface!!.height.toFloat()
                )

                flippedCanvasReface.drawBitmap(firBmpReface, secondMatrixReface, null)
                flippedCanvasReface.drawBitmap(secBmpReface, firstMatrixReface, null)
                setImageBmpReface(flippedBitmapReface)
                callbackReface(flippedBitmapReface)
                swappedReface = true
            } else {
                callbackReface(prevBitmapReface)
                swappedReface = false
            }
        } else {
            callbackReface(null)
        }
    }

    suspend fun saveFacesReface() {
        val paintReface = Paint(Paint.ANTI_ALIAS_FLAG)
        paintReface.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        bitmapReface?.let { bitmapReface ->
            refaceFaceOvals?.forEach { ovalReface ->
                val arrReface = FloatArray(9)
                bitmapMatrixReface.getValues(arrReface)
                val scaleXReface = arrReface[Matrix.MSCALE_X]
                val scaleYReface = arrReface[Matrix.MSCALE_Y]
                val transYReface = arrReface[Matrix.MTRANS_Y]
                val transXReface = arrReface[Matrix.MTRANS_X]
                val flippedBitmapReface =
                    Bitmap.createBitmap(
                        ((bitmapReface!!.width * scaleXReface) + transXReface * 2).toInt(),
                        ((bitmapReface!!.height * scaleYReface) + transYReface * 2).toInt(), Bitmap.Config.ARGB_8888
                    )
                val flippedCanvasReface = Canvas(flippedBitmapReface)
                flippedCanvasReface.drawBitmap(bitmapReface!!, bitmapMatrixReface, null)
                val maskBitmapReface =
                    Bitmap.createBitmap(
                        flippedBitmapReface.width,
                        flippedBitmapReface.height, Bitmap.Config.ARGB_8888
                    )
                val maskCanvasReface = Canvas(maskBitmapReface)
                val maskReface = BitmapFactory.decodeResource(resources, refaceShape.maskReface)
                val scaledMaskReface = Bitmap.createScaledBitmap(
                    maskReface,
                    shapeBitmapReface!!.width,
                    shapeBitmapReface!!.height,
                    false
                )
                maskCanvasReface.drawBitmap(scaledMaskReface, ovalReface.faceMatrixReface, null)
                val croppedFaceBitmapReface =
                    Bitmap.createBitmap(
                        flippedBitmapReface.width,
                        flippedBitmapReface.height, Bitmap.Config.ARGB_8888
                    )
                val croppedFaceCanvasReface = Canvas(croppedFaceBitmapReface)
                croppedFaceCanvasReface.drawBitmap(bitmapReface!!, bitmapMatrixReface, null)
                croppedFaceCanvasReface.drawBitmap(maskBitmapReface, 0f, 0f, paintReface)
                val matrixPointsReface = getMatrixPointsReface(ovalReface.faceMatrixReface)
                val currentRotationReface = ovalReface.faceMatrixReface.getRotationAngleReface()
                val rotatedBitmapReface = rotateBitmapReface(
                    croppedFaceBitmapReface,
                    -currentRotationReface,
                    PointF(matrixPointsReface[8], matrixPointsReface[9]),
                    matrixPointsReface
                )
                val croppedAndRotatedBitmapReface = cropBitmapTransparencyReface(rotatedBitmapReface)
                ovalReface.savedFaceReface = croppedAndRotatedBitmapReface
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun pasteFacesReface(callbackReface: (Bitmap?) -> Unit) {
        val arrReface = FloatArray(9)
        bitmapMatrixReface.getValues(arrReface)
        val scaleXReface = arrReface[Matrix.MSCALE_X]
        val scaleYReface = arrReface[Matrix.MSCALE_Y]
        val transYReface = arrReface[Matrix.MTRANS_Y]
        val transXReface = arrReface[Matrix.MTRANS_X]
        val pastedBitmapReface =
            Bitmap.createBitmap(
                ((bitmapReface!!.width * scaleXReface) + transXReface * 2).toInt(),
                ((bitmapReface!!.height * scaleYReface) + transYReface * 2).toInt(), Bitmap.Config.ARGB_8888
            )
        val pastedCanvasReface = Canvas(pastedBitmapReface)
        pastedCanvasReface.drawBitmap(bitmapReface!!, bitmapMatrixReface, null)
        bitmapReface?.let { bitmapReface ->
            refaceFaceOvals?.forEach { ovalReface ->
                ovalReface.savedFaceReface?.let {
                    val copyReface = it.copy(it.config, true)
                    val scaledBitmapReface = getResizedBitmapReface(
                        copyReface, shapeBitmapReface!!.width.toFloat(),
                        shapeBitmapReface!!.height.toFloat()
                    )
                    pastedCanvasReface.drawBitmap(scaledBitmapReface, ovalReface.faceMatrixReface, null)
                    Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
                }
            }
        }
        callbackReface(pastedBitmapReface)
    }

    private fun getResizedBitmapReface(bmReface: Bitmap, newWidthReface: Float, newHeightReface: Float): Bitmap {
        val widthReface = bmReface.width
        val heightReface = bmReface.height
        val scaleWidthReface = newWidthReface / widthReface
        val scaleHeightReface = newHeightReface / heightReface
        val matrixReface = Matrix()
        matrixReface.postScale(scaleWidthReface, scaleHeightReface)
        
        val resizedBitmapReface = Bitmap.createBitmap(
            bmReface, 0, 0, widthReface, heightReface, matrixReface, false
        )
        bmReface.recycle()
        return resizedBitmapReface
    }

    private suspend fun cropBitmapTransparencyReface(sourceBitmapReface: Bitmap): Bitmap {
        var minXReface = sourceBitmapReface.width
        var minYReface = sourceBitmapReface.height
        var maxXReface = -1
        var maxYReface = -1
        for (yReface in 0 until sourceBitmapReface.height step 3) {
            for (xReface in 0 until sourceBitmapReface.width step 3) {
                val alphaReface = sourceBitmapReface.getPixel(xReface, yReface) shr 24 and 255
                if (alphaReface > 0) {
                    if (xReface < minXReface) minXReface = xReface
                    if (xReface > maxXReface) maxXReface = xReface
                    if (yReface < minYReface) minYReface = yReface
                    if (yReface > maxYReface) maxYReface = yReface
                }
            }
        }
        return if (maxXReface < minXReface || maxYReface < minYReface) null!! else Bitmap.createBitmap(
            sourceBitmapReface,
            minXReface,
            minYReface,
            maxXReface - minXReface + 1,
            maxYReface - minYReface + 1
        )
    }

    private fun rotateBitmapReface(
        sourceReface: Bitmap,
        angleReface: Float,
        centerReface: PointF,
        pointsReface: FloatArray
    ): Bitmap {
        val matrixReface = Matrix()
        
        matrixReface.postRotate(angleReface, centerReface.x, centerReface.y)
        matrixReface.mapPoints(pointsReface)
        return Bitmap.createBitmap(sourceReface, 0, 0, sourceReface.width, sourceReface.height, matrixReface, true)
    }

    private fun getMatrixPointsReface(matrixReface: Matrix): FloatArray {
        val valuesReface = FloatArray(9)
        matrixReface.getValues(valuesReface)
        val shapeBitmapWReface = shapeBitmapReface!!.width
        val shapeBitmapHReface = shapeBitmapReface!!.height
        val topLeftXReface = valuesReface[2]
        val topLeftYReface = valuesReface[5]
        val topRightXReface = valuesReface[0] * shapeBitmapWReface + valuesReface[2]
        val topRightYReface = valuesReface[3] * shapeBitmapWReface + valuesReface[5]
        val bottomLeftXReface = valuesReface[1] * shapeBitmapHReface + valuesReface[2]
        val bottomLeftYReface = valuesReface[4] * shapeBitmapHReface + valuesReface[5]
        val bottomRightXReface =
            valuesReface[0] * shapeBitmapWReface + valuesReface[1] * shapeBitmapHReface + valuesReface[2]
        val bottomRightYReface =
            valuesReface[3] * shapeBitmapWReface + valuesReface[4] * shapeBitmapHReface + valuesReface[5]
        val centerXReface = ((bottomRightXReface + topLeftXReface) / 2 + (topRightXReface + bottomLeftXReface) / 2) / 2
        val centerYReface = ((bottomRightYReface + topLeftYReface) / 2 + (topRightYReface + bottomLeftYReface) / 2) / 2
        val ptArrReface = floatArrayOf(
            topRightXReface,
            topRightYReface,
            bottomRightXReface,
            bottomRightYReface,
            bottomLeftXReface,
            bottomLeftYReface,
            topLeftXReface,
            topLeftYReface,
            centerXReface,
            centerYReface
        )
        return ptArrReface
    }

    private fun rotationReface(eventReface: MotionEvent): Float {
        val dxReface = (eventReface.x - midPointReface.x).toDouble()
        val dyReface = (eventReface.y - midPointReface.y).toDouble()
        val radiansReface = atan2(dyReface, dxReface)
        val degreesReface = toDegrees(radiansReface).toFloat()
        
        return degreesReface
    }

    private fun midPointReface(pointReface: PointF, ovalReface: RefaceFaceOval) {
        
        val xReface =
            ((ovalReface.bottomRightReface.x + ovalReface.topLeftReface.x) / 2 + (ovalReface.topRightReface.x + ovalReface.bottomLeftReface.x) / 2) / 2
        val yReface =
            ((ovalReface.bottomLeftReface.y + ovalReface.topRightReface.y) / 2 + (ovalReface.bottomRightReface.y + ovalReface.topLeftReface.y) / 2) / 2
        pointReface.set(xReface, yReface)
    }

    private fun spacingReface(eventReface: MotionEvent): Float {
        
        val xReface = eventReface.x - midPointReface.x
        val yRefase = eventReface.y - midPointReface.y
        return Math.sqrt((xReface * xReface + yRefase * yRefase).toDouble()).toFloat()
    }

    private fun Matrix.getRotationAngleReface() = FloatArray(9)
        .apply { getValues(this) }
        .let {
            
            -round(
                atan2(
                    it[MSKEW_X].toDouble(),
                    it[MSCALE_X].toDouble()
                ) * (180 / PI)
            ).toFloat()
        }
}