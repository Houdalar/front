package tn.esprit.front.Views.Home

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.Views.Home.BabyList.BabyAdapter
import tn.esprit.front.models.Baby
import tn.esprit.front.models.User
import tn.esprit.front.viewmodels.ApiInterface

class HomeActivity : AppCompatActivity() {

    var services = ApiInterface.create()

    lateinit var recyclerBaby: RecyclerView
    lateinit var recyclerBabyAdapter: BabyAdapter
    lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mSharedPreferences=getSharedPreferences("PREF_NAME", MODE_PRIVATE)
        val userEmail=mSharedPreferences.getString("email","")
        val user= User(email = userEmail.toString())

        recyclerBaby=findViewById(R.id.recyclerView)

        var babyList:MutableList<Baby> = ArrayList()

        services.getBabyList(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println("getBabylistCalled")
                if (response.isSuccessful) {
                    println(response.body())
                    Toast.makeText(
                        this@HomeActivity,
                        "List displayed ",
                        Toast.LENGTH_SHORT
                    ).show()

                    val babies= JSONTokener(response.toString()).nextValue() as JSONArray
                    for (i in 0 until babies.length()){
                        babyList.add(Baby(name = babies.getJSONObject(i).getString("babyName") ))
                    }
                    recyclerBabyAdapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.message?.let {
                    Snackbar.make(
                        findViewById(R.id.HomeLayout),
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                /*Toast.makeText(this@HomeActivity, "something went wrong", Toast.LENGTH_SHORT)
                    .show()*/
            }
        })
        recyclerBabyAdapter= BabyAdapter(babyList)
        recyclerBaby.adapter=recyclerBabyAdapter

        recyclerBaby.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)


    }
}