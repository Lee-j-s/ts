package com.example.myapplication
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val textView1 = findViewById<TextView>(R.id.textView1)

        if (textView1 != null) {
            textView1.isSelected = true
        } else {
            // textView1이 null인 경우에 대한 처리
        }


        val mapIntent = Intent(this, MapActivity::class.java)
        val FormMainIntent = Intent(this, FormMainActivity::class.java)
        val bannerImageView: ImageView? = findViewById(R.id.banner_image_view)




        bannerImageView!!.setOnClickListener {
            Log.d("MainActivity", "ImageView Clicked")
            // 링크로 이동하거나 다른 액션 수행
            // 예: Intent를 사용하여 웹 페이지로 이동
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.e-gen.or.kr/egen/first_aid_basics.do"))
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            startActivity(mapIntent)
        }

        // 추가: button3를 클릭하면 FormMainActivity로 이동
        binding.button3.setOnClickListener {
            startActivity(FormMainIntent)
        }

        binding.button2.setOnClickListener {
            val alarmIntent = Intent(this, AlarmActivity::class.java)
            startActivity(alarmIntent)
        }

    }
}