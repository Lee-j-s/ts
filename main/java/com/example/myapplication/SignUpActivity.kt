package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

data class UserData(
    val username: String,
    val password: String,
    val name: String,
    val dob: Int
)

data class CheckDuplicateData(
    val username: String
)

data class ApiResponse(
    val success: Boolean,
    val message: String
)

interface MyApi {
    @POST("register")
    fun registerUser(@Body userData: UserData): Call<ApiResponse>

    @POST("login")
    fun loginUser(@Body loginData: LoginData): Call<ApiResponse>

    @POST("checkDuplicate")
    fun checkDuplicateUsername(@Body checkDuplicateData: CheckDuplicateData): Call<ApiResponse>
}

class SignUpActivity : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextDOB = findViewById<EditText>(R.id.editTextDOB)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-125-242-66.ap-northeast-2.compute.amazonaws.com:3306")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(MyApi::class.java)

        val buttonSignUp = findViewById<Button>(R.id.buttonSignUp)
        buttonSignUp.setOnClickListener {
            val username = editTextUsername.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()
            val name = editTextName.text.toString()
            val dob = editTextDOB.text.toString()

            // 중복 확인 코드 추가
            // ...

            if (!isValidUsername(username)) {
                editTextUsername.error = "유효한 아이디를 입력하세요 (영어와 숫자만 가능)"
                return@setOnClickListener
            }

            if (!isValidPassword(password, confirmPassword)) {
                editTextPassword.error = "유효한 비밀번호를 입력하세요 (8자 이상, 영어, 숫자, 특수문자만 가능)"
                editTextConfirmPassword.error = "비밀번호가 일치하지 않습니다"
                return@setOnClickListener
            }

            if (!isValidName(name)) {
                editTextName.error = "유효한 이름을 입력하세요 (한글과 영어만 가능)"
                return@setOnClickListener
            }

            if (!isValidDOB(dob)) {
                editTextDOB.error = "유효한 생년월일을 입력하세요 (숫자만 가능)"
                return@setOnClickListener
            }

            val userData = UserData(username, password, name, dob.toInt())

            val call = api.registerUser(userData)

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse?.success == true) {
                            // 회원가입 성공 메시지 표시
                            runOnUiThread {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "회원가입에 성공하였습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            // LoginActivity로 이동
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish() // 현재 Activity 종료
                        } else {
                            runOnUiThread{
                            Toast.makeText(
                                this@SignUpActivity,
                                "회원가입 실패.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }}
                    } else {
                        // 네트워크 오류 메시지 표시
                        println("네트워크 오류!")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    // 오류 처리
                    println("오류 발생: " + t.message)
                }
            })
        }

        val buttonCancel = findViewById<Button>(R.id.buttonCancel)
        buttonCancel.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // 중복 확인 버튼 클릭 이벤트 핸들러 추가
        val buttonCheckDuplicate = findViewById<Button>(R.id.buttonCheckDuplicate)
        buttonCheckDuplicate.setOnClickListener {
            val username = editTextUsername.text.toString()

            val call = api.checkDuplicateUsername(CheckDuplicateData(username))

            call.enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse?.success == false) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@SignUpActivity,
                                    "중복된 아이디입니다. 다른 아이디를 사용하세요.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        println("네트워크 오류!")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    println("오류 발생: " + t.message)
                }
            })
        }
    }

    private fun isValidUsername(username: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9]*\$")
        return regex.matches(username)
    }

    private fun isValidPassword(password: String, confirmPassword: String): Boolean {
        val regex = Regex("^[a-zA-Z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\",./<>?]*\$")
        return password.length >= 8 && password == confirmPassword && regex.matches(password)
    }

    private fun isValidName(name: String): Boolean {
        val regex = Regex("^[가-힣a-zA-Z]*\$")
        return regex.matches(name)
    }

    private fun isValidDOB(dob: String): Boolean {
        val regex = Regex("^[0-9]*\$")
        return regex.matches(dob)
    }
}
