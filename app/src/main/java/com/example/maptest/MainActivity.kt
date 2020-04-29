package com.example.maptest

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =1
    lateinit var textview :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textview = findViewById(R.id.tx)

        if(isLocationAllowed()){
            val loctionProvider =LocationProvider(this)
            val location = loctionProvider.bestLastknownLocation
            if (location == null )
              Toast.makeText(this,"null",Toast.LENGTH_SHORT).show()
            Toast.makeText(this,"gps is allowed",Toast.LENGTH_SHORT).show()
           val s = location?.latitude.toString()
            val k = location?.longitude.toString()
            textview.text = "$s + $k"
        }
        else{
            requestPermission()
        }



    }

    private fun isLocationAllowed():Boolean{
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return false
        }
        return true
    }
    private fun requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this,"gps is dined",Toast.LENGTH_SHORT).show()
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)

            }
         }
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this,"gps is allowed",Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"gps is dined",Toast.LENGTH_SHORT).show()                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
