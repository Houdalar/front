package tn.esprit.front.Ui.PlayList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.PlayList
import tn.esprit.front.viewmodels.musicApi


class addplaylist : AppCompatActivity() {
    lateinit var playlistname: EditText
    lateinit var playlistnameError: TextInputLayout
    lateinit var cover : ImageView
    lateinit var create : Button
    lateinit var cancel : Button
    var map = HashMap<String, String>()
    private val startForResultOpenGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                var selectedImageUri = result.data!!.data
                cover!!.setImageURI(selectedImageUri)
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.addplaylistactivity)
        playlistname = findViewById(R.id.playlist_name)
        playlistnameError = findViewById(R.id.playlist_name_Error)
        cover = findViewById(R.id.playlist_cover)
        create = findViewById(R.id.create_playlist)
        cancel = findViewById(R.id.cancel_button)

        //playlistcover.setImageResource(R.drawable.ic_baseline_add_to_photos_24

        //upload cover from gallery  and set it to the image view
        cover.setOnClickListener {
            val intent= Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type="image/*"
            startForResultOpenGallery.launch(intent)
        }

       map["name"] = playlistname.text.toString()
     //   map["cover"] = playlistcover.Uri.toString()
        map["token"]="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYzOGI5MWUxNjc1ZTE2MTNlOTBlMTYyZiIsImlhdCI6MTY3MDgzMzAxNH0.xtR83b0vClblof3bw4vQ7xu29mcAJNZl8IyHCWpSxG8"
        create.setOnClickListener(){
            if(validateName()){
                musicApi.create().addPlayListToUser(map).enqueue(object : Callback<PlayList> {
                    override fun onResponse(call: Call<PlayList>, response: Response<PlayList>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@addplaylist, "playlist added", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@addplaylist, music_home_page::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@addplaylist, "playlist not added", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PlayList>, t: Throwable) {
                        Toast.makeText(this@addplaylist, "playlist not added", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        }
        cancel.setOnClickListener(){
            finish()
        }
    }
   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            val selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
            playlistcover.setImageBitmap(bitmap)
            playlistcover.setImageURI(selectedPhotoUri)

            //upload the image to the server
        }*/

    private fun validateName(): Boolean {
        val name = playlistname.text.toString()
        return if (name.isEmpty()) {
            playlistnameError.error = "Field can't be empty"
            false
        } else {
            playlistnameError.error = null
            true
        }
    }
}