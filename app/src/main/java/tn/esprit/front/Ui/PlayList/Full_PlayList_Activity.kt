package tn.esprit.front.Ui.PlayList


import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
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
    lateinit var recylcersongAdapter: playlistSong_adapter
    lateinit var title :TextView
    lateinit var playlistcover: ImageView
    lateinit var back: ImageView
    lateinit var sharedPreferences: SharedPreferences
    lateinit var playlistcard: ImageView

    var tracks: ArrayList<Tracks> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_play_list_screen)
        recylcersong = findViewById(R.id.recyclerplaylist)
        playlistcover = findViewById(R.id.imageView19)
        back = findViewById(R.id.imageView22)
        title = findViewById(R.id.playname)

        playlistcard = findViewById(R.id.imageView18)
        // set the color of the vector drawable





        back.setOnClickListener {
            finish()
        }


        // set the title of the play list in the action bar
        val title1 = intent.getStringExtra("playlistname")
        title.text = title1
        supportActionBar?.hide()
        var url = intent.getStringExtra("songUrl")
        val cover = intent.getStringExtra("playlistcover")
        Glide.with(this@Full_PlayList_Activity).load(cover).into(playlistcover)
        // get th

//        val bitmap = (playlistcover.drawable as BitmapDrawable).bitmap
//        val palette = Palette.from(bitmap).generate()
//        val dominantColor = palette.getDominantColor(Color.WHITE)
//        val redValue = Color.red(dominantColor)
//        val greenValue = Color.green(dominantColor)
//        val blueValue = Color.blue(dominantColor)
//        // set the color of the vector drawable
//        val color = Color.argb(255, redValue, greenValue, blueValue)
//        playlistcard.setColorFilter(color)
        var services = musicApi.create()
        val map = HashMap<String, String>()
        var id = intent.getStringExtra("playlistid")
        println("id is $id")
        map["playListid"] = id.toString()
        //put the name of the play list in the shared preferences
        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("playlistname", title.text.toString())
        println("playlist name is ${title.text.toString()}")
        editor.apply()




        services.getPlaylistTracks(map)!!.enqueue(object : Callback<MutableList<Tracks>> {
            override fun onResponse(
                call: Call<MutableList<Tracks>>,
                response: Response<MutableList<Tracks>>
            ) {
                if (response.code() == 200) {


                    val tracksList = response.body() as MutableList<Tracks>
                    tracks.clear()
                    tracks.addAll(tracksList)

                    recylcersongAdapter = playlistSong_adapter(tracks)
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
