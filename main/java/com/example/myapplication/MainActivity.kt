package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import android.view.View
import android.widget.ImageView
import android.widget.Button

class MainActivity : AppCompatActivity() {



    val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val intent = Intent(this, MapActivity::class.java)
        binding.button.setOnClickListener{startActivity(intent)}
    }
}