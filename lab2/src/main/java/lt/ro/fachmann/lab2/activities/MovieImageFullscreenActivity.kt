package lt.ro.fachmann.lab2.activities

import android.app.Activity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_movie_image_fullscreen.*
import lt.ro.fachmann.lab2.R
import org.jetbrains.anko.imageResource

class MovieImageFullscreenActivity : Activity() {
    companion object {
        val RESOURCE_ID = "MovieImageFullscreenActivity:resourceId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_image_fullscreen)

        val resourceId = intent.getIntExtra(RESOURCE_ID, -1)
        if (resourceId != -1) movieImageFullscreen.imageResource = resourceId
    }
}
