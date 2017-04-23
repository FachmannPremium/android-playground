package lt.ro.fachmann.lab2.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import lt.ro.fachmann.lab2.Movie
import lt.ro.fachmann.lab2.R
import lt.ro.fachmann.lab2.adapters.MoviesAdapter
import org.jetbrains.anko.startActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        var init: Boolean = false
        val movieList = prepareMovieData()
        fun prepareMovieData(): ArrayList<Movie> {
            if (init) return movieList
            val movies = arrayListOf(
                    Movie("American Psycho", "Action & Adventure", "2000", "A wealthy New York investment banking executive hides his alternate psychopathic ego from his co-workers and friends as he delves deeper into his violent, hedonistic fantasies.", R.drawable.poster),
                    Movie("Mad Max: Fury Road", "Action & Adventure", "2015", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Inside Out", "Animation, Kids & Family", "2015", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Shaun the Sheep", "Animation", "2015", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("The Martian", "Science Fiction & Fantasy", "2015", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Mission: Impossible Rogue Nation", "Action", "2015", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Up", "Animation", "2009", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Star Trek", "Science Fiction", "2009", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("The LEGO Movie", "Animation", "2014", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Iron Man", "Action & Adventure", "2008", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Aliens", "Science Fiction", "1986", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Chicken Run", "Animation", "2000", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Back to the Future", "Science Fiction", "1985", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Raiders of the Lost Ark", "Action & Adventure", "1981", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Goldfinger", "Action & Adventure", "1965", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster),
                    Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014", " bla blalsdalsdafjnwiu  uwui efweuif weuifweuifwefiu we w w eui  wefui weuif weui weiu wei uweiu fweui fweui wefui weifuh wefihwe w we i w eiwe hweuifwheuifwh wei uwefuw", R.drawable.poster)
            )
            movies.sortWith(compareBy({ it.year }))
            init = true
            return movies
        }
    }

    lateinit var adapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        adapter = MoviesAdapter(movieList) {
            startActivity<MovieDetailsActivity>(MovieDetailsActivity.MOVIE to it)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val simpleItemTouchCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.remove(viewHolder, recyclerView)
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity<AboutActivity>()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
