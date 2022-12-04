package tn.esprit.front.Views.Activities.Height

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import tn.esprit.front.R
import tn.esprit.front.models.Height
import tn.esprit.front.viewmodels.ApiInterface


lateinit var txtHeight: TextView
lateinit var txtDate: TextView

lateinit var heightRecyclerView:RecyclerView
lateinit var heightAdapter: HeightAdapter
lateinit var heightList: MutableList<Height>

lateinit var addBtn: FloatingActionButton


class HeightActivity : AppCompatActivity() {

   /* var services = ApiInterface.create()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_height)
        supportActionBar?.setTitle("Height")

        heightList=services.

        addBtn.setOnClickListener{
            openDialog();
        }

    }

    private fun openDialog() {

    }*/
}