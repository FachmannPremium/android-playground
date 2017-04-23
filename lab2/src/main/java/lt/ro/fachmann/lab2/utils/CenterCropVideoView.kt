package lt.ro.fachmann.lab2.utils

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView


/**
 * Created by bartl on 21.04.2017.
 */
class CenterCropVideoView : VideoView {
    private var leftAdjustment: Int = 0
    private var topAdjustment: Int = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val videoWidth = measuredWidth
        val videoHeight = measuredHeight

        val viewWidth = getDefaultSize(0, widthMeasureSpec)
        val viewHeight = getDefaultSize(0, heightMeasureSpec)

        leftAdjustment = 0
        topAdjustment = 0
        if (videoWidth == viewWidth) {
            val newWidth = (videoWidth.toFloat() / videoHeight * viewHeight).toInt()
            setMeasuredDimension(newWidth, viewHeight)
            leftAdjustment = -(newWidth - viewWidth) / 2
        } else {
            val newHeight = (videoHeight.toFloat() / videoWidth * viewWidth).toInt()
            setMeasuredDimension(viewWidth, newHeight)
            topAdjustment = -(newHeight - viewHeight) / 2

        }
    }

    override fun layout(l: Int, t: Int, r: Int, b: Int) {
        super.layout(l + leftAdjustment, t + topAdjustment, r + leftAdjustment, b + topAdjustment)
    }
}