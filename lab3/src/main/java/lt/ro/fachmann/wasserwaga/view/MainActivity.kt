package lt.ro.fachmann.wasserwaga.view

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import lt.ro.fachmann.wasserwaga.R
import lt.ro.fachmann.wasserwaga.game.Game
import lt.ro.fachmann.wasserwaga.game.Level
import lt.ro.fachmann.wasserwaga.gl.GameRenderer
import org.jetbrains.anko.startActivity
import java.util.*


class MainActivity : AppCompatActivity(), SensorEventListener {
    companion object {
        val REFRESH_RATE = 19
    }

    lateinit var sensorManager: SensorManager
    lateinit var accelerometer: Sensor

    private var lastUpdate: Long = 0

    private lateinit var renderer: GameRenderer
    private lateinit var game: Game

    private val levelMap = HashMap<MenuItem, Level>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        game = Game(this)
        setLevel(game.levels[2])

        renderer = GameRenderer(game, this)
        glView.setRenderer(renderer)


        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            val curTime = System.currentTimeMillis()
            if (curTime - lastUpdate > REFRESH_RATE) {
                val x = event.values[0]
                val y = event.values[1]

                game.updateSensorReading(x, y)
                lastUpdate = curTime
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val levelMenuGroup = menu.findItem(R.id.menu_level).subMenu
        for (level in game.levels) {
            val menuItem = levelMenuGroup.add(level.name)
            levelMap.put(menuItem, level)
        }

        menu.findItem(R.id.menu_free_mode).isChecked = game.freeMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            startActivity<AboutActivity>()
            return true
        } else if (item.itemId == R.id.menu_free_mode) {
            game.freeMode = !game.freeMode
            item.isChecked = game.freeMode
            return true
        } else if (levelMap.contains(item)) {
            val level = levelMap[item]
            setLevel(level as Level)
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    fun setLevel(level: Level) {
        game.setLevel(level)
        title = "${getString(R.string.app_name)} - ${level.name}"
    }
}
