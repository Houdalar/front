package tn.esprit.front.Views.Fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import tn.esprit.front.R


class SplashScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if (mainViewPagerFinished()){
                findNavController().navigate(R.id.action_splashScreenFragment_to_login_Activity)
            }
            else{
                findNavController().navigate(R.id.action_splashScreenFragment_to_viewPagerFragment)
            }

        },3500)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    private fun mainViewPagerFinished():Boolean{
        val sharedPref=requireActivity().getSharedPreferences("mainViewPager",Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }


}