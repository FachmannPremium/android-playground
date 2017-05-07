package lt.ro.fachmann.lab2.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import lt.ro.fachmann.lab2.R
import lt.ro.fachmann.lab2.adapters.MoviesAdapter
import lt.ro.fachmann.lab2.data.MoviesDataBase
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    lateinit var adapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setUpRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            startActivity<AboutActivity>()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    fun setUpRecyclerView() {
        adapter = MoviesAdapter(MoviesDataBase.movieList) {
            startActivity<MovieDetailsActivity>(MovieDetailsActivity.MOVIE_INDEX to MoviesDataBase.movieList.indexOf(it))
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = DefaultItemAnimator()
        createItemTouchHelper().attachToRecyclerView(recyclerView)
        adapter.notifyDataSetChanged()
    }

    fun createItemTouchHelper() = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?) = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            adapter.remove(viewHolder, recyclerView)
        }
    })
}
