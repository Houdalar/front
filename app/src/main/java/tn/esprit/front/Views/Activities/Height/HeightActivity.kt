package tn.esprit.front.Views.Activities.Height

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tn.esprit.front.R

class HeightActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)
        supportActionBar?.setTitle("Height")
    }
}