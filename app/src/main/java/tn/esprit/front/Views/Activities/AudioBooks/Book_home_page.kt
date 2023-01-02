package tn.esprit.front.Views.Activities.AudioBooks

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tn.esprit.front.R


class book_home_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_home_page)
        supportActionBar?.hide()

        val bookPager = findViewById<ViewPager>(R.id.book_viewPager)
        bookPager.adapter = bookviewpager(supportFragmentManager)

        val options = findViewById<TabLayout>(R.id.options)
        options.setupWithViewPager(bookPager)

    }
}


