package tn.esprit.front.Ui.PlayList

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

import com.google.android.material.textfield.TextInputLayout
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.PlayList
import tn.esprit.front.viewmodels.musicApi
import java.io.File


class addplaylist : AppCompatActivity() {
    lateinit var playlistname: EditText
    lateinit var playlistnameError: TextInputLayout
    lateinit var cover : ImageView
    lateinit var create : Button
    lateinit var cancel : Button
    private  var selectedImageUri: Uri?=null
    val services = musicApi.create()
    val token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOGI5MWUxNjc1ZTE2MTNlOTBlMTYyZiIsImlhdCI6MTY3MDc0MTg1MH0.GPsTqD7vbaBS65dsUJdfbPcU0Zdh4kmH4i8irCWgP5M"
    private val startForResultOpenGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                 selectedImageUri = result.data!!.data
                cover!!.setImageURI(selectedImageUri)
                cover!!.scaleType = ImageView.ScaleType.FIT_XY
                cover!!.adjustViewBounds = true
                cover!!.setPadding(0, 0, 0, 0)
                cover!!.scaleType = ImageView.ScaleType.CENTER_CROP
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addplaylistactivity)
        supportActionBar?.hide()
        playlistname = findViewById(R.id.playlist_name)
        playlistnameError = findViewById(R.id.playlist_name_Error)
        cover = findViewById(R.id.playlist_cover)
        create = findViewById(R.id.create_playlist)
        cancel = findViewById(R.id.cancel_button)

        //playlistcover.setImageResource(R.drawable.ic_baseline_add_to_photos_24

        //upload cover from gallery  and set it to the image view
        cover.setOnClickListener {
            val intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type="image/*"
            startForResultOpenGallery.launch(intent)
        }
        cancel.setOnClickListener(){
            finish()
        }

        //create playlist with the name and the cover image
        create.setOnClickListener(){
            createPlaylist()





        }
    }


    private fun validateName(): Boolean {
        if (selectedImageUri == null) {
            playlistnameError.error = "Please select an image"
            return false
        }
        val name = playlistname.text.toString()
        return if (name.isEmpty()) {
            playlistnameError.error = "Field can't be empty"
            false
        } else {
            playlistnameError.error = null
            true
        }
    }

    private fun createPlaylist()
    {
        //send all the name and the cover image in a multipartformdata
        if (validateName()) {
            val name = playlistname.text.toString()
            val file = File(selectedImageUri!!.path)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("cover", file.name, requestFile)
            println("body : $body")
            val namebody = MultipartBody.Part.createFormData("name", name)
            println("namebody : $namebody")
            val tokenbody = MultipartBody.Part.createFormData("token", token)
            println("tokenbody : $tokenbody")
            services.addPlayListToUser(body,namebody,  tokenbody).enqueue(object :
                Callback<PlayList> {
                override fun onResponse(call: Call<PlayList>, response: Response<PlayList>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@addplaylist, "playlist created", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<PlayList>, t: Throwable) {
                    Toast.makeText(this@addplaylist, "playlist not created", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}