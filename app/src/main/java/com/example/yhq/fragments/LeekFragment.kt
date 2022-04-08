package com.example.yhq.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.yhq.R
import com.example.yhq.adapter.RvAdapterLeek
import com.example.yhq.databinding.FragmentLeekBinding
import com.example.yhq.db.MyDbHalper
import com.example.yolharakatiqoidalari.models.Znak
import com.google.android.material.bottomnavigation.BottomNavigationView

class LeekFragment : Fragment() {

    lateinit var fragmentLeekBinding: FragmentLeekBinding
    lateinit var root: View
    lateinit var myDbHalper: MyDbHalper
    lateinit var rvAdapterLeek: RvAdapterLeek
    lateinit var listLeek: ArrayList<Znak>
    lateinit var listLeek1: ArrayList<Znak>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLeekBinding = FragmentLeekBinding.inflate(inflater, container, false)
        root = fragmentLeekBinding.root
        myDbHalper = MyDbHalper(root.context)
        listLeek1 = myDbHalper.getAllZnak()
        var list = ArrayList<Znak>()
        for (i in listLeek1) {
            if (i.znak_leek_backGraund == R.drawable.ic_heart_1) {
                list.add(i)
            }
        }
        rvAdapterLeek = RvAdapterLeek(list, object : RvAdapterLeek.OnItemClick {
            override fun onItemClickLeek(znakLeek: Znak, position: Int) {

            }

            override fun onItemClicDelete(znakLeek: Znak, position: Int) {
                list.remove(znakLeek)
                rvAdapterLeek.notifyItemRemoved(position)
                rvAdapterLeek.notifyItemRangeChanged(position, list.size)
                znakLeek.znak_leek_backGraund = R.drawable.ic_heart__1__1
                myDbHalper.editeZnak(znakLeek)
            }

            override fun onItemEditeZnak(znakLeek: Znak, position: Int) {
                var bundle = Bundle()
                bundle.putInt("position", position)
                bundle.putSerializable("znak", znakLeek)
                findNavController().navigate(
                    R.id.editeLeekFragment,
                    bundle,
                )
            }

            override fun onItemClickOp(znakLeek: Znak, position: Int) {
                var bundle = Bundle()
                bundle.putInt("position", position)
                bundle.putSerializable("znak", znakLeek)

                findNavController().navigate(
                    R.id.infoZnakragment,
                    bundle
                )
            }

        })
        fragmentLeekBinding.rvLeek.adapter = rvAdapterLeek

        var calback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId =
                    R.id.home1
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, calback)

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.VISIBLE

    }
}