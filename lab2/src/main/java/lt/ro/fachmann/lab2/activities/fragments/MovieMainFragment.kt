package lt.ro.fachmann.lab2.activities.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_movie_main.*
import lt.ro.fachmann.lab2.R
import lt.ro.fachmann.lab2.activities.MovieDetailsActivity
import lt.ro.fachmann.lab2.data.Movie
import lt.ro.fachmann.lab2.utils.inflate
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.onRatingBarChange

/**
 * Created by bartl on 27.04.2017.
 */

class MovieMainFragment : Fragment() {
    lateinit var movie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_movie_main)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movie = (activity as MovieDetailsActivity).movie
        bindMovie()
    }

    fun bindMovie() = with(movie) {
        yearDetails.text = year
        descriptionDetails.text = description
        posterDetails.imageResource = posterId
        posterBackgroundDetails.imageResource = posterId
        ratingBarDetails.rating = rating
        ratingBarDetails.onRatingBarChange { _, rating, fromUser ->
            run {
                if (fromUser) movie.rating = rating
            }
        }
    }
}