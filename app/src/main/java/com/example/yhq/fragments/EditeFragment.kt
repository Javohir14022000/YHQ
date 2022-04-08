package com.example.yhq.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yhq.R
import com.example.yhq.adapter.SpinnerAdapter
import com.example.yhq.databinding.FragmentEditeBinding
import com.example.yhq.databinding.ItemBottomsheetBinding
import com.example.yhq.db.MyDbHalper
import com.example.yolharakatiqoidalari.models.Znak
import com.github.florent37.runtimepermission.BuildConfig
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditeFragment : Fragment() {


    lateinit var fragmentEditeBinding: FragmentEditeBinding
    lateinit var root:View
    lateinit var myDbHalper: MyDbHalper
    var filAbsolutePath:String?=null
    var photoURI:Uri?=null
    lateinit var listZnak:ArrayList<Znak>
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var listCategory:ArrayList<String>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditeBinding = FragmentEditeBinding.inflate(inflater,container,false)
        root = fragmentEditeBinding.root
        myDbHalper = MyDbHalper(root.context)
        (activity)!!.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        val znak = arguments?.getSerializable("znak1") as Znak
        listZnak = myDbHalper.getAllZnak()
        fragmentEditeBinding.nameZnak.text = znak.znak_name
        fragmentEditeBinding.name.setText(znak.znak_name)
        fragmentEditeBinding.info.setText(znak.znak_info)
        fragmentEditeBinding.image.setImageURI(Uri.parse(znak.znak_image))
        filAbsolutePath = znak.znak_image!!

        loadCateGory()
        spinnerAdapter = SpinnerAdapter(listCategory)
        fragmentEditeBinding.spinnerCategory.adapter=spinnerAdapter
        fragmentEditeBinding.spinnerCategory.setSelection(listCategory.indexOf(znak.znak_category))

        fragmentEditeBinding.image.setOnClickListener {
        //    clearImages()
            var bottomSheetDialog = BottomSheetDialog(root.context)
            var itemBottomSheetDialog = ItemBottomsheetBinding.inflate(LayoutInflater.from(root.context), null, false)
            itemBottomSheetDialog.camera.setOnClickListener {
                var imageFile = createImageFile()
                photoURI= FileProvider.getUriForFile(root.context, BuildConfig.APPLICATION_ID,imageFile)
                getTakeImageContent.launch(photoURI)
                bottomSheetDialog.hide()
            }


            itemBottomSheetDialog.galereya.setOnClickListener {
                picImageForNewGallery()
                bottomSheetDialog.hide()
            }
            bottomSheetDialog.setContentView(itemBottomSheetDialog.root)
            bottomSheetDialog.show()
        }

        fragmentEditeBinding.saveBtn.setOnClickListener {
            val category_positio  = fragmentEditeBinding.spinnerCategory.selectedItemPosition
            znak.znak_name = fragmentEditeBinding.name.text.toString()
            znak.znak_info = fragmentEditeBinding.info.text.toString()
            znak.znak_category = listCategory[category_positio]
            znak.znak_category_position = category_positio
            znak.znak_image = filAbsolutePath
            if (znak.znak_name.isNullOrBlank()){
                Toast.makeText(root.context, "Iltomos ismingizni kiriting", Toast.LENGTH_SHORT).show()
            }else if (znak.znak_info.isNullOrBlank()){
                Toast.makeText(root.context, "Iltomos infoni kiriting", Toast.LENGTH_SHORT).show()
            }else if (znak.znak_info!!.isNotBlank() && znak.znak_name!!.isNotBlank()){
                myDbHalper.editeZnak(znak)
                findNavController().popBackStack()
            }

        }

        fragmentEditeBinding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        return root
    }

    //camera

    private val getTakeImageContent = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            fragmentEditeBinding.image.setImageURI(photoURI)
            var openInputStream = activity?.contentResolver?.openInputStream(photoURI!!)
            var format = SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(Date())
            var file = File(activity?.filesDir, "$format.jpg")
            var fileoutputStream = FileOutputStream(file)
            openInputStream!!.copyTo(fileoutputStream)
            openInputStream.close()
            fileoutputStream.close()
            filAbsolutePath = file.absolutePath
        }
    }


    @Throws(IOException::class)
    private fun createImageFile(): File {
        val date = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_$date",".jpg",externalFilesDir).apply {
            absolutePath
        }
    }



    // gallery

    private fun picImageForNewGallery() {
        getImageContent.launch("image/*")
    }

    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()){ uri->
        uri?:return@registerForActivityResult
        fragmentEditeBinding.image.setImageURI(uri)
        var openInputStream =(activity)?.contentResolver?.openInputStream(uri)
        var filesDir = (activity)?.filesDir
        var format = SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(Date())
        var file = File(filesDir,"$format.jpg")
        val fileOutputStream = FileOutputStream(file)
        openInputStream!!.copyTo(fileOutputStream)
        openInputStream.close()
        fileOutputStream.close()
        filAbsolutePath = file.absolutePath
//        var fileInputStream = FileInputStream(file)
//        val readBytes = fileInputStream.readBytes()
    }
    private fun loadCateGory() {
            listCategory = ArrayList()
            listCategory.add("Ogohlantiruvchi")
            listCategory.add("Imtiyozli")
            listCategory.add("Ta'qiqlovchi")
            listCategory.add("Buyuruvchi")
    }
}