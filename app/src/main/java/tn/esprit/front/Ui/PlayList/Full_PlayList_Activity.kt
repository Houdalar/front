package tn.esprit.front.Ui.PlayList


import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.shape.CornerFamily
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.Tracks
import tn.esprit.front.viewmodels.musicApi


class Full_PlayList_Activity : AppCompatActivity() {

    lateinit var recylcersong: RecyclerView
    lateinit var recylcersongAdapter: songplaylistviewadapter
    lateinit var cardView: MaterialCardView
    lateinit var playlistcover: ImageView
    lateinit var back: ImageView

    var tracks: ArrayList<Tracks> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_play_list_screen)
        recylcersong = findViewById(R.id.recyclerplaylist)
        cardView= findViewById(R.id.playcard_cover)
        playlistcover = findViewById(R.id.playlist_cover)
        back = findViewById(R.id.back_to_music_home)

        back.setOnClickListener {
            finish()
        }


        // set the title of the play list in the action bar
        val title = intent.getStringExtra("playlistname")
        supportActionBar?.hide()
        var url = intent.getStringExtra("songUrl")
        val cover = intent.getStringExtra("playlistcover")
        Glide.with(this@Full_PlayList_Activity).load(cover).into(playlistcover)

        var services = musicApi.create()
        val map = HashMap<String, String>()
        var id = intent.getStringExtra("playlistid")
        println("id is $id")
        map["playListid"] = id.toString()





        services.getPlaylistTracks(map)!!.enqueue(object : Callback<MutableList<Tracks>> {
            override fun onResponse(
                call: Call<MutableList<Tracks>>,
                response: Response<MutableList<Tracks>>
            ) {
                if (response.code() == 200) {


                    val tracksList = response.body() as MutableList<Tracks>
                    tracks.clear()
                    tracks.addAll(tracksList)

                    recylcersongAdapter = songplaylistviewadapter(tracks)
                    recylcersongAdapter.notifyDataSetChanged()
                    recylcersong.adapter = recylcersongAdapter
                    recylcersong.layoutManager =
                        LinearLayoutManager(this@Full_PlayList_Activity, LinearLayoutManager.VERTICAL, false)
                } else {
                    Toast.makeText(this@Full_PlayList_Activity, "error ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<Tracks>>, t: Throwable) {
                Toast.makeText(this@Full_PlayList_Activity, "error while getting the tracks", Toast.LENGTH_SHORT).show()
            }

        })

        }


    }
