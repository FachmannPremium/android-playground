package lt.ro.fachmann.lab2.activities.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_movie_images.*
import lt.ro.fachmann.lab2.R
import lt.ro.fachmann.lab2.activities.MovieDetailsActivity
import lt.ro.fachmann.lab2.activities.MovieImageFullscreenActivity
import lt.ro.fachmann.lab2.data.Movie
import lt.ro.fachmann.lab2.utils.inflate
import lt.ro.fachmann.lab2.utils.intentFor
import org.jetbrains.anko.onClick
import org.jetbrains.anko.singleTop


/**
 * Created by bartl on 27.04.2017.
 */

class MovieImagesFragment : Fragment() {
    lateinit var movie: Movie

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_movie_images)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movie = (activity as MovieDetailsActivity).movie
        imagesGrid.post {
            (0..imagesGrid.childCount)
                    .filter { it < movie.imagesIds.size }
                    .forEach { setUpImage(imagesGrid.getChildAt(it), movie.imagesIds[it]) }
        }

        val nameViews = listOf(movieDetailsActor1, movieDetailsActor2, movieDetailsActor3)
        val photoViews = listOf(movieDetailsActorImageView1, movieDetailsActorImageView2, movieDetailsActorImageView3)
        (0..3)
                .filter { it < movie.actors.size }
                .forEach {
                    nameViews[it].text = "${movie.actors[it].firstName} ${movie.actors[it].name}"
                    Picasso.with(context).load(movie.actors[it].photoId)
                            .centerCrop().resize(200, 200)
                            .transform(CropCircleTransformation())
                            .into(photoViews[it])
                }

    }

    fun setUpImage(view: View, resourceId: Int): Unit {
        val squareSize = (
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                    imagesGrid.width.toFloat() / imagesGrid.columnCount
                else
                    imagesGrid.height.toFloat() / imagesGrid.rowCount
                ).toInt()

        Picasso.with(context)
                .load(resourceId)
                .centerCrop().resize(squareSize, squareSize)
                .into(view as ImageView)
        view.onClick { _ -> startActivity(intentFor<MovieImageFullscreenActivity>(MovieImageFullscreenActivity.RESOURCE_ID to resourceId).singleTop()) }
    }
}