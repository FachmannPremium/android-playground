package lt.ro.fachmann.lab2

/**
 * Created by bartl on 12.04.2017.
 */
class Movie(var title: String = "", var genre: String = "", var year: String = "") {
    override fun toString(): String {
        return "Movie(title='$title', genre='$genre', year='$year')"
    }
}
