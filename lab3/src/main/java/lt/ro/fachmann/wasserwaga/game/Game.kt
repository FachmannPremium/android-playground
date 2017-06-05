package lt.ro.fachmann.wasserwaga.game

import android.content.Context
import android.view.MotionEvent
import android.widget.TextView
import lt.ro.fachmann.wasserwaga.R
import lt.ro.fachmann.wasserwaga.gl.TexturedRectangle
import org.jetbrains.anko.runOnUiThread
import java.util.*
import javax.microedition.khronos.opengles.GL10

/**
 * Created by bartl on 04.06.2017.
 */
class Game(val context: Context, val fromStart: TextView) {
    val levels = arrayOf(
            Level(context.getString(R.string.level_0), 0.0, 0.0, true),
            Level(context.getString(R.string.level_1), 0.015, 0.05),
            Level(context.getString(R.string.level_2), 0.02, 0.15),
            Level(context.getString(R.string.level_3), 0.03, 0.25),
            Level(context.getString(R.string.level_4), 0.07, 0.85))

    val monk = FloatingDestinationTexturedRectangle(R.drawable.monk)
    val background = DestinationTexturedRectangle(R.drawable.game_background, 3.5f, 7.0f)
    val arena = TexturedRectangle(R.drawable.arena, 2.0f, 2.0f)
    val gameOver = TexturedRectangle(R.drawable.game_over, 2.0f, 0.5f)

    var infoTill = 0L
    var freeMode = false

    fun enable(gl: GL10, context: Context) {
        monk.loadGLTexture(gl, context)
        background.loadGLTexture(gl, context)
        arena.loadGLTexture(gl, context)
        gameOver.loadGLTexture(gl, context)
        gameOver.y = 3.0f
    }

    fun update() {
        val currentTimeMillis = System.currentTimeMillis()
        if (infoTill <= currentTimeMillis) {
            monk.update()
            if (!freeMode && monk.radiusFromMiddle() >= 1.2) {
                monk.reset(3000)
                infoTill = currentTimeMillis + 3000
            }
        }
        background.update()
    }

    fun render(gl: GL10) {
        background.render(gl)
        arena.render(gl)
        monk.render(gl)
        if (System.currentTimeMillis() < infoTill) {
            gameOver.render(gl)
        } else {
            context.runOnUiThread {
                fromStart.text = String.format(Locale.getDefault(), " %.2f s", (System.currentTimeMillis() - monk.startMillis) / 1000.0f)
            }
        }
    }

    fun updateSensorReading(x: Float, y: Float) {
        monk.destinationX = -x
        monk.destinationY = -y
        background.destinationX = x / 15
        background.destinationY = y / 15
    }

    fun setLevel(level: Level) {
        monk.setLevel(level)
        monk.reset()
    }

    fun touch(event: MotionEvent) {
        val currentTimeMillis = System.currentTimeMillis()
        if (infoTill > currentTimeMillis) {
            infoTill = currentTimeMillis - 1
            monk.reset()
        }
    }
}