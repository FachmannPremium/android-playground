package lt.ro.fachmann.lab2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.toast
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mAdapter: MoviesAdapter


    private val movieList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        mAdapter = MoviesAdapter(movieList)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = mAdapter

        prepareMovieData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun prepareMovieData() {
        val movies = arrayListOf(
                Movie("Mad Max: Fury Road", "Action & Adventure", "2015"),
                Movie("Inside Out", "Animation, Kids & Family", "2015"),
                Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015"),
                Movie("Shaun the Sheep", "Animation", "2015"),
                Movie("The Martian", "Science Fiction & Fantasy", "2015"),
                Movie("Mission: Impossible Rogue Nation", "Action", "2015"),
                Movie("Up", "Animation", "2009"),
                Movie("Star Trek", "Science Fiction", "2009"),
                Movie("The LEGO Movie", "Animation", "2014"),
                Movie("Iron Man", "Action & Adventure", "2008"),
                Movie("Aliens", "Science Fiction", "1986"),
                Movie("Chicken Run", "Animation", "2000"),
                Movie("Back to the Future", "Science Fiction", "1985"),
                Movie("Raiders of the Lost Ark", "Action & Adventure", "1981"),
                Movie("Goldfinger", "Action & Adventure", "1965"),
                Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014")
        )
        movieList.addAll(movies)


        mAdapter.notifyDataSetChanged()
    }

    fun snip(view: View) {
        toast("Dymy")

    }
}
