package lt.ro.fachmann.lab2

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.movie_list_row_even.view.*
import kotlinx.android.synthetic.main.movie_list_row_odd.view.*
import org.jetbrains.anko.onClick

class MoviesAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    abstract class MovieViewHolder(itemView: View, var movie: Movie? = null) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.onClick { Toast.makeText(it?.context, movie?.toString(), Toast.LENGTH_SHORT).show() }
        }

        open fun bindMovie(movie: Movie) {
            this.movie = movie
        }
    }

    class MovieViewHolderEven(itemView: View) : MovieViewHolder(itemView) {
        override fun bindMovie(movie: Movie) {
            super.bindMovie(movie)
            itemView.title_even?.text = movie.title
            itemView.genre_even?.text = movie.genre
            itemView.year_even?.text = movie.year
        }
    }

    class MovieViewHolderOdd(itemView: View) : MovieViewHolder(itemView) {
        override fun bindMovie(movie: Movie) {
            super.bindMovie(movie)
            itemView.title_odd?.text = movie.title
            itemView.genre_odd?.text = movie.genre
            itemView.year_odd?.text = movie.year
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        when (viewType) {
            0 -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row_even, parent, false)
                return MovieViewHolderEven(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row_odd, parent, false)
                return MovieViewHolderOdd(itemView)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindMovie(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
