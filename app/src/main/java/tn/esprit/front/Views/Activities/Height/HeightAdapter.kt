package tn.esprit.front.Views.Activities.Height

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.models.Baby
import tn.esprit.front.models.Height

class HeightAdapter(val heightList: MutableList<Height>) : RecyclerView.Adapter<HeightAdapter.HeightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeightViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.add_height_item,parent,false)
        return HeightViewHolder(view)
    }


    override fun onBindViewHolder(holder: HeightViewHolder, position: Int) {
        val hei=heightList[position]

        holder.height.text=hei.height.toString()
        holder.date.text=hei.date
    }


    override fun getItemCount(): Int = heightList.size


    class HeightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val height=itemView.findViewById<TextView>(R.id.height)
        val date=itemView.findViewById<TextView>(R.id.dateHeight)
    }


}