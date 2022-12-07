package tn.esprit.front.Views.Activities.Profile

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
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
    var services = ApiInterface.create()

    lateinit var babyNameTxt: TextView
    lateinit var babyPic : ImageView
    lateinit var babyBirthdayTxt:TextView

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
        val name : String = intent.getStringExtra("BABYNAME").toString()
        mSharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE)

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
        /*babyNameTxt=findViewById(R.id.bbName)
        babyPic=findViewById(R.id.profilePic)
        babyBirthdayTxt=findViewById(R.id.bbBirthday)


        val name=intent.getStringExtra("BABYNAME")
        val email=intent.getStringExtra("EMAIL")
        val baby = Baby(email = email, babyName = name)
        /*babyPic.setImageResource(intent.getIntExtra(PICTURE,0))

        val name=intent.getStringExtra(NAME)
        babyName.text="$name"*/

        services.getBaby(baby).enqueue(object : Callback<Baby> {
            override fun onResponse(call: Call<Baby>, response: Response<Baby>) {
                if (response.code()==200) {

                    val babyName = response.body()?.babyName
                    val babyPic = response.body()?.babyPic
                    val birthday = response.body()?.birthday

                    babyNameTxt.text=babyName.toString()
                    babyBirthdayTxt.text=birthday.toString()
                }
                if (response.code()==401){
                    println("Baby not found")
                }

                if (response.code()==400){
                    println("user not found")
                }
            }
            override fun onFailure(call: Call<Baby>, t: Throwable) {
                println("error")
            }

        })




       /* val name=intent.getStringExtra("BABYNAME")
        babyName.text="$name"
        val birthday=intent.getStringExtra("BABYBIRTHDAY")
        babyBirthday.text="$birthday"*/

         */

}}