package tn.esprit.front.Ui.PlayList

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class Page_Adapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return Playlist()
            }
            1 -> {
                return fav()
            }
            else -> {
                return Playlist()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Categories"
            }
            1 -> {
                return "favorites"
            }
        }
        return super.getPageTitle(position)
    }

}
