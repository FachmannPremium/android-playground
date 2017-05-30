package lt.ro.fachmann.wasserwaga

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10


/**
 * Created by bartl on 30.05.2017.
 */

class Square {
    val size = 0.5f
    // Our vertices.
    private val vertices = floatArrayOf(
            -size, size, 0.0f, // 0, Top Left
            -size, -size, 0.0f, // 1, Bottom Left
            size, -size, 0.0f, // 2, Bottom Right
            size, size, 0.0f)// 3, Top Right
    // The order we like to connect them.
    private val indices = shortArrayOf(0, 1, 2, 0, 2, 3)
    // Our vertex buffer.
    private val vertexBuffer: FloatBuffer
    // Our index buffer.
    private val indexBuffer: ShortBuffer

    init {
        // a float is 4 bytes, therefore we multiply the number if vertices with 4.
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder())
        vertexBuffer = vbb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)
        // short is 2 bytes, therefore we multiply the number if vertices with 2.
        val ibb = ByteBuffer.allocateDirect(indices.size * 2)
        ibb.order(ByteOrder.nativeOrder())
        indexBuffer = ibb.asShortBuffer()
        indexBuffer.put(indices)
        indexBuffer.position(0)
    }

    //load our texture(s)
    fun loadTexture(gl: GL10, context: Context, resource: Int) {
        val bmp = BitmapFactory.decodeResource(context.resources, resource)
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0)
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR)
        gl.glTexParameterx(GL10.GL_TEXTURE_2D,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR)
        bmp.recycle()
    }


    /*This function draws our square on screen. @param gl*/
    fun draw(gl: GL10) {
        // Counter-clockwise winding.
        gl.glFrontFace(GL10.GL_CCW)
        // Enable face culling.
        gl.glEnable(GL10.GL_CULL_FACE)
        // What faces to remove with the face culling.
        gl.glCullFace(GL10.GL_BACK)
        // Enabled the vertices buffer for writing and to be used during rendering.
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        // Specifies the location and data format of an array of vertex
        // coordinates to use when rendering.
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glDrawElements(GL10.GL_TRIANGLES, indices.size, GL10.GL_UNSIGNED_SHORT, indexBuffer)
        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE)
    }
}
