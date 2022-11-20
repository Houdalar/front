package tn.esprit.front.Views.Home

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_drawer.*
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.R
import tn.esprit.front.Views.*
import tn.esprit.front.Views.Home.BabyList.BabyAdapter
import tn.esprit.front.models.Baby
import tn.esprit.front.models.User
import tn.esprit.front.viewmodels.ApiInterface

class DrawerActivity : AppCompatActivity() {
    var services = ApiInterface.create()

    lateinit var recyclerBaby: RecyclerView
    lateinit var recyclerBabyAdapter: BabyAdapter
    lateinit var mSharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {

        mSharedPreferences=getSharedPreferences("PREF_NAME", MODE_PRIVATE)
        val userEmail=mSharedPreferences.getString("email","")
        val user= User(email = userEmail.toString())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        recyclerBaby=findViewById(R.id.recyclerView)

        var babyList:MutableList<Baby> = ArrayList()

        babyList.add(Baby("test 1","20/11/2022",R.drawable.ines,"inessaid1905@gmail.com"))
        babyList.add(Baby("test 2","20/11/2022",R.drawable.gradient_splash,"inessaid1905@gmail.com"))
        babyList.add(Baby("test 3","20/11/2022",R.drawable.ines,"inessaid1905@gmail.com"))

        /* services.getBabyList(user).enqueue(object : Callback<User> {
             override fun onResponse(call: Call<User>, response: Response<User>) {
                 println("getBabylistCalled")
                 if (response.isSuccessful) {
                     println(response.body())
                     Toast.makeText(
                         this@DrawerActivity,
                         "List displayed ",
                         Toast.LENGTH_SHORT
                     ).show()

                     val babies= JSONTokener(response.toString()).nextValue() as JSONArray
                     for (i in 0 until babies.length()){
                         babyList.add(Baby(name = babies.getJSONObject(i).getString("babyName") ))
                     }
                     recyclerBabyAdapter.notifyDataSetChanged()

                 } else {
                     Toast.makeText(this@DrawerActivity, "Error", Toast.LENGTH_SHORT)
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
                 *//*Toast.makeText(this@HomeActivity, "something went wrong", Toast.LENGTH_SHORT)
                    .show()*//*
            }
        })*/
        recyclerBabyAdapter= BabyAdapter(babyList)
        recyclerBaby.adapter=recyclerBabyAdapter

        recyclerBaby.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
      //  actionBar?.setDisplayHomeAsUpEnabled(true)


  /*      recyclerBaby=findViewById(R.id.recyclerView)

        var babyList:MutableList<Baby> = ArrayList()

        services.getBabyList(user).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                if (response.isSuccessful) {
                    Toast.makeText(
                        this@DrawerActivity,
                        "List displayed ",
                        Toast.LENGTH_SHORT
                    ).show()

                    val babies= JSONTokener(response.toString()).nextValue() as JSONArray
                    for (i in 0 until babies.length()){
                        babyList.add(Baby(name = babies.getJSONObject(i).getString("babyName") ))
                    }

                } else {
                    Toast.makeText(this@DrawerActivity, "Error", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                t.message?.let {
                    Snackbar.make(
                        findViewById(R.id.DrawerLayout),
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                *//*Toast.makeText(this@HomeActivity, "something went wrong", Toast.LENGTH_SHORT)
                    .show()*//*
            }
        })
        recyclerBabyAdapter= BabyAdapter(babyList)
        recyclerBaby.adapter=recyclerBabyAdapter

        recyclerBaby.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)*/

        nav_view.setNavigationItemSelectedListener {
            it.isChecked=true

            when (it.itemId) {
                R.id.nav_home -> {
                    val intent= Intent(this, DrawerActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_babies -> {
                    val intent= Intent(this,ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_music -> {
                    val intent= Intent(this, MusicActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_Audiobooks -> {
                    val intent= Intent(this, AudiobooksActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_settings -> {
                    val intent= Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    true
                }
                R.id.nav_help -> {
                    val intent= Intent(this, HelpActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        val toggle = ActionBarDrawerToggle(this, DrawerLayout, R.string.open, R.string.close)
        DrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /*private fun replaceFragment(fragment: Fragment,title: String){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FrameLayout,fragment)
        fragmentTransaction.commit()
        DrawerLayout.closeDrawers()
        setTitle(title)


    }*/


    override fun onBackPressed(){
        when {
            //If drawer layout is open close that on back pressed
            DrawerLayout.isDrawerOpen(GravityCompat.START)->{
                DrawerLayout.closeDrawer(GravityCompat.START)
            }
            else->{
                //If drawer is already in closed condition then go back
                super.onBackPressed()
            }}
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        return when(item.itemId){
            android.R.id.home ->{
                DrawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}