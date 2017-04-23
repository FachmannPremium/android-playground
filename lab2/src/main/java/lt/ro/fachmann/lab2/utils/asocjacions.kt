package lt.ro.fachmann.lab2.utils

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Created by bartl on 22.04.2017.
 */


inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}
