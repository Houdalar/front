package tn.esprit.front.Ui.PlayList

import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.slider.Slider
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.Tracks
import tn.esprit.front.viewmodels.musicApi
import kotlin.concurrent.thread
import kotlin.math.log


class song_detail : AppCompatActivity() {
    lateinit var songName: TextView
    lateinit var songArtist: TextView
    lateinit var songCover: ImageView
    lateinit var play: ImageView
    lateinit var duration: TextView
    lateinit var progress: TextView
    lateinit var progressbar: Slider
    lateinit var mediaPlayer: MediaPlayer
    lateinit var sharedPreferences: SharedPreferences
    var isPlaying :  Boolean = false
    lateinit var playNext : ImageView
    lateinit var playPrevious : ImageView
    lateinit var back : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.played_now)
        supportActionBar?.hide()
        songName = findViewById(R.id.played_now_audio_title)
        songArtist = findViewById(R.id.played_now_audio_author)
        songCover = findViewById(R.id.music_cover)
        play = findViewById(R.id.imageView7)
        duration = findViewById(R.id.end_time)
        progress = findViewById(R.id.progress_time)
        progressbar = findViewById(R.id.slider)
        back = findViewById(R.id.back_to_music_home)

        playNext = findViewById(R.id.imageView8)
        playPrevious = findViewById(R.id.imageView9)

        val map: HashMap<String, String> = HashMap()
        val token : String ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOGI5MWUxNjc1ZTE2MTNlOTBlMTYyZiIsImlhdCI6MTY3MDc0MTg1MH0.GPsTqD7vbaBS65dsUJdfbPcU0Zdh4kmH4i8irCWgP5M"
        map["token"]=token
        sharedPreferences = this.getSharedPreferences("PREF_NAME", 0)
        takeData()
        mediaPlayer = MediaPlayer()
        var url = intent.getStringExtra("songUrl")
        var id = intent.getStringExtra("songId")

        mediaPlayer.setDataSource(url)
        mediaPlayer.prepare()


        val services = musicApi.create()

       back.setOnClickListener {
            finish()
        }


        playNext.setOnClickListener {

            map["currentTrack"] = id.toString()
             // get the next song
            Log.e("id",id.toString())
            services.getNextFavoritesTracks(map).enqueue(object : Callback<Tracks> {
                override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                    if (response.isSuccessful) {
                        val track = response.body()
                        id = track?._id
                        songName.text = track?.name
                        songArtist.text = track?.artist
                        Picasso.with(this@song_detail).load(track?.cover).into(songCover)
                        //update the progress bar
                        progressbar.value = 0f
                        progress.text = "00:00"
                        mediaPlayer.stop()
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(track?.url)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                        playAudio()
                    }
                    else{
                        Toast.makeText(this@song_detail, "no response", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Tracks>, t: Throwable) {
                    Toast.makeText(this@song_detail, "Error", Toast.LENGTH_SHORT).show()
                }
            })

        }

        playPrevious.setOnClickListener {
            map["currentTrack"] = id.toString()
            // get the previous song
            services.getPreviousFavoritesTracks(map).enqueue(object : Callback<Tracks> {
                override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                    if (response.isSuccessful) {
                        val track = response.body()
                        id = track?._id
                        songName.text = track?.name
                        songArtist.text = track?.artist
                        Picasso.with(this@song_detail).load(track?.cover).into(songCover)
                        //update the progress bar
                        progressbar.value = 0f
                        progress.text = "00:00"
                        mediaPlayer.stop()
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(track?.url)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                        playAudio()
                    }
                    else{
                        Toast.makeText(this@song_detail, "no response", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Tracks>, t: Throwable) {
                    Toast.makeText(this@song_detail, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }


        play.setOnClickListener { playAudio() }
    }






        private fun takeData()
        {
            val name = intent.getStringExtra("songName")
            val artist = intent.getStringExtra("songArtist")
            val cover = intent.getStringExtra("songCover")
            val url = intent.getStringExtra("songUrl")
            val id = intent.getStringExtra("songId")


            songName.text = name
            songArtist.text = artist
            Picasso.with(this).load(cover).into(songCover)
            // mediaPlayer.setDataSource(url)
        }
  // refresh the activity
        override fun onResume() {
            super.onResume()
            if (mediaPlayer.isPlaying) {
                play.setImageResource(R.drawable.ic_baseline_pause_24)
                play.tag = "pause"
            } else {
                play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                play.tag = "play"
            }
        }

   // createTimeLabel function to convert the time in milliseconds to minutes and seconds
        fun createTimeLabel(time: Int): String {
            var timeLabel = ""
            val min = time / 1000 / 60
            val sec = time / 1000 % 60

            timeLabel = "$min:"
            if (sec < 10) timeLabel += "0"
            timeLabel += sec

            return timeLabel
        }

        override fun onDestroy() {
            super.onDestroy()
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    fun updateSeekBar(){
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    progressbar.valueFrom = 0f
                    progressbar.valueTo = mediaPlayer.duration.toFloat()
                    //progressbar.value = mediaPlayer.currentPosition.toFloat()
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    progressbar.value = 0f
                }
            }
        }, 0)
    }
    fun playAudio() {
        val dur = mediaPlayer.duration
        // set the duration of the audio in the text view in minutes and seconds
        duration.text = createTimeLabel(dur)
        if (isPlaying)
        {
            mediaPlayer.pause()
            play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            isPlaying = false

        }
        else
        {
            mediaPlayer.start()
            play.setImageResource(R.drawable.ic_baseline_pause_24)
            isPlaying = true

            progressbar.valueTo = mediaPlayer.duration.toFloat()
            progressbar.addOnSliderTouchListener(object :
                Slider.OnSliderTouchListener {
                override fun onStartTrackingTouch(slider: Slider) {
                    mediaPlayer.pause()

                }

                override fun onStopTrackingTouch(slider: Slider) {
                    mediaPlayer.seekTo(slider.value.toInt())
                    mediaPlayer.start()
                }})
        }
       Thread(Runnable {
            while (mediaPlayer != null) {
                try {
                    progressbar.value = mediaPlayer.currentPosition.toFloat()
                    var msg = Message()
                    msg.what = mediaPlayer.currentPosition
                    val handlerThread = HandlerThread("handlerThread")
                    handlerThread.start()
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }).start()
        mediaPlayer.setOnCompletionListener {
            play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            isPlaying = false
        }


    }


    }






