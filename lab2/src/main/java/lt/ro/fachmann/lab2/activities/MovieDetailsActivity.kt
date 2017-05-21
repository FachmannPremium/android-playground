package lt.ro.fachmann.lab2.activities

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_movie_details.*
import lt.ro.fachmann.lab2.R
import lt.ro.fachmann.lab2.activities.fragments.MovieImagesFragment
import lt.ro.fachmann.lab2.activities.fragments.MovieMainFragment
import lt.ro.fachmann.lab2.data.Movie
import lt.ro.fachmann.lab2.data.MoviesDataBase
import lt.ro.fachmann.lab2.utils.DepthPageTransformer


class MovieDetailsActivity : AppCompatActivity() {
    companion object {
        val NUM_PAGES = 2
        val MOVIE_INDEX = "MovieDetailsActivity:movieIndex"
    }

    lateinit var movie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val movieIndex = intent.getIntExtra(MOVIE_INDEX, -1)
        movie = MoviesDataBase.movieList[movieIndex]

        setSupportActionBar(toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = movie.title

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)
        viewPagerDetails.adapter = pagerAdapter
        viewPagerDetails.setPageTransformer(true, DepthPageTransformer())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_movie_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_swap -> {
            val newPage = (viewPagerDetails.currentItem + 1) % 2
            viewPagerDetails.currentItem = newPage
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int) = when (position) {
            0 -> MovieMainFragment()
            else -> MovieImagesFragment()
        }

        override fun getCount() = NUM_PAGES
    }
}
