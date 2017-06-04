package lt.ro.fachmann.wasserwaga.game

import android.content.Context
import lt.ro.fachmann.wasserwaga.R
import lt.ro.fachmann.wasserwaga.gl.GameRenderer
import lt.ro.fachmann.wasserwaga.gl.TexturedRectangle
import javax.microedition.khronos.opengles.GL10

/**
 * Created by bartl on 04.06.2017.
 */
class Game(context: Context) {
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


    fun enable(gl: GL10, context: Context) {
        monk.loadGLTexture(gl, context)
        background.loadGLTexture(gl, context)
        gameOver.loadGLTexture(gl, context)
        arena.loadGLTexture(gl, context)
    }

    fun update() {
        val currentTimeMillis = System.currentTimeMillis()
        if (infoTill <= currentTimeMillis) {
            monk.update()
            if (monk.radiusFromMiddle() >= 1.2) {
                monk.reset()
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
            gl.glTranslatef(0.0f, -3.0f, GameRenderer.DRAW_LAYER)
            gameOver.render(gl)
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
    }
}