package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class LoginData(
    val username: String,
    val password: String
)


class LoginActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val imageViewMain: ImageView = findViewById(R.id.imageViewMain)
        imageViewMain.setImageResource(R.drawable.kdf)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonGoToSignUp = findViewById<Button>(R.id.buttonGoToSignUp)



        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-3-37-128-247.ap-northeast-2.compute.amazonaws.com:3306") // 실제 서버 URL로 변경
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(MyApi::class.java)
            val loginData = LoginData(username, password)

            val call = api.loginUser(loginData)

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse?.success == true) {
                            Toast.makeText(this@LoginActivity, "로그인 성공!", Toast.LENGTH_LONG).show()

                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish() // 현재 Activity 종료
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "로그인 실패: ${apiResponse?.message}",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    } else {
                        // 네트워크 오류 메시지 표시
                        Toast.makeText(this@LoginActivity, "네트워크 오류!", Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "오류 발생: " + t.message, Toast.LENGTH_SHORT)
                        .show()

                }
            })
        }

        buttonGoToSignUp.setOnClickListener {
            val intent = Intent(this, TermsActivity::class.java)
            startActivity(intent)

        }

    }

    private fun Any.enqueue(callback: Callback<ApiResponse>) {

    }
}
