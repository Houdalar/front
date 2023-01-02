package tn.esprit.front.Views.Activities.PlayList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import tn.esprit.front.R

class music_home_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_music_home_page)
        val playlistviewPager = findViewById<ViewPager>(R.id.music_viewPager)
        playlistviewPager.adapter = Page_Adapter(supportFragmentManager)

        val musicviewPager = findViewById<ViewPager>(R.id.musicviewPager)
        musicviewPager.adapter = musicpageadapter(supportFragmentManager)



        val categories = findViewById<TabLayout>(R.id.categories)
        categories.setupWithViewPager(playlistviewPager)

        val newTop = findViewById<TabLayout>(R.id.top_new)
        newTop.setupWithViewPager(musicviewPager)



    }

}