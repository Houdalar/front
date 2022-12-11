package tn.esprit.front.Views.Activities.Profile

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.Views.Activities.Consultation.ConsultationActivity
import tn.esprit.front.Views.Activities.Height.HeightActivity
import tn.esprit.front.Views.Activities.Signin.PREF_NAME
import tn.esprit.front.Views.Activities.Signin.TOKEN
import tn.esprit.front.Views.Activities.Vaccins.VaccinsActivity
import tn.esprit.front.Views.Activities.Weight.WeightActivity
import tn.esprit.front.models.Baby
import tn.esprit.front.viewmodels.ApiInterface

class ProfileActivity : AppCompatActivity() {
    lateinit var babyPic : ImageView
    lateinit var heightBtn:MaterialButton
    lateinit var weightBtn:MaterialButton
    lateinit var doctorBtn:MaterialButton
    lateinit var vaccinesBtn:MaterialButton
    lateinit var mSharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        supportActionBar?.hide()

        heightBtn=findViewById(R.id.HeightBtn)
        weightBtn=findViewById(R.id.WeightBtn)
        doctorBtn=findViewById(R.id.DoctorBtn)
        vaccinesBtn=findViewById(R.id.VaccinesBtn)
        babyPic=findViewById(R.id.profilePic)
        val name : String = intent.getStringExtra("BABYNAME").toString()
        val picture:String=intent.getStringExtra("BABYPIC").toString()
        mSharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        Picasso.with(this).load(picture).into(babyPic)

        heightBtn.setOnClickListener {
            val intent=Intent(this,HeightActivity::class.java)
            intent.apply {
                putExtra("BABYNAME", name)
                putExtra("token", mSharedPreferences.getString(TOKEN,"").toString())
            }
            startActivity(intent)
        }

        weightBtn.setOnClickListener {
            val intent=Intent(this,WeightActivity::class.java)
            intent.apply {
                putExtra("BABYNAME", name)
                putExtra("token", mSharedPreferences.getString(TOKEN,"").toString())
            }
            startActivity(intent)
        }

        doctorBtn.setOnClickListener {
            val intent=Intent(this,ConsultationActivity::class.java)
            intent.apply {
                putExtra("BABYNAME", name)
                putExtra("token", mSharedPreferences.getString(TOKEN,"").toString())
            }
            startActivity(intent)
        }

        vaccinesBtn.setOnClickListener {
            val intent=Intent(this,VaccinsActivity::class.java)
            intent.apply {
                putExtra("BABYNAME", name)
                putExtra("token", mSharedPreferences.getString(TOKEN,"").toString())
            }
            startActivity(intent)
        }
        bbName.text = intent.getStringExtra("BABYNAME").toString()
        bbBirthday.text = intent.getStringExtra("DATE").toString()
}}