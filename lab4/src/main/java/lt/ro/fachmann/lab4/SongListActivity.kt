package lt.ro.fachmann.lab4

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_music_list.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.startActivity
import java.util.*


class SongListActivity : AppCompatActivity() {
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    lateinit var musicService: MusicService
    var serviceBound = false

    lateinit var adapter: SongListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_list)
        bindService()
        titlePopup.isSelected = true
    }

    override fun onDestroy() {
        if (isFinishing) {
            unbindService(serviceConnection)
            val startIntent = Intent(this, MusicService::class.java)
            stopService(startIntent)
        }
        super.onDestroy()
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as MusicService.MusicServiceBinder
            initMusicService(binder.service)
            serviceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }

    fun selectSong(song: Song) {
        bottomPlayerView.visibility = View.VISIBLE
        musicService.play(musicService.songList.indexOf(song))
    }

    fun play(view: View) {
        if (musicService.isPlaying()) {
            musicService.pause()
        } else {
            musicService.play()
        }
    }

    fun next(view: View) {
        musicService.playNext()
    }

    fun showDetails(view: View) {
        startActivity<DetailsActivity>()
    }

    fun bindService() {
        val bindIntent = Intent(this, MusicService::class.java)
        bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun initMusicService(service: MusicService) {
        musicService = service

        val songList =
                if (musicService.songListInitialized) musicService.songList
                else if (checkAndRequestPermissions()) loadAudioList()
                else ArrayList<Song>()

        if (!musicService.songListInitialized) {
            musicService.songList = songList
            musicService.songListInitialized = true
        } else {
            bottomPlayerView.visibility = View.VISIBLE
            titlePopup.text = musicService.currentSong().title
            playPausePopup.imageResource = if (musicService.isPlaying()) R.drawable.ic_pause_circle else R.drawable.ic_play_circle
        }
        initSongRecyclerView(songList)

        musicService.addOnSongChangeListener(object : MusicService.OnSongChangeListener {
            override fun onSongChange(song: Song) {
                titlePopup.text = song.title
            }

            override fun onPause() {
                playPausePopup.imageResource = R.drawable.ic_play_circle
            }

            override fun onPlay() {
                playPausePopup.imageResource = R.drawable.ic_pause_circle
            }
        })
    }

    fun initSongRecyclerView(songList: List<Song>) {
        adapter = SongListAdapter(songList) {
            selectSong(it)
        }
        songRecyclerView.layoutManager = LinearLayoutManager(this)
        songRecyclerView.itemAnimator = DefaultItemAnimator()
        songRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun loadAudioList(): ArrayList<Song> {
        val audioList = ArrayList<Song>()

        val externalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        //val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.DATA} LIKE '/storage/9016-4EF8/Muzyka/'"

        val folder = "/" //"/storage/9016-4EF8/Muzyka"
        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND ${MediaStore.Audio.Media.DATA} LIKE ?"
        val selectionArgs = arrayOf("%$folder%")


        val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"
        val cursor = contentResolver.query(externalContentUri, null, selection, selectionArgs, sortOrder)

        if (cursor != null && cursor.count > 0) {
            while (cursor.moveToNext()) {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val type = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE))
                val uri = Uri.parse(path)
                audioList.add(Song(uri, title, album, artist, type))
            }
        }
        cursor?.close()
        return audioList
    }

    private fun checkAndRequestPermissions(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            val listPermissionsNeeded = ArrayList<String>()

            if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
                return false
            } else {
                return true
            }
        }
        return false
    }
}

