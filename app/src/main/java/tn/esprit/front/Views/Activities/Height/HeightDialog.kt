package tn.esprit.front.Views.Activities.Height


import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.front.Views.Activities.Signin.PREF_NAME
import tn.esprit.front.Views.Activities.Signin.TOKEN
import tn.esprit.front.databinding.HeightDialogBinding
import tn.esprit.front.models.Height
import tn.esprit.front.viewmodels.ApiInterface
import tn.esprit.front.viewmodels.BabyAPIInterface


class HeightDialog(
    val onSubmitClickListener: (String) -> Unit,
    val babyName: String,
    val token: String
):DialogFragment() {
    private lateinit var binding : HeightDialogBinding

    val services = BabyAPIInterface.create()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = HeightDialogBinding.inflate(LayoutInflater.from(context))
        Log.e("******************************************************************** Height Dialog ************************",babyName.toString())
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        binding.saveBtn.setOnClickListener {
            onSubmitClickListener.invoke(binding.babyHeightTxt.text.toString())
            val map: HashMap<String, String> = HashMap()

            map["token"] = token
            map["height"]=binding.babyHeightTxt.text.toString()
            map["babyName"]= babyName
            Log.e("token",token)
            Log.e("height",binding.babyHeightTxt.text.toString())
            Log.e("babyName",babyName)
            services.addHeight(map).enqueue(object: Callback<Height> {
                override fun onResponse(call: Call<Height>, response: Response<Height>) {
                    if(response.isSuccessful){
                        println(response.message())
                        dismiss()
                    }
                    else{
                        Toast.makeText(context, "Baby not found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Height>, t: Throwable) {
                    Toast.makeText(context, "Failed to add height", Toast.LENGTH_SHORT).show()
                }

            })

        }

        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }
}