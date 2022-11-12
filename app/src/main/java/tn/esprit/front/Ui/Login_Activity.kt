package tn.esprit.front.Ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.User
import tn.esprit.front.viewmodels.ApiInterface

const val PREF_NAME = "LOGIN_PREF_Bear"
const val IS_REMEMBRED = "IS_REMEMBRED"

class Login_Activity : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var mailError: TextInputLayout
    lateinit var password: EditText
    lateinit var passwordError: TextInputLayout

    lateinit var forgotYourPassword : TextView
    lateinit var rememberMe: CheckBox

    lateinit var preference : SharedPreferences

    lateinit var backToSignUpButton : Button
    lateinit var loginButton: Button

    var services = ApiInterface.create()


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)

        supportActionBar?.hide()

        email = findViewById(R.id.txtEmail)
        mailError = findViewById(R.id.txtLayoutEmail)
        password = findViewById(R.id.txtPassword)
        passwordError = findViewById(R.id.txtLayoutPassword)

        loginButton = findViewById(R.id.login_button)
        backToSignUpButton = findViewById(R.id.back_to_sign_up_button)
        rememberMe = findViewById(R.id.Remember_Me)

        forgotYourPassword = findViewById(R.id.forgot_password)

        preference=getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

//        if (rememberMe.isChecked)
//        {
//            val editor = preference.edit()
//            editor.putBoolean(IS_REMEMBRED, true)
//            editor.apply()
//        }
//        val intent = Intent(this@Login_Activity, Home::class.java)
//        startActivity(intent)



        loginButton.setOnClickListener()
        {
            clickLogin()
        }

        backToSignUpButton.setOnClickListener()
        {
            val intent = Intent(this@Login_Activity, SignUp_Activity::class.java)
            startActivity(intent)
        }

    }
    private fun clickLogin()
    {
        if (validate())
        {
            val user = User( email.text.toString(), password.text.toString())
            services.login(user).enqueue(object : Callback<User>
            {
                override fun onResponse(call: Call<User>, response: Response<User>)
                {
                    if (rememberMe.isChecked)
                    {
                        val editor = preference.edit()
                        editor.putBoolean(IS_REMEMBRED, true)
                        editor.apply()
                        val intent = Intent(this@Login_Activity, Home::class.java)
                        startActivity(intent)
                    }

                    if (response.isSuccessful)
                    {
                        val intent = Intent(this@Login_Activity, Home::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this@Login_Activity, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable)
                {
                    Log.d("Error", t.message.toString())
                }
            })
        }
    }

    private fun validate():Boolean
    {
        var mail:Boolean=true
        var pswd:Boolean=true

        passwordError?.error =null
        mailError?.error =null

        if(email?.text!!.isEmpty())
        {
            mailError?.error="Please enter your e-mail !"
            mail=false
        }
        if(password?.text!!.isEmpty())
        {
            passwordError?.error="Please enter your password !"
            pswd=false
        }

        if (pswd===false || mail===false)
        {
            return false
        }
        return true
    }
}