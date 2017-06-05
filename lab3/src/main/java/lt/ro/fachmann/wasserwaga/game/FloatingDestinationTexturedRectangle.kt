package lt.ro.fachmann.wasserwaga.game

import android.support.annotation.DrawableRes
import lt.ro.fachmann.wasserwaga.util.Noise1DLimited
import lt.ro.fachmann.wasserwaga.util.Noise1DUnlimited

/**
 * Created by bartl on 04.06.2017.
 */
class FloatingDestinationTexturedRectangle(@DrawableRes resId: Int, width: Float = 1.0f, height: Float = 1.0f)
    : DestinationTexturedRectangle(resId, width, height) {
    var currentX: Float = 0.0f
    var currentY: Float = 0.0f

//    var angle: Double = 0.0
//    var speed: Double = 0.0

    var angleNoise = Noise1DUnlimited(2 * Math.PI, 0.0)
    var radiusNoise = Noise1DLimited(6.0, 0.0)

    var startMillis = System.currentTimeMillis()

    var floating = true

    override fun update() {
        val fromStartSeconds = (System.currentTimeMillis() - startMillis).toDouble() / 1000.0
        val shiftAngle = angleNoise.next(fromStartSeconds)
        var shiftRadius = radiusNoise.next(fromStartSeconds) - 3.0
        if (fromStartSeconds < 5.0) {
            shiftRadius /= 6.0 - fromStartSeconds
        }

        val shiftX = (shiftRadius * Math.cos(shiftAngle)).toFloat()
        val shiftY = (shiftRadius * Math.sin(shiftAngle)).toFloat()
        //Log.i("poke", fromStartSeconds.toString())
//        val dY = (destinationY - currentY).toDouble()
//        val dX = (destinationX - currentX).toDouble()
//        val destAngle = Math.atan2(dY, dX)
//        val destSpeed = Math.hypot(dX, dY)
//        angle += (destAngle - angle) / 2
//        speed = (destSpeed - speed) / 70
//
//        Log.i("Poke", "$angle ; $speed")


//        val newDX = (speed * Math.cos(angle)).toFloat()
//        val newDY = (speed * Math.sin(angle)).toFloat()
//        currentX += newDX
//        currentY += newDY
//
        currentX += (destinationX - currentX) / 30
        currentY += (destinationY - currentY) / 30
        x = currentX
        y = currentY
        if (floating) {
            x -= shiftX
            y -= shiftY
        }
    }

    fun radiusFromMiddle() = Math.hypot(x.toDouble(), y.toDouble())

    fun setLevel(level: Level) {
        floating = !level.off
        angleNoise.scale = level.angleScale
        radiusNoise.scale = level.radiusScale
    }

    fun reset(delay: Int = 0) {
        currentX = 0.0f
        currentY = 0.0f
        startMillis = System.currentTimeMillis() + delay
    }
}