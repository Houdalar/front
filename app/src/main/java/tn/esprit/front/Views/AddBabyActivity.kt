package tn.esprit.front.Views

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import tn.esprit.front.Views.Home.DrawerActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
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

    lateinit  var babyPic: ImageView
    private  var selectedImageUri: Uri?=null
    lateinit var testEmail:TextView
    lateinit var mSharedPreferences: SharedPreferences
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


        startBtn=findViewById(R.id.startBtn)
        babyPic=findViewById(R.id.AddBabyPic)
        babyName=findViewById(R.id.BabyName)
        birthday=findViewById(R.id.BabyBirthday)

        birthdayError=findViewById(R.id.BirthdayLayout)
        babyNameError=findViewById(R.id.BabyNameLayout)

        testEmail=findViewById(R.id.testEmail)

        mSharedPreferences=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)


        babyPic!!.setOnClickListener {
            openGallery()
        }

        startBtn.setOnClickListener {
            start()
        }

        /*babyName=findViewById(R.id.BabyName)
        birthday=findViewById(R.id.BabyBirthday)



        startBtn=findViewById(R.id.startBtn)
        rdBoy=findViewById(R.id.rbBoy)
        rdGirl=findViewById(R.id.rbGirl)

        babyPic=findViewById(R.id.AddBabyPic)

        mSharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE)*/





        /*startBtn.setOnClickListener {
            val intent = Intent(this@AddBabyActivity, DrawerActivity::class.java)
            startActivity(intent)
        }*/


    }

    private fun start() {
        if (validate()){
            val email = mSharedPreferences.getString("email", "")
            println(email)
            val baby=Baby(email="inessaid1905@gmail.com",babyName=babyName.text.toString(),birthday=birthday.text.toString(),babyPic=selectedImageUri.toString())
            println(email)
            println(babyName.text.toString())
            println(birthday.text.toString())
            println(selectedImageUri.toString())
            services.addBaby(baby).enqueue(object:Callback<Baby>{
                override fun onResponse(call: Call<Baby>, response: Response<Baby>) {
                    if(response.isSuccessful){
                        println(response.message())
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            getString(R.string.babyAddedSuccessfully),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@AddBabyActivity, DrawerActivity::class.java)
                        startActivity(intent)
                    }
                    if (response.code()==401)
                    {
                        println( response.message())
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            getString(R.string.babyExistes),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Baby>, t: Throwable) {
                    t.message?.let {
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            it,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }

            })
        }
    }
    /*private fun addBaby() {
        if (validate()){
            val email = mSharedPreferences.getString("email", "")
            println(email.toString())
            val baby=Baby(babyName.text.toString(), birthday.text.toString(), selectedImageUri.toString(), "inessaid1905@gmail.com")
            println(email.toString())
            println(babyName.text.toString())
            println(birthday.text.toString())
            println(selectedImageUri.toString())
            services.addBaby(baby).enqueue(object: Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        println(response.message())
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            getString(R.string.babyAddedSuccessfully),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@AddBabyActivity, DrawerActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        println( response.message())
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            getString(R.string.babyAddedFailure),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    t.message?.let {
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            it,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
    }*/

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


