package com.doing.androidx.postview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.doing.androidx.R

class PostViewPagerAdapter(manager: FragmentManager,  behavior: Int)
: FragmentPagerAdapter(manager, behavior) {
    override fun getCount(): Int {
        return 4
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun getItem(position: Int): Fragment {
        return PostViewFragment()
    }

    class ViewPagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return 10
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(container.context).inflate(R.layout.fragment_post_view,
                container, false)

            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getItemPosition(`object`: Any): Int {
            return POSITION_UNCHANGED
        }
    }
}