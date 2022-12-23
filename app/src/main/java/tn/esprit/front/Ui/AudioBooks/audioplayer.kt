package tn.esprit.front.Ui.AudioBooks

import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.HandlerThread
import android.os.Message
import android.widget.*
import com.bumptech.glide.Glide
import com.google.android.material.slider.Slider
import tn.esprit.front.R
import kotlin.concurrent.thread

class audioplayer : AppCompatActivity() {
    lateinit var bookTitle: TextView
    lateinit var Author: TextView
    lateinit var bookcover : ImageView
    lateinit var duration: TextView
    lateinit var progress: TextView
    lateinit var progressbar: Slider
    lateinit var mediaPlayer: MediaPlayer
    lateinit var sharedPreferences: SharedPreferences
    var isPlaying :  Boolean = false
    lateinit var play: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audioplayer)

        bookTitle = findViewById(R.id.played_now_book_title)
        Author = findViewById(R.id.played_now_book_author)
        bookcover = findViewById(R.id.played_now_book_cover)
        duration = findViewById(R.id.end_time)
        progress = findViewById(R.id.progress_time)
        progressbar = findViewById(R.id.slider2)
        play = findViewById(R.id.imageView27)

        sharedPreferences = this.getSharedPreferences("PREF_NAME", 0)
        takeData()
        mediaPlayer = MediaPlayer()
        var url = intent.getStringExtra("url")
        mediaPlayer.setDataSource(url)
        progressbar.valueFrom = 0f

        play.setOnClickListener {
            playAudio()
        }
       //updae the progress text
                thread(start = true) {
                    while (true) {
                        if (mediaPlayer.isPlaying) {
                            val current = mediaPlayer.currentPosition
                            val total = mediaPlayer.duration
                            val currentMin = current / 1000 / 60
                            val currentSec = current / 1000 % 60
                            val totalMin = total / 1000 / 60
                            val totalSec = total / 1000 % 60
                            val currentText = String.format("%02d:%02d", currentMin, currentSec)
                            runOnUiThread {
                                progress.text = currentText

                            }
                            Thread.sleep(1000)
                        }
                    }
                }


    }
    private fun takeData()
    {
       val cover= intent.getStringExtra("bookcover")
        Glide.with(this).load(cover).into(bookcover)
        val title = intent.getStringExtra("title")
        bookTitle.text = title
        val author = intent.getStringExtra("author")
        Author.text = author
    }
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
            //progressbar.valueFrom = mediaPlayer.currentPosition.toFloat()

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
                    //create a thred to update the progress text view
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
        // update the progress text view in minutes and seconds

    }
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
}