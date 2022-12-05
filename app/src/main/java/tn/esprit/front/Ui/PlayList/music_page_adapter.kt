package tn.esprit.front.Ui.PlayList

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class musicpageadapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return Topmusic()
            }
            1 -> {
                return Topmusic()
            }
            else -> {
                return Topmusic()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Top"
            }
            1 -> {
                return "new"
            }
        }
        return super.getPageTitle(position)
    }

}