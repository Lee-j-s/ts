package com.example.myapplication


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedInputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLEncoder

class NameMainActivity : AppCompatActivity() {
    private var linearLayoutManager: LinearLayoutManager? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: NameMyAdapter? = null
    private var parser: XmlPullParser? = null
    private var edit: EditText? = null
    private var imageView: ImageView? = null
    private var resultDrug: TextView? = null
    private var eventType = 0
    private var requestDrugUrl: String? = null
    private var list: ArrayList<NameDrug?>? = null
    private var nameDrug: NameDrug? = null
    private var imag: String? = null
    private val key =
        "%2FZlhrHrcea1N%2FQVC6ITHp8FRXtR44MCEb0x%2BIk0pJv5T1MWVogGr%2BsoJ8%2BNxvFy5c1knlSK7ED4pdkJ0o0Dsbg%3D%3D" // 약국 공공데이터 서비스키
    private var getEdit: String? = null

    // 로딩중을 띄워주는 progressDialog
    private var progressDialog: ProgressDialog? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.name_activity_main)
        edit = findViewById<View>(R.id.edit) as EditText

        resultDrug = findViewById<View>(R.id.resultDrug) as TextView
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView // 리사이클러뷰 초기화
        recyclerView!!.setHasFixedSize(true) // 리사이클러뷰 기존 성능 강화

        // 리니어레이아웃을 사용하여 리사이클러뷰에 넣어줄것임
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager

        // progressDialog 객체 선언
        progressDialog = ProgressDialog(this)
    }

    fun mOnClick(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(edit!!.windowToken, 0)
        getEdit = edit!!.text.toString()
        if (getEdit.isNullOrEmpty()) {
            Toast.makeText(applicationContext, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
        } else {
            progressDialog?.setMessage("로딩중입니다.")
            progressDialog?.show()
            if (view.id == R.id.buttonNameSearch) {
                Thread {
                    runOnUiThread {
                        val myAsyncTask = MyAsyncTaskImpl()
                        myAsyncTask.execute()
                    }
                }.start()
            }
        }
    }

    inner class MyAsyncTaskImpl : MyAsyncTask() {
        // MyAsyncTask를 상속받는 실제 클래스
    }

    abstract inner class MyAsyncTask : AsyncTask<String?, Void?, String?>() {
        override fun doInBackground(vararg params: String?): String? {
            val str = edit!!.text.toString()
            var drugSearch: String? = null
            val myDrug = NameDrug()
            myDrug.drugName = "Paracetamol" // setter 호출
            val name = myDrug.drugName // getter 호출

            try {
                drugSearch = URLEncoder.encode(str, "UTF-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
            try {
                var drugName = false
                var company = false
                var image = false
                var className = false
                var etcOtcName = false

                //공공데이터 파싱을 위한 주소


                //공공데이터 파싱을 위한 주소
                requestDrugUrl =
                    ("http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?ServiceKey=" //요청 URL
                            + key + "&numOfRows=100&item_name=" + drugSearch) //약 이름으로 검색 하기.


                val url = URL(requestDrugUrl)
                val `is` = url.openStream()
                val factory = XmlPullParserFactory.newInstance()
                parser = factory.newPullParser()
                parser!!.setInput(InputStreamReader(`is`, "UTF-8"))

                eventType = parser!!.eventType

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_DOCUMENT -> list = ArrayList()
                        XmlPullParser.END_TAG -> if (parser!!.name == "item" && nameDrug != null) {
                            list!!.add(nameDrug)
                        }

                        XmlPullParser.START_TAG -> {
                            if (parser!!.name == "item") {
                                nameDrug = NameDrug()
                            }
                            if (parser!!.name == "ITEM_NAME") drugName = true
                            if (parser!!.name == "ENTP_NAME") company = true
                            if (parser!!.name == "ITEM_IMAGE") image = true
                            if (parser!!.name == "CLASS_NAME") className = true
                            if (parser!!.name == "ETC_OTC_NAME") etcOtcName = true
                        }

                        XmlPullParser.TEXT -> when {
                            drugName -> {
                                nameDrug?.drugName = parser?.text
                                drugName = false
                            }

                            company -> {
                                nameDrug?.company = parser?.text
                                company = false
                            }

                            className -> {
                                nameDrug?.className = parser?.text
                                className = false
                            }

                            etcOtcName -> {
                                nameDrug?.etcOtcName = parser?.text
                                etcOtcName = false
                            }

                            image -> {
                                imag = parser?.text
                                try {
                                    val url1 = URL(imag)
                                    val conn = url1.openConnection()
                                    conn.connect()
                                    val bis = BufferedInputStream(conn.getInputStream())
                                    val bm = BitmapFactory.decodeStream(bis)
                                    bis.close()
                                    nameDrug?.image = bm
                                    image = false
                                } catch (e: Exception) {
                                    Log.e("Error", "Failed to load image")
                                }
                            }
                        }
                    }
                    eventType = parser!!.next()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            progressDialog?.dismiss()

            adapter = NameMyAdapter(applicationContext, list)
            recyclerView?.adapter = adapter
            adapter?.notifyDataSetChanged()

            if (list.isNullOrEmpty()) {
                resultDrug?.text = "검색 결과와 일치하는 약이 없습니다."
            } else {
                resultDrug?.text = ""
            }
        }
    }
}
