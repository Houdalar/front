package tn.esprit.front.Views

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import tn.esprit.front.Views.Home.HomeActivity
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

    private lateinit var babyName: TextInputEditText
    lateinit var birthday: TextInputEditText

    private lateinit var babyNameError: TextInputLayout
    lateinit var birthdayError: TextInputLayout

    lateinit var rdBoy:RadioButton
    lateinit var rdGirl:RadioButton

    lateinit var startBtn: MaterialButton

    private  var babyPic: ImageView?=null
    private  var selectedImageUri: Uri?=null
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

        babyName=findViewById(R.id.BabyName)
        birthday=findViewById(R.id.BabyBirthday)

        birthdayError=findViewById(R.id.BirthdayLayout)
        babyNameError=findViewById(R.id.BabyNameLayout)

        startBtn=findViewById(R.id.startBtn)
        rdBoy=findViewById(R.id.rbBoy)
        rdGirl=findViewById(R.id.rbGirl)

        babyPic=findViewById(R.id.AddBabyPic)

        mSharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE)



        babyPic!!.setOnClickListener {
            openGallery()
        }

        startBtn.setOnClickListener {
           addBaby()
        }


    }

    private fun addBaby() {
        if (validate()){
            val email = mSharedPreferences.getString("email", "")
            val baby=Baby(babyName.text.toString(), birthday.text.toString(), selectedImageUri.toString(), email.toString())
            services.addBaby(baby).enqueue(object: Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful){
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            getString(R.string.babyAddedSuccessfully),
                            Snackbar.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@AddBabyActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Snackbar.make(
                            findViewById(R.id.scrollViewAddBaby),
                            getString(R.string.babyAddedFailure),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Snackbar.make(
                        findViewById(R.id.scrollViewAddBaby),
                        getString(R.string.babyAddError),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            })
        }
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