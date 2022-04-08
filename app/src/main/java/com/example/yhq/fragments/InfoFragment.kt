package com.example.yhq.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.yhq.R
import com.example.yhq.databinding.FragmentInfoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class InfoFragment : Fragment() {

    lateinit var fragmentInfoBinding: FragmentInfoBinding
    lateinit var root:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentInfoBinding = FragmentInfoBinding.inflate(inflater,container,false)
        root = fragmentInfoBinding.root
        var calback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.home1
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,calback)
        return root
    }
}