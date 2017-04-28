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
        val pageWidth = view.width

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.alpha = 0.0f

        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the left page
            view.alpha = 1.0f
            view.translationX = 0.0f
            view.scaleX = 1.0f
            view.scaleY = 1.0f

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.alpha = 1 - position

            // Counteract the default slide transition
            view.translationX = pageWidth * -position

            // Scale the page down (between MIN_SCALE and 1)
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.alpha = 0.0f
        }
    }
}