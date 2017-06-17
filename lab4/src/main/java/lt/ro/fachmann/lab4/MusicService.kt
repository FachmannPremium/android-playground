package lt.ro.fachmann.lab4

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager

import java.io.IOException
import java.util.ArrayList
import android.app.PendingIntent
import android.graphics.BitmapFactory


/**
 * Created by bartl on 11.06.2017.
 */

class MusicService : Service() {
    val NOTIFY_ID = 1

    private lateinit var mediaPlayer: MediaPlayer
    private val binder = MusicServiceBinder()

    private val onSongChangeListenerList = ArrayList<OnSongChangeListener>()

    var songList = ArrayList<Song>()
    var currentSongPosition: Int = 0

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    inner class MusicServiceBinder : Binder() {
        internal
        val service: MusicService
            get() = this@MusicService
    }

    override fun onCreate() {
        mediaPlayer = MediaPlayer()
        mediaPlayer.setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        mediaPlayer.setOnPreparedListener {
            it.start()
            //notification
            val notIntent = Intent(this, SongListActivity::class.java)
            notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendInt = PendingIntent.getActivity(this, 0,
                    notIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            val builder = Notification.Builder(this)

            builder.setContentIntent(pendInt)
                    .setSmallIcon(R.drawable.ic_play_circle)
                    .setTicker(songList[currentSongPosition].title)
                    .setOngoing(true)
                    .setContentTitle("Playing")
                    .setContentText(songList[currentSongPosition].title)
            val not = builder.build()
            startForeground(NOTIFY_ID, not)
        }
        mediaPlayer.setOnCompletionListener {
            playNext()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent): Boolean {
        mediaPlayer.stop()
        mediaPlayer.release()
        return false
    }

    fun play(position: Int) {
        mediaPlayer.reset()
        currentSongPosition = position
        val song = currentSong()
        try {
            mediaPlayer.setDataSource(applicationContext, song.uri)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mediaPlayer.prepareAsync()
        onSongChangeListenerList.forEach {
            it.onSongChange(song)
            it.onPlay()
        }
    }

    fun play() {
        mediaPlayer.start()
        onSongChangeListenerList.forEach { it.onPlay() }
    }

    fun pause() {
        mediaPlayer.pause()
        onSongChangeListenerList.forEach { it.onPause() }
    }

    fun playNext() {
        var next = currentSongPosition + 1
        if (next == songList.size) next = 0
        play(next)
    }

    fun playPrev() {
        var prev = currentSongPosition - 1
        if (prev == -1) prev = songList.size - 1
        play(prev)
    }

    var offset
        get() = mediaPlayer.currentPosition
        set(value) = mediaPlayer.seekTo(value)

    val duration
        get() = mediaPlayer.duration

    fun addOnSongChangeListener(listener: OnSongChangeListener) {
        onSongChangeListenerList.add(listener)
    }

    fun isPlaying() = mediaPlayer.isPlaying

    fun currentSong() = songList[currentSongPosition]

    interface OnSongChangeListener {
        fun onSongChange(song: Song)
        fun onPause()
        fun onPlay()
    }
}