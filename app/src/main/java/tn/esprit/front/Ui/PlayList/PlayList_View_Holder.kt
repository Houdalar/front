package tn.esprit.front.Ui.PlayList

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R

class PlayListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
{
    lateinit var playlistCover : ImageView
    lateinit var playcard : ImageView
    val playlistName : TextView = itemView.findViewById<TextView>(R.id.playlist_name)

    init {
        playlistCover = itemView.findViewById<ImageView>(R.id.Play_list_cover)
       // play = itemView.findViewById<ImageView>(R.id.play_stop_playlist)
    }

}