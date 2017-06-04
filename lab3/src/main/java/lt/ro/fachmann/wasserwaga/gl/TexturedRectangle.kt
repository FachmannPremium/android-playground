package lt.ro.fachmann.wasserwaga.gl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLUtils
import android.support.annotation.DrawableRes
import lt.ro.fachmann.wasserwaga.util.nextPowerOf2
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10


open class TexturedRectangle(@DrawableRes val resId: Int, width: Float = 1.0f, height: Float = 1.0f) {
    var x: Float = 0.0f
    var y: Float = 0.0f


    private val vertexBuffer: FloatBuffer    // buffer holding the vertices
    private val textureBuffer: FloatBuffer    // buffer holding the texture coordinates


    /** The texture pointer  */
    private val textures = IntArray(1)

    init {
        val vertices = floatArrayOf(
                -width, -height, 0.0f, // V1 - bottom left
                -width, height, 0.0f, // V2 - top left
                width, -height, 0.0f, // V3 - bottom right
                width, height, 0.0f // V4 - top right
        )


        val texture = floatArrayOf(
                // Mapping coordinates for the vertices
                0.0f, 1.0f, // top left		(V2)
                0.0f, 0.0f, // bottom left	(V1)
                1.0f, 1.0f, // top right	(V4)
                1.0f, 0.0f // bottom right	(V3)
        )
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
        gl.glGenTextures(1, textures, 0) // generate one texture pointer
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]) // ...and bind it to our array

        val originalBitmap = BitmapFactory.decodeResource(context.resources, resId) // loading texture
        val width = nextPowerOf2(originalBitmap.width)
        val height = nextPowerOf2(originalBitmap.height)
        val po2Bitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, false)
//        val canvas = Canvas(po2Bitmap)
//        canvas.drawBitmap(originalBitmap, 0.0f, 0.0f, Paint())

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST.toFloat()) // create nearest filtered texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())
//
//        Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT.toFloat());
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT.toFloat());


        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, po2Bitmap, 0) // Use Android GLUtils to specify a two-dimensional texture image from our bitmap

        originalBitmap.recycle() // Clean up
        po2Bitmap.recycle() // Clean up
    }

    /** The render method for the square with the GL context  */
    fun render(gl: GL10) {
        gl.glLoadIdentity()
        gl.glPushMatrix()

        gl.glEnable(GL10.GL_TEXTURE_2D)

        gl.glTranslatef(x, y, GameRenderer.DRAW_LAYER)

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]) // bind the previously generated texture

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY) // Point to our buffers
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)

        gl.glFrontFace(GL10.GL_CW) // Set the face rotation

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer) // Point to our vertex buffer
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer)

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4)//vertices.size / 3) // Draw the vertices as triangle strip

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY) //Disable the client state before leaving
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)

        gl.glPopMatrix()

        gl.glDisable(GL10.GL_TEXTURE_2D)
    }
}