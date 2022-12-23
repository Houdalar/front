package tn.esprit.front.Ui.AudioBooks

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.TextView.BufferType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import androidx.core.graphics.drawable.*
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.AudioBook
import tn.esprit.front.viewmodels.AudioBookAPi


class full_AudioBook : AppCompatActivity() {
    lateinit var bookTitle: TextView
    lateinit var Author: TextView
    lateinit var narrator: TextView
    lateinit var cover: ImageView
    lateinit var category: TextView
    lateinit var language: TextView
    lateinit var Description: TextView
    lateinit var Rating: TextView
    lateinit var duration: TextView
    lateinit var listen : ImageView
    lateinit var save : ImageView
    lateinit var similar : RecyclerView
    lateinit var similarAdapter : book_adapter
     var similarList : ArrayList<AudioBook> = ArrayList()
    lateinit var barvector : ImageView
    var isfavorite = false
    lateinit var sharedPreferences: SharedPreferences

    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_audio_book)
        supportActionBar?.hide()

        cover = findViewById(R.id.cover)
        val url = intent.getStringExtra("bookcover")
        Glide.with(this).load(url).into(cover)
        barvector = findViewById(R.id.imageView2)



       /* val bitmap = cover.drawable.toBitmap()
        Palette.from(bitmap).generate { palette ->
            val vibrant = palette?.vibrantSwatch
            val vibrantDark = palette?.darkVibrantSwatch
            val vibrantLight = palette?.lightVibrantSwatch
            val muted = palette?.mutedSwatch
            val mutedDark = palette?.darkMutedSwatch
            val mutedLight = palette?.lightMutedSwatch
            val dominant = palette?.dominantSwatch

            val swatches = listOf(
                vibrant,
                vibrantDark,
                vibrantLight,
                muted,
                mutedDark,
                mutedLight,
                dominant,

            )
            val swatch = swatches.first { it != null }
            val color = swatch?.rgb ?: Color.WHITE
            val colorDark = swatch?.bodyTextColor ?: Color.WHITE
            val colorLight = swatch?.titleTextColor ?: Color.WHITE
            barvector.setBackgroundColor(color)
            barvector.setColorFilter(colorDark)
        }*/


        bookTitle = findViewById(R.id.bookTitle)
        bookTitle.text = intent.getStringExtra("title")



        narrator = findViewById(R.id.narrator)
        narrator.text = intent.getStringExtra("narrator")

        Author = findViewById(R.id.author)
        Author.text = intent.getStringExtra("author")

        Description = findViewById(R.id.description)
        Description.text = intent.getStringExtra("Description")



        category = findViewById(R.id.category)
        category.text = intent.getStringExtra("category")

        language = findViewById(R.id.language)
        language.text = intent.getStringExtra("language")

        duration = findViewById(R.id.duration)
        duration.text = intent.getStringExtra("duration")

        Rating = findViewById(R.id.rating)
        Rating.text = intent.getFloatExtra("Rating", 0.0f).toString()



        similar = findViewById(R.id.similarBooks)
        listen = findViewById(R.id.imageView16)
        save = findViewById(R.id.imageView17)

        sharedPreferences = getSharedPreferences("sharedPrefs", 0)
        var favorite: MutableList<String>?
        favorite = sharedPreferences.getStringSet("bookshelf", mutableSetOf())?.toMutableList()

        if (favorite == null) {
            favorite = mutableListOf()
        }
        if (favorite.contains(bookTitle.text.toString())) {
            save.setImageResource(R.drawable.ic_baseline_favorite_24)
            isfavorite = true
        } else {
            save.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            isfavorite = false
        }



        listen.setOnClickListener {
            intent = android.content.Intent(this, audioplayer::class.java).apply {
                putExtra("url", intent.getStringExtra("bookUrl"))
                putExtra("title", intent.getStringExtra("title"))
                putExtra("author", intent.getStringExtra("author"))
                putExtra("narrator", intent.getStringExtra("narrator"))
                putExtra("duration", intent.getStringExtra("duration"))
                putExtra("bookcover", intent.getStringExtra("bookcover"))
                putExtra("Description", intent.getStringExtra("Description"))
                putExtra("category", intent.getStringExtra("category"))
                putExtra("language", intent.getStringExtra("language"))
                putExtra("Rating", intent.getFloatExtra("Rating", 0.0f))
            }
            startActivity(intent)
        }

        save.setOnClickListener {
            val api = AudioBookAPi.create()
            val map: HashMap<String, String> = HashMap()
            val token : String ="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOGI5MWUxNjc1ZTE2MTNlOTBlMTYyZiIsImlhdCI6MTY3MDc0MTg1MH0.GPsTqD7vbaBS65dsUJdfbPcU0Zdh4kmH4i8irCWgP5M"
            map["token"]=token
            map["title"]=intent.getStringExtra("title").toString()

            if(isfavorite==true){

            val call = api.addFavoritesbooks(map)
            call.enqueue(object : Callback<AudioBook> {
                override fun onResponse(call: Call<AudioBook>, response: Response<AudioBook>) {
                    if (response.isSuccessful) {
                        save.setImageResource(R.drawable.ic_baseline_favorite_24)
                        Toast.makeText(this@full_AudioBook, "book added succesfully", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<AudioBook>, t: Throwable) {
                    Toast.makeText(this@full_AudioBook, "Error", Toast.LENGTH_SHORT).show()
                }
            })
                isfavorite=false
        }
            else{
                val call = api.removeFavoritesbooks(map)
                call.enqueue(object : Callback<AudioBook> {
                    override fun onResponse(call: Call<AudioBook>, response: Response<AudioBook>) {
                        if (response.isSuccessful) {
                            save.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                            Toast.makeText(this@full_AudioBook, "book deleted succesfully", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AudioBook>, t: Throwable) {
                        Toast.makeText(this@full_AudioBook, "Error", Toast.LENGTH_SHORT).show()
                    }
                })
                isfavorite=true
            }
        }

        val map: HashMap<String, String> = HashMap()
        map["category"]=category.text.toString()
        var services = AudioBookAPi.create()
        services.getAudioBookByCategory(map)!!.enqueue(object : Callback<MutableList<AudioBook>> {
            override fun onResponse(
                call: Call<MutableList<AudioBook>>,
                response: Response<MutableList<AudioBook>>
            ) {
                if (response.code() == 200) {
                    val List = response.body() as MutableList<AudioBook>
                    similarAdapter = book_adapter(List)
                    similar.adapter = similarAdapter
                    similar.layoutManager = LinearLayoutManager(this@full_AudioBook, LinearLayoutManager.HORIZONTAL, false)
                } else {
                    Toast.makeText(this@full_AudioBook, "error ", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MutableList<AudioBook>>, t: Throwable) {
                Toast.makeText(this@full_AudioBook, "error while getting the AudioBook", Toast.LENGTH_SHORT).show()
            }

        })
    }


}