package tn.esprit.front.Views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.front.*
import kotlinx.android.synthetic.main.home.*
import tn.esprit.front.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        nav_view.setNavigationItemSelectedListener {
            it.isChecked=true

            when (it.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment(),it.title.toString())
                }
                R.id.nav_babies -> {
                    replaceFragment(BabiesFragment(),it.title.toString())
                }
                R.id.nav_music -> {
                    replaceFragment(MusicFragment(),it.title.toString())
                }
                R.id.nav_Audiobooks -> {
                    replaceFragment(AudiobooksFragment(),it.title.toString())
                }
                R.id.nav_settings -> {
                    replaceFragment(SettingsFragment(),it.title.toString())
                }
                R.id.nav_logout -> {
                    true
                }
                R.id.nav_help -> {
                    replaceFragment(HelpFragment(),it.title.toString())
                }
            }
            true
        }

        val toggle = ActionBarDrawerToggle(this, DrawerLayout, R.string.open, R.string.close)
        DrawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun replaceFragment(fragment: Fragment,title: String){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FrameLayout,fragment)
        fragmentTransaction.commit()
        DrawerLayout.closeDrawers()
        setTitle(title)


    }


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