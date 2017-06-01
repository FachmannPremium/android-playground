package lt.ro.fachmann.wasserwaga

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLUtils
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10


class Monk {
    var currentX: Float = 0.0f
    var currentY: Float = 0.0f

    var destinationX: Float = 0.0f
    var destinationY: Float = 0.0f

    val angleNoise = Noise1DUnlimited(360.0, 2.0)
    val radiusNoise = Noise1DLimited(6.0, 0.3)

    val start = System.currentTimeMillis()

    private val edgeSize = 1.0f
    private val vertexBuffer: FloatBuffer    // buffer holding the vertices
    private val vertices = floatArrayOf(
            -edgeSize, -edgeSize, 0.0f, // V1 - bottom left
            -edgeSize, edgeSize, 0.0f, // V2 - top left
            edgeSize, -edgeSize, 0.0f, // V3 - bottom right
            edgeSize, edgeSize, 0.0f // V4 - top right
    )

    private val textureBuffer: FloatBuffer    // buffer holding the texture coordinates
    private val texture = floatArrayOf(
            // Mapping coordinates for the vertices
            0.0f, 1.0f, // top left		(V2)
            0.0f, 0.0f, // bottom left	(V1)
            1.0f, 1.0f, // top right	(V4)
            1.0f, 0.0f // bottom right	(V3)
    )

    /** The texture pointer  */
    private val textures = IntArray(1)

    init {
        val byteBufferVertex = ByteBuffer.allocateDirect(vertices.size * 4) // a float has 4 bytes so we allocate for each coordinate 4 bytes
        byteBufferVertex.order(ByteOrder.nativeOrder())
        vertexBuffer = byteBufferVertex.asFloatBuffer() // allocates the memory from the byte buffer
        vertexBuffer.put(vertices) // fill the vertexBuffer with the vertices
        vertexBuffer.position(0) // set the cursor position to the beginning of the buffer

        val byteBufferTexture = ByteBuffer.allocateDirect(texture.size * 4)
        byteBufferTexture.order(ByteOrder.nativeOrder())
        textureBuffer = byteBufferTexture.asFloatBuffer()
        textureBuffer.put(texture)
        textureBuffer.position(0)
    }

    /**
     * Load the texture for the square
     * @param gl
     * *
     * @param context
     */
    fun loadGLTexture(gl: GL10, context: Context) {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.id) // loading texture

        gl.glGenTextures(1, textures, 0) // generate one texture pointer
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]) // ...and bind it to our array


        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST.toFloat()) // create nearest filtered texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())

        //Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
//        		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT.toFloat());
//        		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT.toFloat());


        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0) // Use Android GLUtils to specify a two-dimensional texture image from our bitmap

        bitmap.recycle() // Clean up
    }

    fun update() {

//        Log.i("poke...", "; $currentX $currentY ; $destinationX $destinationY\r")
        val crazyCall = 30
        currentX += (destinationX - currentX) / crazyCall
        currentY += (destinationY - currentY) / crazyCall
    }

    /** The render method for the square with the GL context  */
    fun render(gl: GL10) {
        val angle = angleNoise.next((System.currentTimeMillis() - start).toDouble() / 1000)
        val radius = radiusNoise.next((System.currentTimeMillis() - start).toDouble() / 1000) - 3.0
        val shiftX = (radius * Math.cos(Math.toRadians(angle))).toFloat()
        val shiftY = (radius * Math.sin(Math.toRadians(angle))).toFloat()
        Log.i("poke...", ";  $angle° $radius ; $shiftX $shiftY")


        gl.glTranslatef(currentX - shiftX, currentY - shiftY, -10.0f)


        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]) // bind the previously generated texture

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY) // Point to our buffers
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)

        gl.glFrontFace(GL10.GL_CW) // Set the face rotation

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer) // Point to our vertex buffer
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer)

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.size / 3) // Draw the vertices as triangle strip

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY) //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
    }
}