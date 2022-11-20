package tn.esprit.front.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import tn.esprit.front.R
import tn.esprit.front.Views.Home.DrawerActivity
import tn.esprit.front.Views.Home.HomeActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            val mainIntent= Intent(this,DrawerActivity::class.java)
            startActivity(mainIntent)
            finish()
        },3500)
    }
}