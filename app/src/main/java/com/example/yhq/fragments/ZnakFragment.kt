package com.example.yhq.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.yhq.R
import com.example.yhq.adapter.RvAdapter
import com.example.yhq.databinding.FragmentZnakBinding
import com.example.yhq.db.MyDbHalper
import com.example.yolharakatiqoidalari.models.Znak

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ZnakFragment : Fragment() {

    private var param1: Int? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var fragmentZnakBinding: FragmentZnakBinding
    lateinit var root: View
    lateinit var myDbHalper: MyDbHalper
    lateinit var listZnak: ArrayList<Znak>
    lateinit var rvAdapter: RvAdapter
    var color: Int = R.drawable.ic_heart_1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentZnakBinding = FragmentZnakBinding.inflate(inflater, container, false)
        root = fragmentZnakBinding.root
        myDbHalper = MyDbHalper(root.context)
        listZnak = myDbHalper.getAllZnak()
        var listZnaks = ArrayList<Znak>()
        for (i in listZnak) {
            if (i.znak_category_position == param1 && i.znak_category.equals(
                    param2,
                    ignoreCase = true
                )
            ) {
                listZnaks.add(i)
            }
        }
        return root
    }


    override fun onResume() {
        super.onResume()
        listZnak = myDbHalper.getAllZnak()
        var listZnaks = ArrayList<Znak>()
        for (i in listZnak) {
            if (i.znak_category_position == param1 && i.znak_category.equals(
                    param2,
                    ignoreCase = true
                )
            ) {
                listZnaks.add(i)
            }
        }

        when (param1) {
            0 -> {
                rvAdapter = RvAdapter(root.context, listZnaks, object : RvAdapter.OnItemClick {
                    override fun onItemClickLeek(znak: Znak, position: Int) {
                        if (znak.znak_leek_backGraund == R.drawable.ic_heart__1__1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart_1
                            myDbHalper.editeZnak(znak)
                            rvAdapter.notifyDataSetChanged()
                        } else if (znak.znak_leek_backGraund == R.drawable.ic_heart_1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart__1__1
                            myDbHalper.editeZnak(znak)

                            // myDbHalper.deleteZnakLeek(znak)
                            rvAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onItemClicDelete(znak: Znak, position: Int) {
                        myDbHalper.deleteZnak(znak)
                        listZnaks.remove(znak)
                        rvAdapter.notifyItemRemoved(position)
                        rvAdapter.notifyItemRangeChanged(position, listZnaks.size)
                        // clearImages()
                    }


                    override fun onItemEditeZnak(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putInt("category", param1!!)
                        bundle.putSerializable("znak1", znak)

                        findNavController().navigate(R.id.editeFragment, bundle)
                    }

                    override fun onItemClickOp(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putSerializable("znak", znak)


                        findNavController().navigate(
                            R.id.infoZnakragment,
                            bundle,
                        )
                    }
                }, color)
                fragmentZnakBinding.rv.adapter = rvAdapter
            }
            1 -> {
                rvAdapter = RvAdapter(root.context, listZnaks, object : RvAdapter.OnItemClick {
                    override fun onItemClickLeek(znak: Znak, position: Int) {
                        if (znak.znak_leek_backGraund == R.drawable.ic_heart__1__1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart_1
                            myDbHalper.editeZnak(znak)
                            rvAdapter.notifyDataSetChanged()
                        } else if (znak.znak_leek_backGraund == R.drawable.ic_heart_1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart__1__1
                            myDbHalper.editeZnak(znak)

                            // myDbHalper.deleteZnakLeek(znak)
                            rvAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onItemClicDelete(znak: Znak, position: Int) {
                        myDbHalper.deleteZnak(znak)
                        listZnaks.remove(znak)
                        rvAdapter.notifyItemRemoved(position)
                        rvAdapter.notifyItemRangeChanged(position, listZnaks.size)
                        //  clearImages()
                    }

                    override fun onItemEditeZnak(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putInt("category", param1!!)
                        bundle.putSerializable("znak1", znak)

                        findNavController().navigate(R.id.editeFragment, bundle)
                    }

                    override fun onItemClickOp(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putSerializable("znak", znak)

                        findNavController().navigate(
                            R.id.infoZnakragment,
                            bundle
                        )
                    }
                }, color)
                fragmentZnakBinding.rv.adapter = rvAdapter
            }
            2 -> {
                rvAdapter = RvAdapter(root.context, listZnaks, object : RvAdapter.OnItemClick {
                    override fun onItemClickLeek(znak: Znak, position: Int) {
                        if (znak.znak_leek_backGraund == R.drawable.ic_heart__1__1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart_1
                            myDbHalper.editeZnak(znak)
                            rvAdapter.notifyDataSetChanged()
                        } else if (znak.znak_leek_backGraund == R.drawable.ic_heart_1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart__1__1
                            myDbHalper.editeZnak(znak)

                            // myDbHalper.deleteZnakLeek(znak)
                            rvAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onItemClicDelete(znak: Znak, position: Int) {
                        myDbHalper.deleteZnak(znak)
                        listZnaks.remove(znak)
                        rvAdapter.notifyItemRemoved(position)
                        rvAdapter.notifyItemRangeChanged(position, listZnaks.size)
                        //clearImages()
                    }

                    override fun onItemEditeZnak(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putInt("category", param1!!)
                        bundle.putSerializable("znak1", znak)

                        findNavController().navigate(R.id.editeFragment, bundle)
                    }

                    override fun onItemClickOp(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putSerializable("znak", znak)

                        findNavController().navigate(
                            R.id.infoZnakragment,
                            bundle
                        )
                    }
                }, color)
                fragmentZnakBinding.rv.adapter = rvAdapter
            }
            3 -> {
                rvAdapter = RvAdapter(root.context, listZnaks, object : RvAdapter.OnItemClick {
                    override fun onItemClickLeek(znak: Znak, position: Int) {
                        if (znak.znak_leek_backGraund == R.drawable.ic_heart__1__1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart_1
                            myDbHalper.editeZnak(znak)
                            rvAdapter.notifyDataSetChanged()
                        } else if (znak.znak_leek_backGraund == R.drawable.ic_heart_1) {
                            znak.znak_leek_backGraund = R.drawable.ic_heart__1__1
                            myDbHalper.editeZnak(znak)

                            // myDbHalper.deleteZnakLeek(znak)
                            rvAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onItemClicDelete(znak: Znak, position: Int) {
                        myDbHalper.deleteZnak(znak)
                        listZnaks.remove(znak)
                        rvAdapter.notifyItemRemoved(position)
                        rvAdapter.notifyItemRangeChanged(position, listZnaks.size)
                        //clearImages()
                    }

                    override fun onItemEditeZnak(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putInt("category", param1!!)
                        bundle.putSerializable("znak1", znak)

                        findNavController().navigate(R.id.editeFragment, bundle)
                    }

                    override fun onItemClickOp(znak: Znak, position: Int) {
                        var bundle = Bundle()
                        bundle.putInt("position", position)
                        bundle.putSerializable("znak", znak)

                        findNavController().navigate(
                            R.id.infoZnakragment,
                            bundle
                        )
                    }
                }, color)
                fragmentZnakBinding.rv.adapter = rvAdapter
            }
        }
    }


    fun notify(b: Boolean) {
        var listZnaks = ArrayList<Znak>()
        for (i in listZnak) {
            if (i.znak_category_position == param1 && i.znak_category.equals(
                    param2,
                    ignoreCase = true
                )
            ) {
                listZnaks.add(i)
            }
        }
        rvAdapter.list = listZnaks
        rvAdapter.notifyDataSetChanged()
    }

    private fun clearImages() {
        val filesDir = activity?.filesDir
        if (filesDir!!.isDirectory) {
            var listFiles = filesDir.listFiles()
            for (i in listFiles) {
                i.delete()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            ZnakFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}