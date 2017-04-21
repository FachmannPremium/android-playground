package lt.ro.fachmann.lab2.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.movie_list_row_even.view.*
import kotlinx.android.synthetic.main.movie_list_row_odd.view.*
import lt.ro.fachmann.lab2.Movie
import lt.ro.fachmann.lab2.R
import org.jetbrains.anko.imageResource

class MoviesAdapter(private val moviesList: List<Movie>,
                    private val itemClick: (Movie) -> Unit) :
        RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun getItemCount() = moviesList.size

    override fun getItemViewType(position: Int) = position % 2

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) = holder.bindMovie(moviesList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row_even, parent, false)
                return MovieViewHolderEven(itemView, itemClick)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row_odd, parent, false)
                return MovieViewHolderOdd(itemView, itemClick)
            }
        }
    }

    abstract class MovieViewHolder(itemView: View, val itemClick: (Movie) -> Unit) : RecyclerView.ViewHolder(itemView) {
        open fun bindMovie(movie: Movie) = itemView.setOnClickListener { itemClick(movie) }
    }

    class MovieViewHolderEven(itemView: View, itemClick: (Movie) -> Unit) : MovieViewHolder(itemView, itemClick) {
        override fun bindMovie(movie: Movie) = with(movie) {
            super.bindMovie(this)
            itemView.imageBackgroundEven.imageResource = posterId
            itemView.imageEven.imageResource = posterId
            itemView.titleEven?.text = title
            itemView.genreEven?.text = genre
            itemView.yearEven?.text = year
        }
    }

    class MovieViewHolderOdd(itemView: View, itemClick: (Movie) -> Unit) : MovieViewHolder(itemView, itemClick) {
        override fun bindMovie(movie: Movie) = with(movie) {
            super.bindMovie(this)
            itemView.imageBackgroundOdd.imageResource = posterId
            itemView.imageOdd.imageResource = posterId
            itemView.titleOdd.text = title
            itemView.genreOdd.text = genre
            itemView.yearOdd.text = year
        }
    }
}
