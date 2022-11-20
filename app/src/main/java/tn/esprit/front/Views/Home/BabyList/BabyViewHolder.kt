package tn.esprit.front.Views.Home.BabyList

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R

class BabyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
    val babyPic:ImageView
    val babyName:TextView=itemView.findViewById(R.id.itemName)

    init {
        babyPic=itemView.findViewById(R.id.itemPic)
    }
}