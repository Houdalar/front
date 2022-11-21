package tn.esprit.front.Views.Home.BabyList

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.Views.ProfileActivity
import tn.esprit.front.models.Baby
import tn.esprit.front.models.PICTURE


class BabyAdapter(val babyList: MutableList<Baby>):RecyclerView.Adapter<BabyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BabyViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.baby_item,parent,false)

        return BabyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BabyViewHolder, position: Int) {
        val name=babyList[position].name
//        val pic=babyList[position].babyPic
        holder.babyName.text=name
        holder.babyPic.setImageResource(babyList[position].babyPic!!)

        holder.itemView.setOnClickListener{
            val intent=Intent(holder.itemView.context,ProfileActivity::class.java)
            intent.apply {
                putExtra(PICTURE,babyList[position].babyPic)
                putExtra(tn.esprit.front.models.NAME,name)
            }
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount()=babyList.size
}