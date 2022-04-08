package com.example.yhq.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yhq.R
import com.example.yhq.databinding.FragmentInfoZnakragmentBinding
import com.example.yhq.db.MyDbHalper
import com.example.yolharakatiqoidalari.models.Znak
import com.google.android.material.bottomnavigation.BottomNavigationView

class InfoZnakragment : Fragment() {

    lateinit var fragmentInfoZnakBinding: FragmentInfoZnakragmentBinding
    lateinit var root:View
    lateinit var myDbHalper: MyDbHalper
    lateinit var listZnak:ArrayList<Znak>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        fragmentInfoZnakBinding = FragmentInfoZnakragmentBinding.inflate(inflater,container,false)
        root = fragmentInfoZnakBinding.root
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        myDbHalper = MyDbHalper(root.context)
        var znak = arguments?.getSerializable("znak") as Znak

        fragmentInfoZnakBinding.imageZnak.setImageURI(Uri.parse(znak.znak_image))
        fragmentInfoZnakBinding.nameTv.text = znak.znak_name
        fragmentInfoZnakBinding.nameZnak.text = znak.znak_name
        fragmentInfoZnakBinding.info.text = znak.znak_info
        fragmentInfoZnakBinding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return root
    }

}