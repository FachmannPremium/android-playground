package lt.ro.fachmann.wasserwaga.util

/**
 * Created by bartl on 04.06.2017.
 */
fun nextPowerOf2(n: Int): Int {
    var result = 1
    while (result < n)
        result *= 2
    return result
}