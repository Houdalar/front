package tn.esprit.front.Ui.PlayList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.models.Song
import tn.esprit.front.models.Tracks

class songviewadapter (val tracks: MutableList<Tracks>) : RecyclerView.Adapter<songviewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): songviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.topmusic_item, parent, false)

        return songviewHolder(view)
    }

    override fun onBindViewHolder(holder:songviewHolder, position: Int) {

        val name = tracks[position].name
        val artist = tracks[position].artist


        holder.songcover.setImageResource(tracks[position].cover!!)
        holder.songName.text = name
        holder.songArtist.text = artist


        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, song_detail::class.java)
            intent.apply {
            }
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount() = tracks.size

}