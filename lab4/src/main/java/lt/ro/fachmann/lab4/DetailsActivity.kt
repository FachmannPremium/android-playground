package lt.ro.fachmann.lab4

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import android.support.v8.renderscript.Allocation
import android.support.v8.renderscript.Element
import android.support.v8.renderscript.RenderScript
import android.support.v8.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.uiThread

class DetailsActivity : AppCompatActivity() {

    lateinit var musicService: MusicService
    var serviceBound = false
    var currentSong: Song? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        bindService()
        titleDetails.isSelected = true
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
        serviceBound = false
    }

    fun refreshView() {
        if (currentSong != null) {
            titleDetails.text = currentSong?.title
            albumDetails.text = currentSong?.album
            artistDetails.text = currentSong?.artist

            playPauseDetails.imageResource =
                    if (musicService.isPlaying())
                        R.drawable.ic_pause_circle
                    else
                        R.drawable.ic_play_circle
            val renderScript = RenderScript.create(this)
            doAsync {
                val coverBitmap = currentSong?.getCoverBitmap(resources)
                val coverBitmapBlurred = coverBitmap?.copy(coverBitmap.config, true)

                val input: Allocation = Allocation.createFromBitmap(renderScript, coverBitmapBlurred)
                val output: Allocation = Allocation.createTyped(renderScript, input.type)
                val script: ScriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
                script.setRadius(8f)
                script.setInput(input)
                script.forEach(output)
                output.copyTo(coverBitmapBlurred)

                uiThread {
                    coverDetails.setImageBitmap(coverBitmap)
                    coverBackgroundDetails.setImageBitmap(coverBitmapBlurred)
                    songOffsetDetails.max = musicService.duration
                    songOffsetDetails.progress = musicService.offset
                }
            }
        }
    }

    fun bindService() {
        val bindIntent = Intent(this, MusicService::class.java)
        bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun initService() {
        musicService.addOnSongChangeListener(object : MusicService.OnSongChangeListener {
            override fun onSongChange(song: Song) {
                currentSong = song
                refreshView()
            }

            override fun onPause() {
                playPauseDetails.imageResource = R.drawable.ic_play_circle
            }

            override fun onPlay() {
                playPauseDetails.imageResource = R.drawable.ic_pause_circle
            }
        })
        currentSong = musicService.currentSong()
        refreshView()
        val offsetHandler = Handler()
        runOnUiThread(object : Runnable {
            override fun run() {
                if (serviceBound) {
                    songOffsetDetails.progress = musicService.offset
                    offsetHandler.postDelayed(this, 1000)
                }
            }
        })

        songOffsetDetails.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService.offset = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    fun playDetails(view: View) {
        if (musicService.isPlaying()) {
            musicService.pause()
        } else {
            musicService.play()
        }
    }

    fun nextDetails(view: View) {
        musicService.playNext()
    }

    fun prevDetails(view: View) {
        musicService.playPrev()
    }


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MusicService.MusicServiceBinder
            musicService = binder.service
            serviceBound = true
            initService()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }
}
