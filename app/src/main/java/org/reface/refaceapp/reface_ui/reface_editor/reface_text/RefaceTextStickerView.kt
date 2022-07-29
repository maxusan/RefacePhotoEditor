package org.reface.refaceapp.reface_ui.reface_editor.reface_text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import org.reface.refaceapp.R
import org.reface.refaceapp.reface_core.reface_data.RefaceColor
import org.reface.refaceapp.reface_core.reface_data.RefaceFont
import org.reface.refaceapp.reface_core.reface_data.RefaceTextItem


class RefaceTextStickerView(contextReface: Context, attributeSetReface: AttributeSet) :
    AppCompatTextView(contextReface, attributeSetReface) {

    private var bitmapReface: Bitmap? = null
    private val bitmapMatrixReface: Matrix = Matrix()
    private val savedMatrixReface: Matrix = Matrix()
    private var matrixScaledReface: Boolean = false
    private var flagReface: Boolean = false

    private var topLeftReface: PointF = PointF()
    private var topRightReface: PointF = PointF()
    private var bottomRightReface: PointF = PointF()
    private var bottomLeftReface: PointF = PointF()

    private var lastXReface: Float = 0f
    private var lastYReface: Float = 0f

    private var topRightRectReface: RectF = RectF()
    private var bottomRightRectReface: RectF = RectF()
    private var bottomLeReface: RectF = RectF()

    private val iconsCoeReface: Float = 0.2f

    private var dReface: Float = 0f
    private var newRotationReface: Float = 0f

    val midPointReface = PointF()

    private var oldDistReface: Float = 0f
    private var newDiRefaceRefcae: Float = 0f

    private var movinReface: Boolean = false
    private var deleteReface: Boolean = false
    private var sizeReface: Boolean = false
    private var rotateReface: Boolean = false

    private var textBitmapReface: Bitmap? = null
    private var rotateBitmapReface: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.ic_text_rotate)
    private var deleteBitmapReface: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
    private var sizeBitmapReface: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_size)

    private var textBgBitmapReface: Bitmap =
        BitmapFactory.decodeResource(resources, R.drawable.text_background)

    


    fun setImageBmpReface(bitmapReface: Bitmap?) {
        this.bitmapReface = bitmapReface
        
        invalidate()
    }

    private var stickerItemReface: RefaceTextItem? = null
    fun addStickerReface(refaceText: RefaceTextItem?) {
        if (stickerItemReface == null) {
            stickerItemReface = refaceText
            invalidate()
        } else {
            applyTextToImageReface()
            stickerItemReface = refaceText
            matrixScaledReface = false
            flagReface = false
            invalidate()
        }
        
    }

    fun getBitmapReface(): Bitmap {
        
        return applyTextToImageReface()
    }

    private fun applyTextToImageReface(): Bitmap {
        val arrReface = FloatArray(9)
        bitmapMatrixReface.getValues(arrReface)
        val scaleXReface = arrReface[Matrix.MSCALE_X]
        val scaleYReface = arrReface[Matrix.MSCALE_Y]
        val transYReface = arrReface[Matrix.MTRANS_Y]
        val transXReface = arrReface[Matrix.MTRANS_X]
        val appliedBitmapReface = Bitmap.createBitmap(
            ((bitmapReface!!.width * scaleXReface) + transXReface * 2).toInt(),
            ((bitmapReface!!.height * scaleYReface) + transYReface * 2).toInt(), Bitmap.Config.ARGB_8888
        )
        
        val cvReface = Canvas(appliedBitmapReface)
        cvReface.drawBitmap(bitmapReface!!, bitmapMatrixReface, null)
        if(stickerItemReface != null){
            val textBitmapReface = createTextBitmapReface(stickerItemReface!!)
            cvReface.drawBitmap(textBitmapReface.first, stickerItemReface!!.matrixReface, null)
        }
        val croppedBitmapReface = Bitmap.createBitmap(appliedBitmapReface, arrReface[Matrix.MTRANS_X].toInt(), arrReface[Matrix.MTRANS_Y].toInt(), width - arrReface[Matrix.MTRANS_X].toInt() * 2, height - arrReface[Matrix.MTRANS_Y].toInt() * 2)
        bitmapReface = croppedBitmapReface
        return croppedBitmapReface.copy(Bitmap.Config.ARGB_8888, true)
    }

    fun setTextColorReface(refaceColor: RefaceColor) {
        stickerItemReface?.refaceColorReface = refaceColor
        
        invalidate()
    }

    fun setTextTypeFaceReface(refaceFont: RefaceFont) {
        stickerItemReface?.refaceFontReface = refaceFont
        
        flagReface = false
        invalidate()
    }

    fun setTextReface(textReface: String) {
        stickerItemReface?.textReface = textReface
        flagReface = false
        
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvasReface: Canvas) {
        super.onDraw(canvasReface)
        bitmapReface?.let {
            if (!matrixScaledReface) {
                bitmapMatrixReface.setRectToRect(
                    RectF(0f, 0f, it.width.toFloat(), it.height.toFloat()),
                    RectF(0f, 0f, width.toFloat(), height.toFloat()),
                    Matrix.ScaleToFit.CENTER
                )
                matrixScaledReface = true
            }
            canvasReface.drawBitmap(it, bitmapMatrixReface, null)
            stickerItemReface?.let {
                val pairReface = createTextBitmapReface(it)
                textBitmapReface = pairReface.first

                if (!flagReface) {
                    it.matrixReface.setRectToRect(
                        RectF(0f, 0f, 0f, 0f),
                        RectF(0f, 0f, pairReface.first.width.toFloat(), pairReface.first.height.toFloat()),
                        Matrix.ScaleToFit.CENTER
                    )
                    it.matrixReface.postTranslate(
                        (width / 2 - textBitmapReface!!.width / 2).toFloat(),
                        (height / 4 - textBitmapReface!!.height / 2).toFloat()
                    )
                    flagReface = true
                }
                val bgReface = Bitmap.createScaledBitmap(
                    textBgBitmapReface,
                    (textBitmapReface!!.width * 1).toInt(), (textBitmapReface!!.height * 1).toInt(), false
                )
                val pointsReface = getMatrixPointsReface(it.matrixReface)
                topLeftReface.set(pointsReface[5], pointsReface[6])
                topRightReface.set(pointsReface[0], pointsReface[1])
                bottomLeftReface.set(pointsReface[4], pointsReface[5])
                bottomRightReface.set(pointsReface[2], pointsReface[3])

                //topRight
                topRightRectReface.top = topRightReface.y - deleteBitmapReface.width * iconsCoeReface
                topRightRectReface.bottom = topRightReface.y + deleteBitmapReface.width * iconsCoeReface
                topRightRectReface.left = topRightReface.x - deleteBitmapReface.width * iconsCoeReface
                topRightRectReface.right = topRightReface.x + deleteBitmapReface.width * iconsCoeReface

                //bottomRight
                bottomRightRectReface.top = bottomRightReface.y - sizeBitmapReface.width * iconsCoeReface
                bottomRightRectReface.bottom = bottomRightReface.y + sizeBitmapReface.width * iconsCoeReface
                bottomRightRectReface.left = bottomRightReface.x - sizeBitmapReface.width * iconsCoeReface
                bottomRightRectReface.right = bottomRightReface.x + sizeBitmapReface.width * iconsCoeReface

                //bottomLeft
                bottomLeReface.top = bottomLeftReface.y - rotateBitmapReface.width * iconsCoeReface
                bottomLeReface.bottom = bottomLeftReface.y + rotateBitmapReface.width * iconsCoeReface
                bottomLeReface.left = bottomLeftReface.x - rotateBitmapReface.width * iconsCoeReface
                bottomLeReface.right = bottomLeftReface.x + rotateBitmapReface.width * iconsCoeReface

                canvasReface.drawBitmap(bgReface, it.matrixReface, null)
                canvasReface.drawBitmap(textBitmapReface!!, it.matrixReface, null)
                canvasReface.drawBitmap(deleteBitmapReface, null, topRightRectReface, null)
                canvasReface.drawBitmap(sizeBitmapReface, null, bottomRightRectReface, null)
                canvasReface.drawBitmap(rotateBitmapReface, null, bottomLeReface, null)
            }
        }
    }

    override fun onTouchEvent(eventReface: MotionEvent): Boolean {
        var handledReface = true
        when (eventReface.action) {
            MotionEvent.ACTION_DOWN -> {
                if (isInTextReface(eventReface)) {
                    movinReface = true
                    lastXReface = eventReface.x
                    lastYReface = eventReface.y
                } else if (isInResizeReface(eventReface)) {
                    midPointRefaceReface(midPointReface)
                    lastXReface = eventReface.x
                    lastYReface = eventReface.y
                    savedMatrixReface.set(stickerItemReface!!.matrixReface)
                    dReface = rotationReface(eventReface)
                    oldDistReface = spacingReface(eventReface)
                } else if (isInDeleteReface(eventReface)) {
                    deleteCurrentStickerReface()
                } else {
                    handledReface = false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (movinReface) {
                    val xRefcae = eventReface.getX(0)
                    val yReface = eventReface.getY(0)
                    val xDiffReface = xRefcae - lastXReface
                    val yDiffReface = yReface - lastYReface
                    stickerItemReface?.matrixReface?.postTranslate(xDiffReface, yDiffReface)
                    lastXReface = xRefcae
                    lastYReface = yReface
                } else if (rotateReface) {
                    newRotationReface = rotationReface(eventReface)
                    val rReface = newRotationReface - dReface
                    stickerItemReface!!.matrixReface.set(savedMatrixReface)
                    stickerItemReface!!.matrixReface.postRotate(
                        rReface,
                        midPointReface.x,
                        midPointReface.y
                    )
                } else if (sizeReface) {
                    midPointRefaceReface(midPointReface)
                    newDiRefaceRefcae = spacingReface(eventReface)
                    if (newDiRefaceRefcae > 10f) {
                        stickerItemReface!!.matrixReface.set(savedMatrixReface)
                        val scale = newDiRefaceRefcae / oldDistReface
                        stickerItemReface!!.matrixReface.postScale(scale, scale, midPointReface.x, midPointReface.y)
                    }
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                midPointRefaceReface(midPointReface)
                movinReface = false
                rotateReface = false
                sizeReface = false
                deleteReface = false
            }
        }
        
        return handledReface
    }

    private fun deleteCurrentStickerReface() {
        
        addStickerReface(null)
    }

    private fun isInTextReface(eventReface: MotionEvent): Boolean {
        
        kotlin.runCatching {
            val pointsReface = getMatrixPointsReface(stickerItemReface!!.matrixReface)
            val topLeftReface = PointF(pointsReface[6], pointsReface[7])
            val topRightReface = PointF(pointsReface[0], pointsReface[1])
            val bottomRightReface = PointF(pointsReface[2], pointsReface[3])
            val bottomLeftReface = PointF(pointsReface[4], pointsReface[5])
            return (eventReface.x > topLeftReface.x && eventReface.x < bottomRightReface.x) && (eventReface.y > topLeftReface.y && eventReface.y < bottomRightReface.y)
        }
        return false
    }

    private fun spacingReface(eventReface: MotionEvent): Float {
        val xReface = eventReface.x - midPointReface.x
        
        val yReface = eventReface.y - midPointReface.y
        return Math.sqrt((xReface * xReface + yReface * yReface).toDouble()).toFloat()
    }

    private fun createTextBitmapReface(refaceText: RefaceTextItem): Pair<Bitmap, Rect> {
        val tsReface = 72f
        val paintReface = TextPaint(TextPaint.ANTI_ALIAS_FLAG)
        paintReface.color = refaceText.refaceColorReface.colorReface
        paintReface.typeface = Typeface.createFromAsset(context.assets, "fonts/${refaceText.refaceFontReface.fileNameReface}")
        paintReface.textSize = tsReface
        val boundsReface = Rect()
        paintReface.getTextBounds(refaceText.textReface, 0, refaceText.textReface.length, boundsReface)
        val textBitmapReface = Bitmap.createBitmap(
            if (refaceText.textReface.isNotBlank()) (boundsReface.width() + tsReface / 2).toInt() else tsReface.toInt(),
            if (refaceText.textReface.isNotBlank()) (boundsReface.height() + tsReface / 2).toInt() else tsReface.toInt(),
            Bitmap.Config.ARGB_8888
        )
        
        val textCanvasReface = Canvas(textBitmapReface)
        textCanvasReface.drawText(
            refaceText.textReface,
            ((textBitmapReface.width - boundsReface.width()) / 2).toFloat(),
            ((textBitmapReface.height + boundsReface.height()) / 2).toFloat(), paintReface
        )
        return Pair(textBitmapReface, boundsReface)
    }

    private fun getMatrixPointsReface(matrixRefcae: Matrix): FloatArray {
        val valuesReface = FloatArray(9)
        matrixRefcae.getValues(valuesReface)
        val shapeBitmapWReface = textBitmapReface!!.width
        val shapeBitmapHReface = textBitmapReface!!.height
        val topLefRefacetX = valuesReface[2]
        val topLeftYReface = valuesReface[5]
        val topRightXReface = valuesReface[0] * shapeBitmapWReface + valuesReface[2]
        val topRightYReface = valuesReface[3] * shapeBitmapWReface + valuesReface[5]
        val bottomLeftXReface = valuesReface[1] * shapeBitmapHReface + valuesReface[2]
        val bottomLeftYReface = valuesReface[4] * shapeBitmapHReface + valuesReface[5]
        val bottomRightXReface =
            valuesReface[0] * shapeBitmapWReface + valuesReface[1] * shapeBitmapHReface + valuesReface[2]
        val bottomRRefaceightY =
            valuesReface[3] * shapeBitmapWReface + valuesReface[4] * shapeBitmapHReface + valuesReface[5]
        val centerXReface = ((bottomRightXReface + topLefRefacetX) / 2 + (topRightXReface + bottomLeftXReface) / 2) / 2
        val centerYReface = ((bottomRRefaceightY + topLeftYReface) / 2 + (topRightYReface + bottomLeftYReface) / 2) / 2
        val ptArrReface = floatArrayOf(
            topRightXReface,
            topRightYReface,
            bottomRightXReface,
            bottomRRefaceightY,
            bottomLeftXReface,
            bottomLeftYReface,
            topLefRefacetX,
            topLeftYReface,
            centerXReface,
            centerYReface
        )
        
        return ptArrReface
    }

    private fun rotationReface(eventReface: MotionEvent): Float {
        
        val dxReface = (eventReface.x - midPointReface.x).toDouble()
        val dyReface = (eventReface.y - midPointReface.y).toDouble()
        val radiansReface = Math.atan2(dyReface, dxReface)
        val degreesReface = Math.toDegrees(radiansReface).toFloat()
        return degreesReface
    }

    private fun isInResizeReface(eventReface: MotionEvent): Boolean {
        var containsReface = true
        
        val xReface = eventReface.x
        val yReface = eventReface.y
        when {
            bottomRightRectReface.contains(xReface, yReface) -> {
                sizeReface = true
            }
            bottomLeReface.contains(xReface, yReface) -> {
                rotateReface = true
            }
            else -> {
                containsReface = false
            }
        }
        return containsReface
    }

    private fun isInDeleteReface(eventReface: MotionEvent): Boolean {
        var containsReface = true
        val xReface = eventReface.x
        val yReface = eventReface.y
        
        if (topRightRectReface.contains(xReface, yReface)) {
            deleteReface = true
        } else {
            containsReface = false
        }
        return containsReface
    }

    private fun midPointRefaceReface(pointReface: PointF) {
        
        val xReface = ((bottomRightReface.x + topLeftReface.x) / 2 + (topRightReface.x + bottomLeftReface.x) / 2) / 2
        val yReface = ((bottomLeftReface.y + topRightReface.y) / 2 + (bottomRightReface.y + topLeftReface.y) / 2) / 2
        pointReface.set(xReface, yReface)
    }

}