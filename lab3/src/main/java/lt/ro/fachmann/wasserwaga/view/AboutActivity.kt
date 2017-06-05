package lt.ro.fachmann.wasserwaga.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AccelerateInterpolator
import kotlinx.android.synthetic.main.activity_about.*
import lt.ro.fachmann.wasserwaga.R
import org.jetbrains.anko.toast

class AboutActivity : AppCompatActivity() {
    //private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        aboutFaceImage.animate()
                .rotation(-180.0f)
                .setInterpolator(AccelerateInterpolator())
                .setDuration(IMAGE_ANIMATION_DURATION)
                .setStartDelay(IMAGE_ANIMATION_DELAY)
        //toast = Toast.makeText(applicationContext, "", Toast.LENGTH_LONG)
    }

    fun openPersonalSite() {
        try {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_site)))
            startActivity(browserIntent)
        } catch (e: ActivityNotFoundException) {
            toast(R.string.share_error)
        }

    }

//    private fun showToast(message: String) {
//        toast!!.setText(message)
//        if (!toast!!.view.isShown) {
//            toast!!.show()
//        }
//    }
//
//    private fun showToast(resId: Int) {
//        showToast(getString(resId))
//    }

    companion object {
        val IMAGE_ANIMATION_DURATION = 1000L
        val IMAGE_ANIMATION_DELAY = 500L
    }

}
