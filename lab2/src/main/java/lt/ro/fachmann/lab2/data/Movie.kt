package lt.ro.fachmann.lab2.data

import android.support.annotation.DrawableRes

/**
 * Created by bartl on 12.04.2017.
 */
class Movie(val title: String = "",
            val genre: String = "",
            val year: String = "",
            val description: String = "",
            val actors: List<Actor> = ArrayList(),
            @DrawableRes val posterId: Int = -1,
            val imagesIds: List<Int> = ArrayList(),
            var seen: Boolean = false,
            var rating: Float = 0.0f) {
    override fun toString() = "Movie(title=$title, genre='$genre', year='$year')"
}
