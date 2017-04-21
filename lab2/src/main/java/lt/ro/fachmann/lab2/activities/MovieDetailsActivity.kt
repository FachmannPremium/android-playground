package lt.ro.fachmann.lab2.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_movie_details.*
import lt.ro.fachmann.lab2.Movie
import lt.ro.fachmann.lab2.R
import org.jetbrains.anko.imageResource


class MovieDetailsActivity : AppCompatActivity() {

    companion object {
        val MOVIE = "MovieDetailsActivity:movie"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        appBarDetails.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            run {
                var maxOf = maxOf(map(verticalOffset, -appBarDetails.totalScrollRange, -appBarDetails.totalScrollRange + 130, 255, 0), 0)
                var color = resources.getColor(R.color.colorPrimary)
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.argb(maxOf, Color.red(color), Color.green(color), Color.blue(color))))
            }

        }

        val movie = intent.getSerializableExtra(MOVIE) as Movie
        bindMovie(movie)
    }

    fun bindMovie(movie: Movie) = with(movie) {
        supportActionBar?.title = title
        yearDetails.text = year
        descriptionDetails.text = description
        posterDetails.imageResource = posterId
        posterBackgroundDetails.imageResource = posterId
    }

    fun map(x: Int, in_min: Int, in_max: Int, out_min: Int, out_max: Int): Int {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min
    }
}
