package tn.esprit.front.Views.Activities.Height

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.models.Baby
import tn.esprit.front.models.Height
import tn.esprit.front.models.Weight

class WeightAdapter(val weightList: MutableList<Weight>) : RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.add_weight_item,parent,false)
        return WeightViewHolder(view)
    }


    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        val wei= weightList.get(position)
        holder.weight.text=wei.weight.toString()
        holder.date.text=wei.date
    }


    override fun getItemCount(): Int =  weightList.size




    class WeightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val weight=itemView.findViewById<TextView>(R.id.weight)
        val date=itemView.findViewById<TextView>(R.id.dateWeight)


    }

}