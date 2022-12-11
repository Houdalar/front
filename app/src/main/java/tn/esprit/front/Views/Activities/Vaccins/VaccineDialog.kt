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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.databinding.HeightDialogBinding
import tn.esprit.front.databinding.VaccinItemBinding
import tn.esprit.front.databinding.VaccineDialogBinding
import tn.esprit.front.models.Height
import tn.esprit.front.models.Vaccine
import tn.esprit.front.viewmodels.ApiInterface
import tn.esprit.front.viewmodels.BabyAPIInterface
import java.text.SimpleDateFormat

class VaccineDialog(
    val onSubmitClickListener: (String) -> Unit,
    val babyName: String,
    val token: String
): DialogFragment() {
    private lateinit var binding : VaccineDialogBinding

    val services = BabyAPIInterface.create()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = VaccineDialogBinding.inflate(LayoutInflater.from(context))
        Log.e("******************************************************************** vaccine Dialog ************************",babyName.toString())
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)


        val VaccineDatePicker=MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select Vaccine Date")
            .build()

        VaccineDatePicker.addOnPositiveButtonClickListener {
            binding.VaccineDateTxt.setText(VaccineDatePicker.headerText.toString())
        }

        binding.VaccineDateTxt.setOnFocusChangeListener  {view, hasFocus ->
            if (hasFocus){
                VaccineDatePicker.show(parentFragmentManager,"VACCINE_DATE")
            }else{
                VaccineDatePicker.dismiss()
            }        }

        binding.saveBtnV.setOnClickListener {
            onSubmitClickListener.invoke(binding.babyVaccineTxt.text.toString())
            val map: HashMap<String, String> = HashMap()

            map["token"] = token
            map["vaccine"]=binding.babyVaccineTxt.text.toString()
            map["date"] =binding.VaccineDateTxt.text.toString()
            map["babyName"]= babyName
            Log.e("token",token)
            Log.e("vaccine",binding.babyVaccineTxt.text.toString())
            Log.e("date",binding.VaccineDateTxt.text.toString())
            Log.e("babyName",babyName)
            services.addVaccine(map).enqueue(object: Callback<Vaccine> {
                override fun onResponse(call: Call<Vaccine>, response: Response<Vaccine>) {
                    if(response.isSuccessful){
                        println(response.message())
                        dismiss()
                    }
                    else{
                        Toast.makeText(context, "Baby not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Vaccine>, t: Throwable) {
                    Toast.makeText(context, "Failed to add vaccine", Toast.LENGTH_SHORT).show()
                }

            })

        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}