package tn.esprit.front.Ui.PlayList

import android.content.Intent
import android.net.Uri
import android.nfc.tech.NfcF.get
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.reflect.TypeToken.get
import com.squareup.picasso.Picasso
import tn.esprit.front.R
import tn.esprit.front.models.Tracks
import java.lang.reflect.Array.get


class songviewadapter (val tracks: MutableList<Tracks>) : RecyclerView.Adapter<songviewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): songviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.topmusic_item, parent, false)

        return songviewHolder(view)
    }

    override fun onBindViewHolder(holder:songviewHolder, position: Int) {

        val name = tracks[position].name
        val artist = tracks[position].artist
        val cover = tracks[position].cover







        // holder.songcover.setImageResource(tracks[position].cover!!)
        holder.songName.text = name
        holder.songArtist.text = artist
        Picasso.with(holder.itemView.context).load(cover).into(holder.songcover)







        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, song_detail::class.java)
            intent.apply {
                putExtra("songName", name)
                putExtra("songArtist", artist)
                putExtra("songCover", tracks[position].cover)
                putExtra("songUrl", tracks[position].url)

            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = tracks.size

}