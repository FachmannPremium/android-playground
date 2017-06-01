package lt.ro.fachmann.wasserwaga

/**
 * Created by bartl on 01.06.2017.
 */
abstract class Noise1D(var amplitude: Double = 1.0, var scale: Double = 1.0) {
    abstract fun next(x: Double): Double
}

class Noise1DLimited(amplitude: Double = 1.0, scale: Double = 1.0) : Noise1D(amplitude, scale) {
    val MAX_VERTICES = 256
    val MAX_VERTICES_MASK = MAX_VERTICES - 1
    val r = Array(MAX_VERTICES) {
        Math.random()
    }

    override fun next(x: Double): Double {
        val scaledX = x * scale
        val xFloor = scaledX.toInt()

        // Modulo using &
        val xMin = xFloor and MAX_VERTICES_MASK
        val xMax = (xMin + 1) and MAX_VERTICES_MASK

        val t = scaledX - xFloor
        val tRemapSmoothStep = t * t * (3 - 2 * t)

        val y = lerp(r[xMin], r[xMax], tRemapSmoothStep)

        return y * amplitude
    }

    /**
     * Linear interpolation function.
     * @param a The lower integer value
     * @param b The upper integer value
     * @param t The value between the two
     * @returns {number}
     */
    fun lerp(a: Double, b: Double, t: Double) = a * (1 - t) + b * t
}

class Noise1DUnlimited(amplitude: Double = 1.0, scale: Double = 1.0) : Noise1D(amplitude, scale) {
    var value: Double = Math.random() * amplitude
    val noiseLimited = Noise1DLimited(1.0, scale)

    override fun next(x: Double): Double {
        value += (noiseLimited.next(x) - 0.5) * scale
        return value % amplitude
    }
}