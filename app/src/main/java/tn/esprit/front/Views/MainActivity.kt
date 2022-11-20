package tn.esprit.front.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.front.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
    }
}