package lt.ro.fachmann.wasserwaga.gl

import javax.microedition.khronos.opengles.GL10


class Square(var x: Float = 0.0f,
             var y: Float = 0.0f,
             edgeSize: Float = 1.0f) {

    // Our vertex buffer.
    val vertexBuffer: java.nio.FloatBuffer
    // Our index buffer.
    val indexBuffer: java.nio.ShortBuffer

    init {
        // Our vertices.
        val vertices = floatArrayOf(
                -edgeSize, edgeSize, 0.0f, // 0, Top Left
                -edgeSize, -edgeSize, 0.0f, // 1, Bottom Left
                edgeSize, -edgeSize, 0.0f, // 2, Bottom Right
                edgeSize, edgeSize, 0.0f)// 3, Top Right
        // The order we like to connect them.
        val indices = shortArrayOf(0, 1, 2, 0, 2, 3)
        // a float is 4 bytes, therefore we multiply the number if vertices with 4.
        val vbb = java.nio.ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(java.nio.ByteOrder.nativeOrder())
        vertexBuffer = vbb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)
        // short is 2 bytes, therefore we multiply the number if vertices with 2.
        val ibb = java.nio.ByteBuffer.allocateDirect(indices.size * 2)
        ibb.order(java.nio.ByteOrder.nativeOrder())
        indexBuffer = ibb.asShortBuffer()
        indexBuffer.put(indices)
        indexBuffer.position(0)
    }


    /*This function draws our square on screen. @param gl*/
    fun render(gl: GL10) {
        gl.glLoadIdentity()
        // Drawing
        gl.glTranslatef(x, y, GameRenderer.DRAW_LAYER)
        gl.glColor4f(1.0f, 0.0f, 1.0f, 1.0f)
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
        gl.glDrawElements(GL10.GL_TRIANGLES, 6, GL10.GL_UNSIGNED_SHORT, indexBuffer)
        // Disable the vertices buffer.
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        // Disable face culling.
        gl.glDisable(GL10.GL_CULL_FACE)

        gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f)
    }
}
