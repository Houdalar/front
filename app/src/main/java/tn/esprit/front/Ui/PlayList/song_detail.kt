package tn.esprit.front.Ui.PlayList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.front.R

class song_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.played_now)
        supportActionBar?.hide()
    }
}