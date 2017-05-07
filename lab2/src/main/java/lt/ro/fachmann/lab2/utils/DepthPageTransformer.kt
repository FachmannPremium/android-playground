package lt.ro.fachmann.lab2.utils

import android.support.v4.view.ViewPager
import android.view.View


/**
 * Created by bartl on 27.04.2017.
 */
class DepthPageTransformer : ViewPager.PageTransformer {
    companion object {
        private val MIN_SCALE = 0.75f
    }

    override fun transformPage(view: View, position: Float) {
        when {
            position < -1 -> view.alpha = 0.0f
            position <= 0 -> {
                view.alpha = 1.0f
                view.translationX = 0.0f
                view.scaleX = 1.0f
                view.scaleY = 1.0f
            }
            position <= 1 -> {
                view.alpha = 1 - position
                view.translationX = view.width * -position
                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
            else -> view.alpha = 0.0f
        }
    }
}