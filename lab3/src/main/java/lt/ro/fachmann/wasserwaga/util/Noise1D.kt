package lt.ro.fachmann.wasserwaga.util

/**
 * Created by bartl on 01.06.2017.
 */
abstract class Noise1D(var amplitude: Double = 1.0, var scale: Double = 1.0) {
    abstract fun next(time: Double): Double
}

class Noise1DLimited(amplitude: Double = 1.0, scale: Double = 1.0) : lt.ro.fachmann.wasserwaga.util.Noise1D(amplitude, scale) {
    val MAX_VERTICES = 256
    val MAX_VERTICES_MASK = MAX_VERTICES - 1
    val randomData = Array(MAX_VERTICES) {
        Math.random()
    }

    override fun next(time: Double): Double {
        val scaledX = time * scale
        val xFloor = scaledX.toInt()

        // Modulo using &
        val xMin = xFloor and MAX_VERTICES_MASK
        val xMax = (xMin + 1) and MAX_VERTICES_MASK

        val t = scaledX - xFloor
        val tRemapSmoothStep = t * t * (3 - 2 * t)

        val y = lerp(randomData[xMin], randomData[xMax], tRemapSmoothStep)

        return y * amplitude
    }

    /**
     * Linear interpolation function.
     * @param v0 The lower integer value
     * @param v1 The upper integer value
     * @param t The value between the two
     * @returns {number}
     */
    fun lerp(v0: Double, v1: Double, t: Double) = v0 * (1 - t) + v1 * t
}

class Noise1DUnlimited(amplitude: Double = 1.0, scale: Double = 1.0) : lt.ro.fachmann.wasserwaga.util.Noise1D(amplitude, scale) {
    var value: Double = Math.random() * amplitude
    val noiseLimited = lt.ro.fachmann.wasserwaga.util.Noise1DLimited(1.0, scale)

    override fun next(time: Double): Double {
        value += (noiseLimited.next(time) - 0.5) * scale
        return value % amplitude
    }
}
