package lt.ro.fachmann.lab4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by bartl on 12.06.2017.
 */


fun ViewGroup.inflate(layoutId: Int): View {
    return LayoutInflater.from(context).inflate(layoutId, this, false)
}