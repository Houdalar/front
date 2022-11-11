package tn.esprit.front.Ui

import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.util.Patterns
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.models.User
import tn.esprit.front.viewmodels.ApiInterface


class SignUp_Activity : AppCompatActivity() {

    var services = ApiInterface.create()


    lateinit var name: TextInputEditText
    lateinit var email: TextInputEditText
    lateinit var password: TextInputEditText
    lateinit var SignupButton: Button
    lateinit var errorName: TextInputLayout
    lateinit var errorEmail: TextInputLayout
    lateinit var errorPassword: TextInputLayout



    override fun onCreate(savedInstanceState: Bundle?)
    {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_screen)

        supportActionBar?.hide()

        SignupButton =findViewById(R.id.Sign_up)
        name =findViewById(R.id.name)
        email = findViewById(R.id.EmailText)
        password = findViewById(R.id.PasswordText)

        errorName=findViewById(R.id.ParentNameTextField)
        errorEmail=findViewById(R.id.EmailTextField)


        SignupButton.setOnClickListener { signUp() }
    }
    //add user to database
    private fun signUp()
    {
        if (validate())
        {
            val user = User(name.text.toString(), email.text.toString(), password.text.toString())
            services.signup(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            this@SignUp_Activity,
                            "User added successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SignUp_Activity, Login_Activity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(
                            this@SignUp_Activity,
                            "User already exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable)
                {
                    Toast.makeText(this@SignUp_Activity, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun validate(): Boolean {
        var Name=true
        var Email=true
        var Pwd=true

        errorName?.error =null
        errorEmail?.error =null
        errorPassword?.error =null

        if(name?.text!!.isEmpty())
        {
            errorName?.error="Please enter your name!"
            Name=false
        }

        if(email?.text!!.isEmpty())
        {
            errorEmail?.error="Please enter your e-mail!"
            Email=false
        }

        if(password?.text!!.isEmpty())
        {
            errorPassword?.error="Please enter your password !"
            Pwd=false
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email?.text!!).matches() && name?.text!!.isNotEmpty())
        {

            errorEmail?.error="Email not valid !"
            Email= false
        }
        if (Name===false || Email===false||Pwd===false )
        {
            return false
        }
        return true
    }
}

