package lt.ro.fachmann.wasserwaga

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), SensorEventListener {
    lateinit var sensorManager: SensorManager
    lateinit var accelerometer: Sensor

    private var lastUpdate: Long = 0
//    private var last_x: Float = 0f
//    private var last_y: Float = 0f
//    private var last_z: Float = 0f
//    private var SHAKE_THRESHOLD = 600

    private lateinit var renderer: GameRenderer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        renderer = GameRenderer(this)
        glView.setRenderer(renderer)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //TO DO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSensorChanged(event: SensorEvent) {
//Log.i("poke...", "poke")
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val curTime = System.currentTimeMillis()
                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                helloBox.text = String.format(Locale.getDefault(), "%.3f %.3f %.3f", x, y, z)
            renderer.monk.destinationX = -x
            renderer.monk.destinationY = -y
//                renderer.monk2.currentX = -x
//                renderer.monk2.currentY = -y
//                val diffTime = curTime - lastUpdate
            lastUpdate = curTime
//                //val speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000
//                last_x = x
//                last_y = y
//                last_z = z

        }
    }


    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
}
