package lt.ro.fachmann.lab2

import android.support.annotation.DrawableRes
import java.io.Serializable

/**
 * Created by bartl on 12.04.2017.
 */
class Movie(val title: String = "",
            val genre: String = "",
            val year: String = "",
            val description: String = "",
            @DrawableRes val posterId: Int = -1,
            var seen: Boolean = false) : Serializable {
    override fun toString() = "Movie(title=$title, genre='$genre', year='$year')"
}
