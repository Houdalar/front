package tn.esprit.front.Views.Activities.AudioBooks

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class bookviewpager (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 ->
            {
                return AudioBook()
            }
            1 ->
            {
                return bookshelf()
            }
            else ->
            {
                return AudioBook()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position) {
            0 -> {
                return "Home"
            }
            1 -> {
                return "BookShelf"
            }
        }
        return super.getPageTitle(position)
    }

}
