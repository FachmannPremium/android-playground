package lt.ro.fachmann.lab2.activities

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*
import lt.ro.fachmann.lab2.R
import org.jetbrains.anko.imageResource


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val uri = Uri.parse("android.resource://lt.ro.fachmann.lab2/" + R.raw.reksio)
        aboutBackgroundVideo.setVideoURI(uri)
        aboutBackgroundVideo.setOnPreparedListener { mp -> mp.isLooping = true }
        aboutBackgroundVideo.start()
        aboutBackgroundVideo.requestFocus()

    }

    override fun onResume() {
        super.onResume()
        aboutBackgroundVideo.start()
    }

    var b: Boolean = true
    fun flipCard(view: View) {
        testImage1.rotationY = 0f
        testImage1.animate().rotationY(90f).setListener(object : AnimatorListener {

            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                b = !b
                testImage1.imageResource = if (b) R.drawable.ic_movie_seen_layer else R.drawable.ic_movie_unseen_layer
                testImage1.rotationY = 270f
                testImage1.animate().rotationY(360f).setListener(null)

            }

            override fun onAnimationCancel(animation: Animator) {}
        })
    }
}
