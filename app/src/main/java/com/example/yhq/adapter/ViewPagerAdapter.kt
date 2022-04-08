package com.example.yhq.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yhq.fragments.ZnakFragment
import com.example.yolharakatiqoidalari.models.Category

class ViewPagerAdapter(var list: List<Category>, fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {
    var fragments:ArrayList<ZnakFragment> = ArrayList()
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment =  ZnakFragment.newInstance(list[position].category_position!!, list[position].category_name!!)
        fragments.add(fragment)
        return fragment
    }
}