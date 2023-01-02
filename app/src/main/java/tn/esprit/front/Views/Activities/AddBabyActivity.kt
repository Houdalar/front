package tn.esprit.front.Views.Activities

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
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import tn.esprit.front.Views.Activities.Home.DrawerActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.Views.Activities.Profile.ProfileActivity
import tn.esprit.front.models.Baby
import tn.esprit.front.util.UploadRequestBody
import tn.esprit.front.util.getFileName
import tn.esprit.front.viewmodels.BabyViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

const val GENDER = "GENDER"

class AddBabyActivity : AppCompatActivity(),UploadRequestBody.UploadCallback {
    var services = BabyViewModel.create()

    lateinit var BabyName: TextInputEditText
    lateinit var Birthday: TextInputEditText

    lateinit var babyNameError: TextInputLayout
    lateinit var birthdayError: TextInputLayout

    lateinit var rbBoy: RadioButton
    lateinit var rbGirl:RadioButton

    lateinit var startBtn: MaterialButton
    lateinit var mSharedPreferences: SharedPreferences

    lateinit  var babyPic: ImageView
    private  var selectedImageUri: Uri?=null
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
        BabyName=findViewById(R.id.BabyName)
        Birthday=findViewById(R.id.BabyBirthday)

        birthdayError=findViewById(R.id.BirthdayLayout)
        babyNameError=findViewById(R.id.BabyNameLayout)

        rbBoy=findViewById(R.id.rbBoy)
        rbGirl=findViewById(R.id.rbGirl)

        mSharedPreferences=getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)


        babyPic!!.setOnClickListener {
            openGallery()
        }
        startBtn.setOnClickListener {
            var gender:String?=null
            if (validate()){
                if (rbBoy!!.isChecked){
                    gender="Boy"
                }
                else{
                    gender="Girl"
                }
                AddBaby(BabyName.text.toString(),Birthday.text.toString(),gender)
                val intent = Intent(this@AddBabyActivity, DrawerActivity::class.java).apply {
                    if (rbBoy!!.isChecked){
                        putExtra(GENDER,rbBoy!!.text.toString())
                    }
                    if (rbGirl!!.isChecked){
                        putExtra(GENDER,rbGirl!!.text.toString())
                    }
                }
                startActivity(intent)
            }
        }

        val birthdayPicker= MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select baby's birthday")
            .build()

        birthdayPicker.addOnPositiveButtonClickListener {
            Birthday.setText(birthdayPicker.headerText.toString())
        }

        Birthday.setOnFocusChangeListener  {view, hasFocus ->
               if (hasFocus){
                birthdayPicker.show(supportFragmentManager,"BIRTHDAY_PICKER")
            }else{
                birthdayPicker.dismiss()
            }        }

    }



    private fun AddBaby(babyName : String,birthday: String,gender:String)
    {
        var parcelFileDescriptor = contentResolver.openFileDescriptor(selectedImageUri!!,"r",null) ?: return
        var inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file= File(cacheDir,contentResolver.getFileName(selectedImageUri!!))


        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        val uploadRequestFile = UploadRequestBody(file,"image",this)
        Log.e("uploadRequestFile",uploadRequestFile.toString())

        mSharedPreferences=getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val token=intent.getStringExtra("token").toString()

        val babyPic=MultipartBody.Part.createFormData("babyPic",file?.name,uploadRequestFile)

        val babyName=MultipartBody.Part.createFormData("babyName",babyName)
        val birthday=MultipartBody.Part.createFormData("birthday",birthday)
        val gender=MultipartBody.Part.createFormData("gender",gender)



        services.addBaby(babyName,birthday,babyPic,gender,token).enqueue(object : Callback<Baby> {
            override fun onResponse(call: Call<Baby>, response: Response<Baby>) {
                if (response.isSuccessful) {
                    Log.d("AddBaby", "onResponse: ${response.body()}")
                    Toast.makeText(this@AddBabyActivity, "Baby added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("AddBaby", "onResponse: ${response.errorBody()}")
                    Toast.makeText(this@AddBabyActivity, "Error while adding", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Baby>, t: Throwable) {
                Log.d("AddBaby", "onFailure: ${t.message}")
                Toast.makeText(this@AddBabyActivity, "Failed to add", Toast.LENGTH_SHORT).show()
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

        Birthday?.error=null
        birthdayError?.error=null

        if (selectedImageUri == null) {
            Snackbar.make(
                findViewById(R.id.scrollViewAddBaby),
                getString(R.string.selectImageError),
                Snackbar.LENGTH_SHORT
            ).show()
            return false
        }

        if(BabyName?.text!!.isEmpty())
        {
            babyNameError?.error=getString(R.string.FieldEmptyError)
            name=false
        }
        if(Birthday?.text!!.isEmpty())
        {
            birthdayError?.error=getString(R.string.FieldEmptyError)
            date=false
        }
        if(!name || !date){
            return false
        }
        return true
    }

    override fun onProgressUpdate(pecentage: Int) {
    }
}


