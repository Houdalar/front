package tn.esprit.front.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import tn.esprit.front.R
import tn.esprit.front.viewmodels.ApiInterface

class ProfileActivity : AppCompatActivity() {
    var services = ApiInterface.create()

    lateinit var babyName: TextView
    lateinit var birthday: TextView
    lateinit var gender:TextView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        babyName=findViewById(R.id.bbName)
        birthday=findViewById(R.id.bbBirthday)
        gender=findViewById(R.id.bbGender)
    }
}