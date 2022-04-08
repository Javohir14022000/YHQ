package com.example.yhq.fragments

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.yhq.R
import com.example.yhq.adapter.SpinnerAdapter
import com.example.yhq.databinding.FragmentEditeLeekBinding
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

class EditeLeekFragment : Fragment() {


    lateinit var fragmentEditeLeekBinding: FragmentEditeLeekBinding
    lateinit var root:View
    lateinit var myDbHalper: MyDbHalper
    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var listCategory:ArrayList<String>
    lateinit var filAbsolutePath:String
    lateinit var photoURI:Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditeLeekBinding = FragmentEditeLeekBinding.inflate(inflater,container,false)
        root = fragmentEditeLeekBinding.root
        myDbHalper = MyDbHalper(root.context)
        (activity as AppCompatActivity).findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        var leek_znak = arguments?.getSerializable("znak") as Znak
        fragmentEditeLeekBinding.image.setImageURI(Uri.parse(leek_znak.znak_image))
        fragmentEditeLeekBinding.nameZnak.text = leek_znak.znak_name
        fragmentEditeLeekBinding.name.setText(leek_znak.znak_name)
        fragmentEditeLeekBinding.info.setText(leek_znak.znak_info)
        filAbsolutePath = leek_znak.znak_image!!
        loadCateGory()
        spinnerAdapter = SpinnerAdapter(listCategory)
        fragmentEditeLeekBinding.spinnerCategory.setSelection(listCategory.indexOf(leek_znak.znak_category))
        fragmentEditeLeekBinding.spinnerCategory.adapter=spinnerAdapter

        fragmentEditeLeekBinding.image.setOnClickListener {
            var bottomSheetDialog = BottomSheetDialog(root.context)
            var itemBottomSheetDialog = ItemBottomsheetBinding.inflate(LayoutInflater.from(root.context), null, false)
            itemBottomSheetDialog.camera.setOnClickListener {
                var imageFile = createImageFile()
                photoURI= FileProvider.getUriForFile(root.context,
                    BuildConfig.APPLICATION_ID,imageFile)
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

        fragmentEditeLeekBinding.saveBtn.setOnClickListener {
            val category_positio  = fragmentEditeLeekBinding.spinnerCategory.selectedItemPosition
            leek_znak.znak_name = fragmentEditeLeekBinding.name.text.toString()
            leek_znak.znak_info = fragmentEditeLeekBinding.info.text.toString()
            leek_znak.znak_category = listCategory[category_positio]
            leek_znak.znak_category_position = category_positio
            leek_znak.znak_image = filAbsolutePath
            if (leek_znak.znak_name.isNullOrBlank()){
                Toast.makeText(root.context, "Iltomos ismingizni kiriting", Toast.LENGTH_SHORT).show()
            }else if (leek_znak.znak_info.isNullOrBlank()){
                Toast.makeText(root.context, "Iltomos infoni kiriting", Toast.LENGTH_SHORT).show()
            }else if (leek_znak.znak_info!!.isNotBlank() && leek_znak.znak_name!!.isNotBlank()){
                myDbHalper.editeZnak(leek_znak)
                findNavController().popBackStack()
            }

        }

        fragmentEditeLeekBinding.closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        return root
    }

    // camera
    private val getTakeImageContent = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            fragmentEditeLeekBinding.image.setImageURI(photoURI)
            var openInputStream = activity?.contentResolver?.openInputStream(photoURI)
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
        fragmentEditeLeekBinding.image.setImageURI(uri)
        var openInputStream =(activity as AppCompatActivity).contentResolver.openInputStream(uri)
        var filesDir = (activity as AppCompatActivity).filesDir
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