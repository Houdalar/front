package tn.esprit.front.Ui.PlayList

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.nfc.tech.NfcF.get
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.reflect.TypeToken.get
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.Views.Activities.Signin.TOKEN
import tn.esprit.front.models.PlayList
import tn.esprit.front.models.Tracks
import tn.esprit.front.viewmodels.musicApi
import java.lang.reflect.Array.get



class songviewadapter (val tracks: MutableList<Tracks>) : RecyclerView.Adapter<songviewHolder>()  {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): songviewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.topmusic_item, parent, false)

        return songviewHolder(view)
    }

    override fun onBindViewHolder(holder:songviewHolder, @SuppressLint("RecyclerView") position: Int) {


        val name = tracks[position].name
        val artist = tracks[position].artist
        val cover = tracks[position].cover


        holder.songName.text = name
        holder.songArtist.text = artist
        Picasso.with(holder.itemView.context).load(cover).into(holder.songcover)


        holder.itemView.setOnClickListener{

            /*sharedPreferences = holder.itemView.context.getSharedPreferences("PREF_NAME", 0)
            val editor = sharedPreferences.edit()
            editor.putString("SongUrl", tracks[position].url)*/

            val intent = Intent(holder.itemView.context, song_detail::class.java)
            intent.apply {
                putExtra("songName", name)
                putExtra("songArtist", artist)
                putExtra("songCover", tracks[position].cover)
               putExtra("songUrl", tracks[position].url)
            }
            val service = musicApi.create()
            val tracks = Tracks(name = name)
            service.countListened(tracks)
            holder.itemView.context.startActivity(intent)


    }
        /*holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Hey, I'm listening to ${tracks[position].name} by ${tracks[position].artist} on babylo. Download it now: https://play.google.com/store/apps/details?id=tn.esprit.front")
            holder.itemView.context.startActivity(Intent.createChooser(intent, "Share with"))
            true
        })*/
        // on click on the heart icon
        /*holder.fav.setOnClickListener {
            val service = musicApi.create()
            val tracks = Tracks(name = name)
            service.addFav(tracks)
        }*/

        //on a long click on the item of the recycler view show a dialog to add the song to the playlist or to the fav list or to share it
        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            val map : HashMap<String, String> = HashMap()
            //map.put("trackid", tracks[position]._id.toString())
            val mSharedPreferences = holder.itemView.context.getSharedPreferences("PREF_NAME", 0)
            val token=mSharedPreferences.getString(TOKEN,"").toString()
            map["token"]=token
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Choose an option")
            val options = arrayOf("Add to playlist","Share","cancel")
            builder.setItems(options, DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    0 -> {
                        // show the dialog to choose the playlist
                        val service = musicApi.create()
                        val call = service.getPlaylists(map)
                        call.enqueue(object : Callback<MutableList<PlayList>> {


                            override fun onResponse(call: Call<MutableList<PlayList>>, response: Response<MutableList<PlayList>>) {
                                if (response.isSuccessful) {
                                    val playlists = response.body()
                                    val builder = AlertDialog.Builder(holder.itemView.context)
                                    builder.setTitle("Choose a playlist")
                                    val options = arrayOfNulls<String>(playlists!!.size)
                                    for (i in 0 until playlists.size) {
                                        options[i] = playlists[i].name
                                    }
                                    val service = musicApi.create()
                                    map.put("trackid", tracks[position]._id.toString())
                                    map["name"] = playlists[which].name.toString()
                                    builder.setItems(options, DialogInterface.OnClickListener { dialog, which ->
                                         service.addTrackToPlayList(map).enqueue(object : Callback<PlayList> {
                                            override fun onResponse(call: Call<PlayList>, response: Response<PlayList>) {
                                                if (response.isSuccessful) {
                                                    Toast.makeText(holder.itemView.context, "Song added to playlist", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                            override fun onFailure(call: Call<PlayList>, t: Throwable) {
                                                Toast.makeText(holder.itemView.context, "Error while adding the track", Toast.LENGTH_SHORT).show()
                                            }
                                        })
                                    })
                                    builder.show()
                                }
                            }
                            override fun onFailure(call: Call<MutableList<PlayList>>, t: Throwable) {
                                Toast.makeText(holder.itemView.context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        })

                    }
                    1 -> {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        intent.putExtra(Intent.EXTRA_TEXT, "Hey, I'm listening to ${tracks[position].name} by ${tracks[position].artist} on babylo. Download it now: https://play.google.com/store/apps/details?id=tn.esprit.front")
                        holder.itemView.context.startActivity(Intent.createChooser(intent, "Share with"))
                    }
                    2 -> {
                        dialog.dismiss()
                    }
                }
            })
            builder.show()
            true
        })




    }

    override fun getItemCount() = tracks.size




}