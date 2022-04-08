package com.example.yhq.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yhq.R
import com.example.yhq.databinding.FragmentOpenBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class OpenFragment : Fragment() {

    lateinit var fragmentOpenBinding: FragmentOpenBinding
    lateinit var root: View
    lateinit var handler: Handler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentOpenBinding = FragmentOpenBinding.inflate(inflater, container, false)
        root = fragmentOpenBinding.root

        handler = Handler()
        handler.postDelayed({
            val bundle = Bundle()

            findNavController().navigate(R.id.menuFragment)
        }, 1000)
        return root
    }

    override fun onResume() {
        super.onResume()
        (activity)!!.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
        findNavController().popBackStack()
    }

}