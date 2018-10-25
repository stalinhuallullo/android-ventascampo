package dev.lstr.llevateclaro.presentation.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView

/**
 * Created by Alejandro on 2/03/2018.
 * ImageView that keeps aspect ratio when scaled
 * https://stackoverflow.com/questions/18077325/scale-image-to-fill-imageview-width-and-keep-aspect-ratio
 */
class ScaleImageView : ImageView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        try {
            val drawable = drawable
            if (drawable == null) {
                setMeasuredDimension(0, 0)
            } else {
                val measuredWidth = View.MeasureSpec.getSize(widthMeasureSpec)
                val measuredHeight = View.MeasureSpec.getSize(heightMeasureSpec)
                if (measuredHeight == 0 && measuredWidth == 0) { //Height and width set to wrap_content
                    setMeasuredDimension(measuredWidth, measuredHeight)
                } else if (measuredHeight == 0) { //Height set to wrap_content
                    val height = measuredWidth * drawable.intrinsicHeight / drawable.intrinsicWidth
                    setMeasuredDimension(measuredWidth, height)
                } else if (measuredWidth == 0) { //Width set to wrap_content
                    val width = measuredHeight * drawable.intrinsicWidth / drawable.intrinsicHeight
                    setMeasuredDimension(width, measuredHeight)
                } else { //Width and height are explicitly set (either to match_parent or to exact value)
                    setMeasuredDimension(measuredWidth, measuredHeight)
                }
            }
        } catch (e: Exception) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }

    }
}