package lt.ro.fachmann.lab2.utils

import android.content.Intent
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.internals.AnkoInternals

/**
 * Created by bartl on 22.04.2017.
 */


inline fun View.snack(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit) {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
}

fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}

inline fun <reified T : Any> Fragment.intentFor(vararg params: Pair<String, Any>): Intent =
        AnkoInternals.createIntent(activity, T::class.java, params)

