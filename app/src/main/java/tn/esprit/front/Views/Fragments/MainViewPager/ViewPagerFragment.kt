package tn.esprit.front.Views.Fragments.MainViewPager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view_pager.view.*
import tn.esprit.front.R
import tn.esprit.front.Views.Fragments.MainViewPager.Screens.FirstScreenFragment
import tn.esprit.front.Views.Fragments.MainViewPager.Screens.SecondFragment
import tn.esprit.front.Views.Fragments.MainViewPager.Screens.ThirdFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ViewPagerFragment : Fragment() {

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

        val adapter=MainViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        view.mainViewPager.adapter=adapter



        return view

    }

}