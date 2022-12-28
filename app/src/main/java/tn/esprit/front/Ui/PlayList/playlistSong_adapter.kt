package tn.esprit.front.Ui.PlayList

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.PlayList
import tn.esprit.front.models.Tracks
import tn.esprit.front.viewmodels.musicApi



class playlistSong_adapter (val tracks: MutableList<Tracks>) : RecyclerView.Adapter<songviewHolder>()  {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var mediaPlayer: MediaPlayer
    var isPlaying :  Boolean = false
    var isfavorite: Boolean = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): songviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.topmusic_item, parent, false)

        return songviewHolder(view)
    }

    override fun onBindViewHolder(holder:songviewHolder, @SuppressLint("RecyclerView") position: Int) {


        val name = tracks[position].name
        val artist = tracks[position].artist
        val cover = tracks[position].cover

        mediaPlayer = MediaPlayer()
        mediaPlayer.setDataSource(tracks[position].url)
        mediaPlayer.prepare()

       // if the name of the song is too long, it will be cut and add "..." at the end
        if (name.length > 20) {
            holder.songName.text = name.substring(0, 20) + "..."
            // if the name is focused, it will show the full name with animation "fade in"
            holder.songName.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    holder.songName.text = name
                    holder.songName.isSelected = true
                } else {
                    holder.songName.text = name.substring(0, 20) + "..."
                }
            }


        } else {
            holder.songName.text = name
        }

        holder.songArtist.text = artist
        Glide.with(holder.itemView.context).load(cover).into(holder.songcover)
        

        //get the favorites song and set the heart icon
        sharedPreferences = holder.itemView.context.getSharedPreferences("sharedPrefs", 0)
        var favorite: MutableList<String>?
        favorite = sharedPreferences.getStringSet("favoriteTracks", mutableSetOf())?.toMutableList()

        if (favorite == null) {
            favorite = mutableListOf()
        }
        if (favorite.contains(name))
        {
            holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24)
            isfavorite = true
        }
        else
        {
            holder.fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            isfavorite = false
        }

        // play the song

        holder.itemView.setOnClickListener{
            // get the recyclerview tag
            val intent = Intent(holder.itemView.context, song_detail::class.java)
            intent.apply {
                putExtra("songName", name)
                putExtra("songArtist", artist)
                putExtra("songCover", tracks[position].cover)
                putExtra("songUrl", tracks[position].url)
                putExtra("tag", "top")
                putExtra("songId", tracks[position]._id)
            }
            val service = musicApi.create()
            val tracks = Tracks(name = name)
            service.countListened(tracks)
            holder.itemView.context.startActivity(intent)


    }
        // on click on the heart icon
        holder.fav.setOnClickListener {
            val service = musicApi.create()
            val map = HashMap<String, String>()
            map["songname"] = name
            map["token"]="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOGI5MWUxNjc1ZTE2MTNlOTBlMTYyZiIsImlhdCI6MTY3MDk2NjYwMX0.ZXBT5XlrgcSVdsvtW9Qte6BMsHhxTJ7SAOAhiFH2vdg"
            // map["token"]= sharedPreferences.getString("token", "").toString()
            if(isfavorite)
            {

                service.removeFavoritesTrack(map).enqueue(object : Callback<Tracks> {
                    override fun onFailure(call: Call<Tracks>, t: Throwable) {
                        Toast.makeText(holder.itemView.context, "Error", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                        if (response.isSuccessful) {
                            Toast.makeText(holder.itemView.context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                            holder.fav.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            isfavorite = false
                        }
                    }
                })
            }
            else
            {
                    service.addFavoritesTrack(map)
                        .enqueue(object : Callback<Tracks> {
                            override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                                if (response.isSuccessful) {
                                    Toast.makeText(holder.itemView.context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                    holder.fav.setImageResource(R.drawable.ic_baseline_favorite_24)
                                    isfavorite = true
                                }
                            }

                            override fun onFailure(call: Call<Tracks>, t: Throwable) {
                                Toast.makeText(holder.itemView.context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
        }

        //on a long click on the item of the recycler view show a dialog to add the song to the playlist or to the fav list or to share it

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete this song from your playlist ?")
            builder.setPositiveButton("Yes") { dialog, which ->
                val services = musicApi.create()
                val map : HashMap<String, String> = HashMap()
                map["trackid"] =tracks[position]._id.toString()
                val playlistName = sharedPreferences.getString("playlistname", "")
                map["name"] = playlistName.toString()
                map["token"]="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOGI5MWUxNjc1ZTE2MTNlOTBlMTYyZiIsImlhdCI6MTY3MDgzMzAxNH0.xtR83b0vClblof3bw4vQ7xu29mcAJNZl8IyHCWpSxG8"
                services.deleteTrackfromPlaylist(map).enqueue(object : Callback<Tracks> {
                    override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                        if (response.isSuccessful) {
                            Toast.makeText(holder.itemView.context, "Song deleted from your playlist", Toast.LENGTH_SHORT).show()
                            notifyDataSetChanged()
                            notifyItemRangeChanged(holder.adapterPosition, tracks.size)
                        }
                    }

                    override fun onFailure(call: Call<Tracks>, t: Throwable) {
                        Toast.makeText(holder.itemView.context, "Error", Toast.LENGTH_SHORT).show()
                    }
                })

            }
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
            true
            return@setOnLongClickListener true
        }

        holder.play.setOnClickListener()
        {

            if (isPlaying) {
                mediaPlayer.pause()
                holder.play.setImageResource(R.drawable.ic_baseline_play_circle_outline_24)
                isPlaying = false
            } else {
                mediaPlayer.start()
                holder.play.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24)
                isPlaying = true
                val service = musicApi.create()
                val tracks = Tracks(name = name)
                service.countListened(tracks)
            }
        }
    }
    //


    override fun getItemCount() = tracks.size




}