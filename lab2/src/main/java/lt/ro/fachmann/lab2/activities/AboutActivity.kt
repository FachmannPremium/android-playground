package lt.ro.fachmann.lab2.activities

import android.animation.Animator


import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_about.*
import lt.ro.fachmann.lab2.R
import lt.ro.fachmann.lab2.utils.SimpleAnimatorListener
import org.jetbrains.anko.imageResource

class AboutActivity : AppCompatActivity() {
    var sound: MediaPlayer? = null
    var seen: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        initPlayer()

        sound = MediaPlayer.create(applicationContext, R.raw.reksio_music)
        sound?.isLooping = true
    }

    override fun onStart() {
        super.onStart()
        sound?.start()
    }

    override fun onResume() {
        super.onResume()
        aboutBackgroundVideo.start()
        sound?.start()
    }


    override fun onPause() {
        super.onPause()
        sound?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        sound?.stop()
    }

    fun initPlayer() {
        val uri = Uri.parse("android.resource://lt.ro.fachmann.lab2/" + R.raw.reksio)
        aboutBackgroundVideo.setVideoURI(uri)
        aboutBackgroundVideo.setOnPreparedListener { mp -> mp.isLooping = true }
        aboutBackgroundVideo.start()
        aboutBackgroundVideo.requestFocus()
    }

    fun flipCard(view: View) {
        seen = !seen
        if (seen)
            sound?.start()
        else {

            sound?.pause()
        }
        testImage1.rotationY = 0f
        testImage1.animate().rotationY(90f).setListener(object : SimpleAnimatorListener() {
            override fun onAnimationEnd(animation: Animator) {
                testImage1.imageResource = if (seen) R.drawable.ic_movie_seen_layer else R.drawable.ic_movie_unseen_layer
                testImage1.rotationY = 270f
                testImage1.animate().rotationY(360f).setListener(null)
            }
        })
    }
}
