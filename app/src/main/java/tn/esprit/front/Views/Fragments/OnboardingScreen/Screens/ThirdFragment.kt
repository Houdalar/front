package tn.esprit.front.Views.Fragments.OnboardingScreen.Screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_third.view.*
import tn.esprit.front.R




class ThirdFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_third, container, false)

        view.finish.setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_login_Activity)
            mainViewPagerFinished()

        }


        return view
    }

    private fun mainViewPagerFinished(){
        val sharedPref = requireActivity().getSharedPreferences("mainViewPager", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

}