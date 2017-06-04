package lt.ro.fachmann.wasserwaga.gl

import android.content.Context
import android.opengl.GLES10
import android.opengl.GLSurfaceView
import android.opengl.GLU
import lt.ro.fachmann.wasserwaga.game.Game
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * Created by bartl on 30.05.2017.
 */


class GameRenderer(val game: Game, private val context: Context) : GLSurfaceView.Renderer {
    companion object {
        val DRAW_LAYER = -10.0f
    }

    /** Constructor to set the handed over context  */

    override fun onDrawFrame(gl: GL10) {
        // clear Screen and Depth Buffer
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        game.update()
        game.render(gl)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        val properHeight = if (height > 0) height else 1//Prevent A Divide By Zero By
        // Making Height Equal One

        gl.glViewport(0, 0, width, properHeight)    //Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION)    //Select The Projection Matrix
        gl.glLoadIdentity()                    //Reset The Projection Matrix

        //Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, 45.0f, width.toFloat() / properHeight.toFloat(), 0.1f, 100.0f)

        gl.glMatrixMode(GL10.GL_MODELVIEW)    //Select The Modelview Matrix
        gl.glLoadIdentity()                    //Reset The Modelview Matrix
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        game.enable(gl, context)

        gl.glDepthFunc(GL10.GL_NEVER)

        gl.glEnable(GL10.GL_TEXTURE_2D)            //Enable Texture Mapping ( NEW )
        GLES10.glEnable(GLES10.GL_ALPHA_TEST)
        GLES10.glAlphaFunc(GLES10.GL_GREATER, 0.0f)

        gl.glEnable(GL10.GL_BLEND)
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA)

        gl.glShadeModel(GL10.GL_SMOOTH)            //Enable Smooth Shading
        gl.glClearColor(1.0f, 1.0f, 0.0f, 0.0f)    //YELLow Background

//        gl.glClearDepthf(1.0f)                    //Depth Buffer Setup
//        gl.glEnable(GL10.GL_DEPTH_TEST)            //Enables Depth Testing
//        gl.glDepthFunc(GL10.GL_LEQUAL)            //The Type Of Depth Testing To Do

        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST) //Really Nice Perspective Calculations

    }

}
