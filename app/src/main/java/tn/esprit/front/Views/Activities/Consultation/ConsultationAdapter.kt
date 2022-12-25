package tn.esprit.front.Views.Activities.Vaccins

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.front.R
import tn.esprit.front.models.Consultation

class ConsultationAdapter(val consultationList: MutableList<Consultation>) : RecyclerView.Adapter<ConsultationAdapter.ConsultationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultationViewHolder {
        val view= LayoutInflater.from(parent.context)
            .inflate(R.layout.consultation_item,parent,false)
        return ConsultationViewHolder(view)
    }


    override fun onBindViewHolder(holder: ConsultationViewHolder, position: Int) {
        val cons= consultationList.get(position)
        holder.doctor.text=cons.doctor.toString()
        holder.date.text=cons.date
        holder.time.text=cons.time
    }

    override fun getItemCount(): Int =  consultationList.size

    class ConsultationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val doctor=itemView.findViewById<TextView>(R.id.doctorName)
        val date=itemView.findViewById<TextView>(R.id.dateConsultation)
        val time=itemView.findViewById<TextView>(R.id.timeConsultation)


    }
}