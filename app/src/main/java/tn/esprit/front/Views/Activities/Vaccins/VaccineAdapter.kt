package tn.esprit.front.Views.Activities.Vaccins

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.Views.Activities.Height.HeightAdapter
import tn.esprit.front.models.Height
import tn.esprit.front.models.Vaccine

class VaccineAdapter(val vaccineList: MutableList<Vaccine>) : RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.vaccin_item,parent,false)
        return VaccineViewHolder(view)
    }


    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        val vacc= vaccineList.get(position)
        holder.vaccine.text=vacc.vaccine.toString()
        holder.date.text=vacc.date
    }


    override fun getItemCount(): Int =  vaccineList.size




    class VaccineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val vaccine=itemView.findViewById<TextView>(R.id.vaccine)
        val date=itemView.findViewById<TextView>(R.id.dateVaccine)


    }
}