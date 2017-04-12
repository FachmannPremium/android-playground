package lt.ro.fachmann.lab2

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.movie_list_row.view.*


/**
 * Created by bartl on 12.04.2017.
 */
class MoviesAdapter(private val moviesList: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindMovie(movie: Movie) {
            itemView.title?.text = movie.title
            itemView.genre?.text = movie.genre
            itemView.year?.text = movie.year
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_list_row, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }
}
