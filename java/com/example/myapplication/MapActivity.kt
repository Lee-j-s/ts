package com.example.myapplication

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkLocationPermissionWithRationale()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // 사용자의 현재 위치를 가져와서 5km 반경 내의 약국을 표시
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                // 현재 위치의 좌표
                val currentLatLng = LatLng(location.latitude, location.longitude)

                // "kdu1.png" 이미지를 사용한 마커 아이콘 생성
                val kdu1Bitmap = BitmapFactory.decodeResource(resources, R.drawable.kdu2)
                val resizedBitmap = Bitmap.createScaledBitmap(kdu1Bitmap, 100, 100, false)
                val kdu2Icon = BitmapDescriptorFactory.fromBitmap(resizedBitmap)

                // 5km 반경 내의 약국 표시
                val jsonString = this@MapActivity.assets.open("phars.json").bufferedReader().readText()
                val jsonType = object : TypeToken<PharmacyInfo>() {}.type
                val pharmacyInfo = Gson().fromJson(jsonString, jsonType) as PharmacyInfo

                pharmacyInfo.pharmacy.forEach {
                    val pharmacyLatLng = LatLng(it.Latitude, it.Longitude)

                    // 5km 반경 내의 약국만 표시
                    if (calculateDistance(currentLatLng, pharmacyLatLng) <= 7000) {
                        googleMap.addMarker(
                            MarkerOptions()
                                .position(pharmacyLatLng)
                                .title(it.name)
                                .icon(kdu2Icon)
                        )
                    }
                }

                // 현재 위치로 지도 이동
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12F))

            }
        }

        // 사용자가 위치 권한을 허용한 경우 현재 위치 표시
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            googleMap.isMyLocationEnabled = true
        }
    }

    // 사용자의 현재 위치에서 약국까지의 거리 계산
    private fun calculateDistance(currentLatLng: LatLng, pharmacyLatLng: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            currentLatLng.latitude, currentLatLng.longitude,
            pharmacyLatLng.latitude, pharmacyLatLng.longitude, results
        )
        return results[0]
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