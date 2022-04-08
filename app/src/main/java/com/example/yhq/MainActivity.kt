package com.example.yhq

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.example.yhq.databinding.ActivityMainBinding
import com.github.florent37.runtimepermission.RuntimePermission.askPermission
import com.github.florent37.runtimepermission.kotlin.askPermission

class MainActivity : AppCompatActivity() {
    var bool: Boolean = false
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        var controller = findNavController(R.id.fragment)
        askPermission(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE) {
            bool = false
            activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener {
                var id = it.itemId
                when (id) {
                    R.id.home1 -> {
                        controller.navigate(R.id.menuFragment)
                    }
                    R.id.leek -> {
                        controller.navigate(R.id.leekFragment)
                    }
                    R.id.info -> {
                        controller.navigate(R.id.infoFragment)
                    }
                }
                true
            }
        }.onDeclined {
            if (it.hasDenied()) {
                AlertDialog.Builder(this).setMessage("Iltimos dostup bering yo`qsa sizga yordam bera olmayman")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, which ->
                        it.askAgain()
                    }.setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }.show()
            }
            if (it.hasForeverDenied()) {
                AlertDialog.Builder(this).setMessage("Iltimos dostup bering yo`qsa sizga yordam bera olmayman")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, which ->
                        bool = true
                        it.goToSettings()
                    }.show()
            }

        }



    }


    override fun onResume() {
        super.onResume()
        if (bool) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                var controller = findNavController(R.id.fragment)
                activityMainBinding.bottomNavigation.setOnNavigationItemSelectedListener {
                    var id = it.itemId
                    when (id) {
                        R.id.home1 -> {
                            controller.navigate(R.id.menuFragment)
                        }
                        R.id.leek -> {
                            controller.navigate(R.id.leekFragment)
                        }
                        R.id.info -> {
                            controller.navigate(R.id.infoFragment)
                        }
                    }
                    true
                }
            } else {
                AlertDialog.Builder(this).setMessage("Iltimos dostup bering yo`qsa sizga yordam bera olmayman")
                    .setCancelable(false)
                    .setPositiveButton("Ok") { dialog, which ->
                        val fragmentActivity: FragmentActivity = this
                        if (fragmentActivity != null) {
                            fragmentActivity.startActivity(
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", fragmentActivity.packageName, null)
                                )
                            )
                        }
                    }.show()
            }
        }

    }
}