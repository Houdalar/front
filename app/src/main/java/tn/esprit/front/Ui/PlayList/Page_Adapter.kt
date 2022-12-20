package tn.esprit.front.Ui.PlayList

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class Page_Adapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 ->
            {
                return Categories()
            }
            1 ->
            {
                return Playlist()
            }
            else ->
            {
                return Categories()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Categories"
            }
            1 -> {
                return "playlist"
            }
        }
        return super.getPageTitle(position)
    }

}
