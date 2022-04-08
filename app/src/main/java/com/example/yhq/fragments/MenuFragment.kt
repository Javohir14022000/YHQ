package com.example.yhq.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.yhq.R
import com.example.yhq.adapter.ViewPagerAdapter
import com.example.yhq.databinding.FragmentMenuBinding
import com.example.yhq.databinding.TabItemBinding
import com.example.yolharakatiqoidalari.models.Category
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import android.R.string.no





class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val fragmentMenuBinding by viewBinding(FragmentMenuBinding::bind)

    lateinit var listCategory: ArrayList<Category>
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var photoURI: Uri
    private lateinit var mToolbar: Toolbar

    var bool: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity)!!.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE

        loadCategory()

        viewPagerAdapter = ViewPagerAdapter(listCategory, requireActivity())

        fragmentMenuBinding.viewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(
            fragmentMenuBinding.tabLayout,
            fragmentMenuBinding.viewPager2
        ) { tab, position ->
            tab.text = listCategory[position].category_name
        }.attach()

        statTab()

        var calback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as AppCompatActivity).finish()
            }
        }

        fragmentMenuBinding.tabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                var tabItemBinding = TabItemBinding.bind(customView!!)
                tabItemBinding.cons2.setBackgroundColor(Color.WHITE)
                tabItemBinding.nameCategory.setTextColor(Color.parseColor("#005CA1"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab!!.customView
                var tabItemBinding = TabItemBinding.bind(customView!!)
                tabItemBinding.cons2.setBackgroundColor(Color.parseColor("#005CA1"))
                tabItemBinding.nameCategory.setTextColor(Color.WHITE)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        fragmentMenuBinding.add.setOnClickListener{
            var bundle = Bundle()
            bool = true
            findNavController().navigate(R.id.addZnakFragment, bundle)
        }

    }

    override fun onResume() {
        super.onResume()
        if (bool) {
            viewPagerAdapter = ViewPagerAdapter(listCategory, requireActivity())
            fragmentMenuBinding.viewPager2.adapter = viewPagerAdapter
        } else if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
        }


    }

    override fun onStart() {
        super.onStart()
    }

    private fun statTab() {
        val tabCount = fragmentMenuBinding.tabLayout.tabCount
        for (i in 0 until tabCount) {
            var tabItemBinding =
                TabItemBinding.inflate(LayoutInflater.from(requireContext()), null, false)
            val tabAt = fragmentMenuBinding.tabLayout.getTabAt(i)
            tabAt!!.customView = tabItemBinding.root
            tabItemBinding.nameCategory.text = listCategory[i].category_name
            if (i == 0) {
                tabItemBinding.cons2.setBackgroundColor(Color.WHITE)
                tabItemBinding.nameCategory.setTextColor(Color.parseColor("#005CA1"))
            } else {
                tabItemBinding.cons2.setBackgroundColor(Color.parseColor("#005CA1"))
                tabItemBinding.nameCategory.setTextColor(Color.WHITE)

            }
        }
    }

    private fun loadCategory() {
        listCategory = ArrayList()
        listCategory.add(Category("Ogohlantiruvchi", 0))
        listCategory.add(Category("Imtiyozli", 1))
        listCategory.add(Category("Ta'qiqlovchi", 2))
        listCategory.add(Category("Buyuruvchi", 3))
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

}