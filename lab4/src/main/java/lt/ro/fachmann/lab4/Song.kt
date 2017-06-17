package lt.ro.fachmann.lab4

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.support.annotation.DrawableRes

/**
 * Created by bartl on 12.06.2017.
 */

class Song(val uri: Uri,
           val title: String = "",
           val album: String = "",
           val artist: String = "",
           val type: String = "",
           val cover: Bitmap? = null) {

    override fun toString() = "Audio(title='$title', artist='$artist)"


    fun getCoverBitmap(resources: Resources): Bitmap? {
        if(DEFAULT_COVER == null ){
            DEFAULT_COVER = BitmapFactory.decodeResource(resources, R.drawable.bang)
        }
        val mediaMetadataRetriever = MediaMetadataRetriever()
        if (type == "audio/mpeg") {
            mediaMetadataRetriever.setDataSource(uri.toString())
            val data = mediaMetadataRetriever.embeddedPicture

            if (data != null) {
                return BitmapFactory.decodeByteArray(data, 0, data.size)
            }
        }
        return DEFAULT_COVER
    }

    companion object {
        var DEFAULT_COVER: Bitmap? = null
    }

}