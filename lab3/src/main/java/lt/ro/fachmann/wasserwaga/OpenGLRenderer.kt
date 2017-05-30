package lt.ro.fachmann.wasserwaga

import android.opengl.GLSurfaceView
import android.opengl.GLU
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * Created by bartl on 30.05.2017.
 */
class OpenGLRenderer : GLSurfaceView.Renderer {
    private val square: Square = Square()

    var x: Float = 0.0f
        set(value) {
            field = field / 2 + value
        }

    var y: Float = 0.0f
        set(value) {
            field = field / 2 + value
        }


    override fun onDrawFrame(gl: GL10) {
        // Czyszczenie bufora głębokości.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity()
        // Translates 10 units into the screen.
        gl.glTranslatef(x, y, -10.0f)

        // SQUARE A
        // Save the current matrix.
        gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f)
        gl.glPushMatrix()
        // Rotate square A counter-clockwise.
        gl.glRotatef(0.0f, 0.0f, 0.0f, 1.0f)
        // Draw square A.
        square.draw(gl)
        // Restore the last matrix.
        gl.glPopMatrix()

    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        gl.glViewport(0, 0, width, height)
        // Wybranie macierzy projekcji
        gl.glMatrixMode(GL10.GL_PROJECTION)
        // Wyczyszczenie macierzy projekcji
        gl.glLoadIdentity()
        // Ustawienie perspektywy
        GLU.gluPerspective(gl, 45.0f, width.toFloat() / height.toFloat(), 0.1f, 100.0f)
        // Wybranie macierzy widoku
        gl.glMatrixMode(GL10.GL_MODELVIEW)
        // Wyczyszczenie macierzy widoku
        gl.glLoadIdentity()
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig?) {
        // Ustawienie koloru tła na czarny ( rgba ).
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f)
        // Włączenie cieniowania
        gl.glShadeModel(GL10.GL_SMOOTH)
        // Ustawienie bufora głębokości
        gl.glClearDepthf(1.0f)
        // Włączenie testowania bufora głębokości
        gl.glEnable(GL10.GL_DEPTH_TEST)
        gl.glDepthFunc(GL10.GL_LEQUAL)
        // Ustawienia dotyczące perspektywy
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST)
    }
}