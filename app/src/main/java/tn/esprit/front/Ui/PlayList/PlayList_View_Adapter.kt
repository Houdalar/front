package tn.esprit.front.Ui.PlayList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import tn.esprit.front.R
import tn.esprit.front.Ui.Home
import tn.esprit.front.models.PlayList

class PlayListViewAdapter (val PlayList: MutableList<PlayList>) : RecyclerView.Adapter<PlayListViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.playlist_item, parent, false)

        return PlayListViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {

        val name = PlayList[position].name
        val cover = PlayList[position].cover


        Glide.with(holder.itemView.context).load(cover).into(holder.playlistCover)
        holder.playlistName.text = name



        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, Full_PlayList_Activity::class.java)
            intent.apply {
                putExtra("playlistname", name)
                putExtra("playlistcover", cover)
                putExtra("playlistid", PlayList[position]._id)
                putExtra("tag", "playlist")
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = PlayList.size

}
