package lt.ro.fachmann.lab4

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.song_list_row.view.*
import android.widget.LinearLayout
import android.graphics.BitmapFactory
import android.util.Log
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.uiThread
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


/**
 * Created by bartl on 12.06.2017.
 */
class SongListAdapter(
        val songList: ArrayList<Song>,
        private val itemClick: (Song) -> Unit
) : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {
    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) = holder.bindSong(songList[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val itemView = parent.inflate(R.layout.song_list_row)
        return SongViewHolder(itemView, itemClick)
    }

    class SongViewHolder(itemView: View, val itemClick: (Song) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindSong(song: Song) {
            itemView.titleRow.text = song.title
            itemView.albumRow.text = song.album
            itemView.artistRow.text = song.artist

            doAsync {
                val coverBitmap = song.getCoverBitmap(itemView.resources)
                uiThread {
                    itemView.coverRow.setImageBitmap(coverBitmap)
                }
            }

            itemView.setOnClickListener { itemClick(song) }
        }
    }
}