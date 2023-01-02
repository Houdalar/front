package tn.esprit.front.Views.Fragments.OnboardingScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view_pager.view.*
import tn.esprit.front.R
import tn.esprit.front.Views.Fragments.OnboardingScreen.Screens.FirstScreenFragment
import tn.esprit.front.Views.Fragments.OnboardingScreen.Screens.SecondFragment
import tn.esprit.front.Views.Fragments.OnboardingScreen.Screens.ThirdFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OnBoardingScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList= arrayListOf<Fragment>(
            FirstScreenFragment(),
            SecondFragment(),
            ThirdFragment()

        )

        val adapter=OnBoardingScreenAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.mainViewPager.adapter=adapter



        return view

    }

}