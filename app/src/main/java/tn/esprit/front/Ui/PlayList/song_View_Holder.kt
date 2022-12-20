package tn.esprit.front.Ui.PlayList

import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R

class songviewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)
{
     var card : CardView = itemView.findViewById<CardView>(R.id.cardView)
     var songcover : ImageView
     var songArtist : TextView
     var songName : TextView
     var fav : ImageView
     var play : ImageView

    init {
        songcover = itemView.findViewById<ImageView>(R.id.ivTopMusic)
        songArtist= itemView.findViewById<TextView>(R.id.songauthor)
        songName= itemView.findViewById<TextView>(R.id.track_name)
        fav = itemView.findViewById<ImageView>(R.id.imageView13)
        play = itemView.findViewById<ImageView>(R.id.play)

    }

}