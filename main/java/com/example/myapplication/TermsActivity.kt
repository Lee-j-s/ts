package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.SignUpActivity

class TermsActivity : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)


        val checkBoxAgree = findViewById<CheckBox>(R.id.checkBoxAgree) // CheckBox 찾아오기
        val buttonNext = findViewById<Button>(R.id.buttonNext)

        buttonNext.setOnClickListener {
            if (checkBoxAgree.isChecked) {
                // 이용약관에 동의한 경우에만 회원가입 화면으로 이동
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            } else {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("알림")
                alertDialogBuilder.setMessage("이용약관에 동의해주세요.")
                alertDialogBuilder.setPositiveButton("확인", null)
                alertDialogBuilder.show()// 이용약관에 동의하지 않은 경우, 사용자에게 알려주는 코드를 작성할 수 있습니다.
            }
        }
        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener {
            finish() // 현재 액티비티를 종료하여 이전 화면으로 돌아갑니다.
        }
    }
}