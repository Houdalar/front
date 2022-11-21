package tn.esprit.front.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import tn.esprit.front.R
import tn.esprit.front.models.NAME
import tn.esprit.front.models.PICTURE
import tn.esprit.front.viewmodels.ApiInterface

class ProfileActivity : AppCompatActivity() {
    var services = ApiInterface.create()

    lateinit var babyName: TextView
    lateinit var birthday: TextView
    lateinit var babyPic : ImageView
    lateinit var gender:TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        babyName=findViewById(R.id.bbName)
        babyPic=findViewById(R.id.profilePic)

        babyPic.setImageResource(intent.getIntExtra(PICTURE,0))

        val name=intent.getStringExtra(NAME)
        babyName.text="$name"
    }
}