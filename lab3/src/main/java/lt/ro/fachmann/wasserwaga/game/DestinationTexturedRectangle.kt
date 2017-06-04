package lt.ro.fachmann.wasserwaga.game

import android.support.annotation.DrawableRes
import lt.ro.fachmann.wasserwaga.gl.TexturedRectangle

open class DestinationTexturedRectangle(@DrawableRes resId: Int, width: Float = 1.0f, height: Float = 1.0f)
    : TexturedRectangle(resId, width, height) {
    var destinationX: Float = 0.0f
    var destinationY: Float = 0.0f

    open fun update() {
        x += (destinationX - x) / 30
        y += (destinationY - y) / 30
    }
}