package tn.esprit.front.Views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import tn.esprit.front.Views.Activities.Home.DrawerActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_add_baby2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.Views.Activities.Signin.TOKEN
import tn.esprit.front.models.Baby
import tn.esprit.front.models.User
import tn.esprit.front.viewmodels.ApiInterface

class AddBabyActivity : AppCompatActivity() {
    var services = ApiInterface.create()

    lateinit var babyName: TextInputEditText
    lateinit var birthday: TextInputEditText

    lateinit var babyNameError: TextInputLayout
    lateinit var birthdayError: TextInputLayout

    lateinit var rdBoy:RadioButton
    lateinit var rdGirl:RadioButton

    lateinit var startBtn: MaterialButton
    lateinit var mSharedPreferences: SharedPreferences

    lateinit  var babyPic: ImageView
    private  var selectedImageUri: Uri?=null
    lateinit var testEmail:TextView
    private var PREF_NAME:String?="PREF_NAME"


    private val startForResultOpenGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                selectedImageUri = result.data!!.data
                babyPic!!.setImageURI(selectedImageUri)
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_baby2)

        supportActionBar?.hide()


        startBtn=findViewById(R.id.addBtn)
        babyPic=findViewById(R.id.AddBabyPic)
        babyName=findViewById(R.id.BabyName)
        birthday=findViewById(R.id.BabyBirthday)

        birthdayError=findViewById(R.id.BirthdayLayout)
        babyNameError=findViewById(R.id.BabyNameLayout)

        mSharedPreferences=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)


        babyPic!!.setOnClickListener {
            openGallery()
        }

        startBtn.setOnClickListener {
            //start()

        }

        /*babyName=findViewById(R.id.BabyName)
        birthday=findViewById(R.id.BabyBirthday)



        startBtn=findViewById(R.id.startBtn)
        rdBoy=findViewById(R.id.rbBoy)
        rdGirl=findViewById(R.id.rbGirl)

        babyPic=findViewById(R.id.AddBabyPic)

        mSharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE)*/




        val babyName=findViewById<TextInputEditText>(R.id.BabyName)
        startBtn.setOnClickListener {
            AddBaby(BabyName.text.toString(),BabyBirthday.text.toString())
            val intent = Intent(this@AddBabyActivity, DrawerActivity::class.java)
            startActivity(intent)
        }


    }
    private fun AddBaby(babyName : String,birthday: String)
    {
        mSharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val map: HashMap<String, String> = HashMap()

        map["token"] = intent.getStringExtra("token").toString()
        map["babyName"] = babyName
        map["birthday"] = birthday
        Log.e("token : ",intent.getStringExtra("token").toString())
        Log.e("babyname : ",babyName)
        Log.e("birthday : ",birthday)
        services.addBaby(map).enqueue(object : Callback<Baby> {
            override fun onResponse(call: Call<Baby>, response: Response<Baby>) {
                println("Add")

                if (response.isSuccessful) {
                    val babies = response.body()
                    Log.e("babies : ",babies.toString())
                    println(response.body())
                    Toast.makeText(
                        this@AddBabyActivity,
                        "Added",
                        Toast.LENGTH_SHORT
                    ).show()


                } else {
                    //babyList.add(Baby("wafa","21/11/2022","","ines.said@esprit.tn"))
                    Toast.makeText(this@AddBabyActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Baby>, t: Throwable) {
                Toast.makeText(this@AddBabyActivity, "Error", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun openGallery() {
        val intent= Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type="image/*"
        startForResultOpenGallery.launch(intent)
    }


    private fun validate(): Boolean {
        var name=true
        var date=true

        birthday?.error=null
        birthdayError?.error=null

        if (selectedImageUri == null) {
            Snackbar.make(
                findViewById(R.id.scrollViewAddBaby),
                getString(R.string.selectImageError),
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }

        if(babyName?.text!!.isEmpty())
        {
            babyNameError?.error=getString(R.string.FieldEmptyError)
            name=false
        }
        if(birthday?.text!!.isEmpty())
        {
            birthdayError?.error=getString(R.string.FieldEmptyError)
            date=false
        }
        if(!name || !date){
            return false
        }
        return true
    }
}


