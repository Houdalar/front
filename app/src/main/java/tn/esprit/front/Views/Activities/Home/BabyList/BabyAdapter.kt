package tn.esprit.front.Views.Activities.Home.BabyList

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.esprit.front.R
import tn.esprit.front.Views.Activities.Profile.ProfileActivity
import tn.esprit.front.Views.Activities.Signin.PREF_NAME
import tn.esprit.front.Views.Activities.Signin.TOKEN
import tn.esprit.front.models.Baby
import tn.esprit.front.viewmodels.ApiInterface

class BabyAdapter(val babyList: MutableList<Baby>):RecyclerView.Adapter<BabyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BabyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.baby_item, parent, false)

        return BabyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BabyViewHolder, position: Int) {

        val babyName = babyList[position].babyName
        val pic=babyList[position].babyPic
        Picasso.with(holder.itemView.context).load(pic).into(holder.babyPic)
        holder.babyName.text = babyName
        //holder.babyPic.setImageResource(babyList[position].babyPic!!)
        var mSharedPreferences: SharedPreferences =
            holder.itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, ProfileActivity::class.java)

            intent.apply {
                putExtra("BABYNAME", babyList[position].babyName)
                putExtra("DATE",babyList[position].birthday)
                putExtra("BABYPIC",pic)
                putExtra("token",mSharedPreferences.getString(TOKEN,"").toString())
                Log.e("******************************************* Token *************************************",mSharedPreferences.getString(TOKEN,"").toString())
            }
            holder.itemView.context.startActivity(intent)

        }
    }
    override fun getItemCount() = babyList.size

}

