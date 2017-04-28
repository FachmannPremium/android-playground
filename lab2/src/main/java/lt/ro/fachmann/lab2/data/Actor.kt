package lt.ro.fachmann.lab2.data

import android.support.annotation.DrawableRes

/**
 * Created by bartl on 28.04.2017.
 */
class Actor(val firstName: String = "",
            val name: String = "",
            @DrawableRes val photoId: Int = -1) {
    override fun toString() = "Actor(firstName='$firstName', name='$name')"
}