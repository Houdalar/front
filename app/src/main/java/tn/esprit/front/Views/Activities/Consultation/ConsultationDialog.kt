package tn.esprit.front.Views.Activities.Vaccins

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.databinding.ConsultationDialogBinding
import tn.esprit.front.models.Consultation
import tn.esprit.front.viewmodels.BabyAPIInterface
import java.text.SimpleDateFormat

class ConsultationDialog(
    val onSubmitClickListener: (String) -> Unit,
    val babyName: String,
    val token: String
) : DialogFragment() {
    private lateinit var binding: ConsultationDialogBinding

    val services = BabyAPIInterface.create()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ConsultationDialogBinding.inflate(LayoutInflater.from(context))
        Log.e(
            "******************************************************************** consultation Dialog ************************",
            babyName.toString()
        )
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        //Date Picker
        val ConsultationDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select consultation Date")
            .build()

        ConsultationDatePicker.addOnPositiveButtonClickListener {
            binding.ConsultationDayTxt.setText(ConsultationDatePicker.headerText.toString())
        }

        binding.ConsultationDayTxt.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                ConsultationDatePicker.show(parentFragmentManager, "CONSULTATION_DATE")
            } else {
                ConsultationDatePicker.dismiss()
            }
        }

        val ConsultationTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(CLOCK_24H)
            .setHour(12)
            .setMinute(10)
            .setTitleText("Select consultation Time")
            .build()

        ConsultationTimePicker.addOnPositiveButtonClickListener {
            binding.ConsultationTimeTxt.setText(ConsultationTimePicker.hour.toString() + ":" + ConsultationTimePicker.minute.toString())
        }

        binding.ConsultationTimeTxt.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                ConsultationTimePicker.show(parentFragmentManager, "CONSULTATION_TIME")
            } else {
                ConsultationTimePicker.dismiss()
            }
        }


        binding.saveBtnC.setOnClickListener {
            onSubmitClickListener.invoke(binding.DoctorNameTxt.text.toString())
            val map: HashMap<String, String> = HashMap()

            map["token"] = token
            map["doctor"] = binding.DoctorNameTxt.text.toString()
            map["date"] = binding.ConsultationDayTxt.text.toString()
            map["time"] = binding.ConsultationTimeTxt.text.toString()
            map["babyName"] = babyName
            Log.e("token", token)
            Log.e("vaccine", binding.DoctorNameTxt.text.toString())
            Log.e("date", binding.ConsultationDayTxt.text.toString())
            Log.e("time", binding.ConsultationTimeTxt.text.toString())
            Log.e("babyName", babyName)
            services.addConsultation(map).enqueue(object : Callback<Consultation> {
                override fun onResponse(
                    call: Call<Consultation>,
                    response: Response<Consultation>
                ) {
                    if (response.isSuccessful) {
                        println(response.message())
                        dismiss()
                    } else {
                        Toast.makeText(context, "Baby not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Consultation>, t: Throwable) {
                    Toast.makeText(context, "Failed to add consultation", Toast.LENGTH_SHORT).show()
                }

            })

        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}