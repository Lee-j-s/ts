package com.example.myapplication

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkLocationPermissionWithRationale()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            val jsonString = this@MapActivity.assets.open("phars.json").bufferedReader().readText()
            val jsonType = object : TypeToken<PharmacyInfo>() {}.type
            val pharmacyInfo = Gson().fromJson(jsonString, jsonType) as PharmacyInfo

            // "kdu1.png" 이미지를 사용한 마커 아이콘 생성
            val kdu1Bitmap = BitmapFactory.decodeResource(resources, R.drawable.kdu2)
            val resizedBitmap = Bitmap.createScaledBitmap(kdu1Bitmap, 100, 100, false)
            val kdu2Icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap)

            pharmacyInfo.pharmacy.forEach {
                // "kdu1.png" 이미지를 사용한 마커 추가
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(it.latitude, it.longitude))
                        .title(it.name)
                        .icon(kdu2Icon)
                )
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(pharmacyInfo.pharmacy[0].latitude, pharmacyInfo.pharmacy[0].longitude), 12F))
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        }
    }


    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }

    private fun checkLocationPermissionWithRationale() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(this)
                    .setTitle("위치정보")
                    .setMessage("이 앱을 사용하기 위해서는 위치정보에 접근이 필요합니다. 위치정보 접근을 허용하여 주세요.")
                    .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION

                        )
                    }
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION

                )




            }
        }
    }
}
