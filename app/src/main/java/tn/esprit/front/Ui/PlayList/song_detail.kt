package tn.esprit.front.Ui.PlayList

import android.media.AudioManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.google.android.material.slider.Slider
import com.squareup.picasso.Picasso
import tn.esprit.front.R


class song_detail : AppCompatActivity() {
    lateinit var songName : TextView
    lateinit var songArtist : TextView
    lateinit var songCover : ImageView
    lateinit var play : ImageView
    lateinit var duration: TextView
    lateinit var progressbar: Slider
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.played_now)
        supportActionBar?.hide()
        songName = findViewById(R.id.played_now_audio_title)
        songArtist = findViewById(R.id.played_now_audio_author)
        songCover = findViewById(R.id.music_cover)
        play = findViewById(R.id.imageView7)
        duration = findViewById(R.id.end_time)
        progressbar = findViewById(R.id.slider)

        mediaPlayer = MediaPlayer()
        takeData()

        play.tag = "play"

        play.setOnClickListener{
            if (play.tag == "play"){
                play.setImageResource(R.drawable.ic_baseline_pause_24)

            // on below line we are creating a variable for our audio url
            var url =intent.getStringExtra("songUrl")
            //var audioUrl = "http://172.16.1.34:8080/media/music/aot.mp3"
            // on below line we are setting audio stream
            // type as stream music on below line.
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)

            // on below line we are running a try
            // and catch block for our media player.
            try {
                // on below line we are setting audio
                // source as audio url on below line.
                mediaPlayer.setDataSource(url)

                // on below line we are
                // preparing our media player.
                mediaPlayer.prepare()

                // on below line we are
                // starting our media player.
                mediaPlayer.start()

            } catch (e: Exception) {

                // on below line we are handling our exception.
                e.printStackTrace()
            }
            // on below line we are displaying a toast message as audio player.
            Toast.makeText(applicationContext, "Audio started playing..", Toast.LENGTH_SHORT).show()
                play.tag = "pause"

            }else{
                play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                if (mediaPlayer.isPlaying) {
                    // if media player is playing we
                    // are stopping it on below line.
                    mediaPlayer.stop()

                    // on below line we are resetting
                    // our media player.
                    mediaPlayer.reset()

                    // on below line we are calling
                    // release to release our media player.
                    mediaPlayer.release()
                    play.tag = "play"

                    // on below line we are displaying a toast message to pause audio/
                    Toast.makeText(applicationContext, "Audio has been  paused..", Toast.LENGTH_SHORT)
                        .show()


                } else {
                    // if audio player is not displaying we are displaying below toast message
                    Toast.makeText(applicationContext, "Audio not played..", Toast.LENGTH_SHORT).show()
                }


            }

        }


        /*if (play.tag == "play") {
            play.setImageResource(R.drawable.ic_baseline_pause_24)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
           // mediaPlayer.setDataSource(intent.getStringExtra("songUrl"))
            //val url = intent.getStringExtra("songUrl")
           var url = "http://localhost:8080/media/music/aot.mp3"
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
            play.tag = "pause"
        } else {
            play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                play.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            } else {
                mediaPlayer.start()
                play.setImageResource(R.drawable.ic_baseline_pause_24)
            }
            play.tag = "play"
        }*/
    }
    private fun takeData() {
        val name = intent.getStringExtra("songName")
        val artist = intent.getStringExtra("songArtist")
        val cover = intent.getStringExtra("songCover")


        songName.text = name
        songArtist.text = artist
        Picasso.with(this).load(cover).into(songCover)

    }

    }

