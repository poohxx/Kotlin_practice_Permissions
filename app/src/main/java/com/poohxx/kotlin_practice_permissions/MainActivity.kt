package com.poohxx.kotlin_practice_permissions

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private lateinit var launcherPermission: ActivityResultLauncher<Array<String>>
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerPermissionListener()
        checkCameraPermission()
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkCameraPermission(){
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "Camera Permissoin already ok", Toast.LENGTH_SHORT).show()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                Toast.makeText(this, "need permission", Toast.LENGTH_SHORT).show()

                launcherPermission.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION))

            }
            else -> {
                launcherPermission.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION))
            }
        }
    }
    private fun registerPermissionListener(){
        launcherPermission = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            if(it[Manifest.permission.CAMERA] == true)  {
                Toast.makeText(this, "Camera Permissoin is granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Camera Permission is denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}