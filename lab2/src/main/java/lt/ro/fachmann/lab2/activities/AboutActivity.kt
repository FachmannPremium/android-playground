package lt.ro.fachmann.lab2.activities

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*
import lt.ro.fachmann.lab2.R


class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val uri = Uri.parse("android.resource://lt.ro.fachmann.lab2/" + R.raw.reksio)
        aboutBackgroundVideo.setVideoURI(uri)
        aboutBackgroundVideo.setOnPreparedListener { mp -> mp.isLooping = true }
        aboutBackgroundVideo.start()
        aboutBackgroundVideo.requestFocus()
        aboutBackgroundVideo

    }
}
