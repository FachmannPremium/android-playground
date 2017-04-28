package lt.ro.fachmann.lab2.utils

import android.animation.Animator

/**
 * Created by bartl on 23.04.2017.
 * With this Listener its not necessary to override all methods
 */
abstract class SimpleAnimatorListener : Animator.AnimatorListener {

    override fun onAnimationStart(animation: Animator) {
    }

    override fun onAnimationRepeat(animation: Animator) {
    }

    override fun onAnimationEnd(animation: Animator) {
    }

    override fun onAnimationCancel(animation: Animator) {
    }

}
