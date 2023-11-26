package com.example.myapplication
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class FormMainActivity : AppCompatActivity() {

    // 각각의 카테고리에서 최종적으로 선택한 것 저장
    private var choosecolor: String? = null // 선택한 색상 저장
    private var chooseshape: String? = null // 선택한 모양 저장
    private var choosetype: String? = null // 선택한 제형 저장
    private var searchmarkfront: String? = null // 식별자 검색 저장(앞)
    private var searchmarkback: String? = null // 식별자 검색 저장(뒤)

    //색상 버튼과 관련
    var colorBtn = arrayOfNulls<Button>(16) //색상 버튼 배열
    var result_colorbtn: Button? = null //버튼의 id값 저장
    private var colorbtn_id: String? = null //버튼의 id값
    private var thiscolor: String? = null // 비교할 색상 값

    //모양 버튼과 관련
    var shapeBtn = arrayOfNulls<Button>(11) //모양 버튼 배열
    var result_shapebtn: Button? = null //버튼의 id값 저장
    private var shapebtn_id: String? = null //버튼의 id값
    private var thisshape: String? = null // 비교할 색상 값

    //제형 버튼과 관련
    var typeBtn = arrayOfNulls<Button>(4) //모양 버튼 배열
    var result_typebtn: Button? = null //버튼의 id값 저장
    private var typebtn_id: String? = null //버튼의 id값
    private var thistype: String? = null // 비교할 색상 값
    var textcolor: TextView? = null
    var textshape: TextView? = null
    var texttype: TextView? = null
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity_main)


        //색상 버튼 눌린것 텍스트뷰로 띄워줄것
        textcolor = findViewById<View>(R.id.choosecolor) as TextView
        textshape = findViewById<View>(R.id.chooseshape) as TextView
        texttype = findViewById<View>(R.id.choosetype) as TextView

        //로딩중 progressdialog
        //  progressDialog = new ProgressDialog(this);
        thiscolor = textcolor!!.text.toString()
        thisshape = textshape!!.text.toString()
        thistype = texttype!!.text.toString()
        Log.e("지금 색:", thiscolor!!)

        //색상, 모양, 제형 버튼 이벤트 실행
        settingColorbtn()
        settingShapebtn()
        settingTypebtn()
    }

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ//
    //색상 버튼 이벤트
    fun settingColorbtn() {
        for (i in colorBtn.indices) {
            colorbtn_id = "color_btn" + (i + 1) //버튼 아이디값 저장
            colorBtn[i] =
                findViewById(resources.getIdentifier(colorbtn_id, "id", packageName)) //버튼 초기화
        }
        for (buttonId in colorBtn) {
            buttonId!!.setOnClickListener { v ->
                result_colorbtn = findViewById(v.id)
                result_colorbtn?.run {
                    setBackgroundResource(R.drawable.choose_btton) //해당아이디 버튼의 배경색을 바꿈
                    setTextColor(Color.WHITE)
                    choosecolor = getText().toString()
                } //선택 색상을 저장

                //////여기서 for문으로 thiscolor랑 result.getText.toString()비교해서 배경색 다시 바꿔주기
                Log.e("다음 클릭 후 : ", thiscolor!!)
                for (j in colorBtn.indices) {
                    if (colorBtn[j]!!.text.toString() != choosecolor) {
                        colorBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                        colorBtn[j]!!.setTextColor(Color.BLACK)
                    }
                    if (colorBtn[j]!!.text.toString() == thiscolor) {
                        colorBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                        colorBtn[j]!!.setTextColor(Color.BLACK)
                    }
                }
                thiscolor = textcolor!!.text.toString()
            }
        }
    }

    // 모양 버튼 이벤트
    fun settingShapebtn() {
        for (i in shapeBtn.indices) {
            shapebtn_id = "shape_btn" + (i + 1) //버튼 아이디값 저장
            shapeBtn[i] = findViewById(resources.getIdentifier(shapebtn_id, "id", packageName))
        }
        for (buttonId in shapeBtn) {
            buttonId!!.setOnClickListener { v ->
                result_shapebtn = findViewById(v.id)
                result_shapebtn?.run {
                    setBackgroundResource(R.drawable.choose_btton) //해당아이디 버튼의 배경색을 하양으로 바꿈
                    setTextColor(Color.WHITE)
                    chooseshape = getText().toString()
                }
                Log.e("다음 클릭 후 : ", thisshape!!)
                for (j in shapeBtn.indices) {
                    if (shapeBtn[j]!!.text.toString() != chooseshape) {
                        shapeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                        shapeBtn[j]!!.setTextColor(Color.BLACK)
                    }
                    if (shapeBtn[j]!!.text.toString() == thisshape) {
                        shapeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                        shapeBtn[j]!!.setTextColor(Color.BLACK)
                    }
                }

                //  textcolor.setText(result.getText()); // 선택 색상을 보여줄 textview
                thisshape = textshape!!.text.toString()
            }
        }
    }

    // 제형 버튼 이벤트
    fun settingTypebtn() {
        for (i in typeBtn.indices) {
            typebtn_id = "type_btn" + (i + 1) //버튼 아이디값 저장
            typeBtn[i] = findViewById(resources.getIdentifier(typebtn_id, "id", packageName)) //초기화
        }
        for (buttonId in typeBtn) {
            buttonId!!.setOnClickListener { v ->
                result_typebtn = findViewById(v.id)
                result_typebtn?.run {
                    setBackgroundResource(R.drawable.choose_btton) //해당아이디 버튼의 배경색을 하양으로 바꿈
                    setTextColor(Color.WHITE)
                    choosetype = getText().toString()
                }
                if (choosetype!!.contains("정")) {
                    choosetype =
                        "나정, 필름코팅정, 서방정, 저작정, 추어블정(저작정), 구강붕해정, 서방성필름코팅정, 장용성필름코팅정, 다층정, 분산정(현탁정), 정제"
                } else if (choosetype!!.contains("경질")) {
                    choosetype = "경질캡슐제|산제, 경질캡슐제|과립제, 경질캡슐제|장용성과립제, 스팬슐, 서방성캡슐제|펠렛"
                } else if (choosetype!!.contains("연질")) {
                    choosetype = "연질캡슐제|현탁상, 연질캡슐제|액상"
                } else if (choosetype!!.contains("기타")) {
                    choosetype = "껌제, 트로키제"
                }

                //texttype.setText(choosetype);
                Log.e("choosetype ?????", choosetype!!)
                Log.e("다음 클릭 후 : ", thistype!!)
                for (j in typeBtn.indices) {
                    if (typeBtn[j]!!.text.toString().contains("정")) {
                        if (!choosetype!!.contains("정")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("정")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                    } else if (typeBtn[j]!!.text.toString().contains("경질")) {
                        if (!choosetype!!.contains("경질")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("경질")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                    } else if (typeBtn[j]!!.text.toString().contains("연질")) {
                        if (!choosetype!!.contains("연질")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("연질")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                    } else {
                        if (!choosetype!!.contains("껌제")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("제")) {
                            typeBtn[j]!!.setBackgroundResource(R.drawable.basic_button)
                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                    }
                }

                //  textcolor.setText(result.getText()); // 선택 색상을 보여줄 textview
                thistype = texttype!!.text.toString()
            }
        }
    }

    //식별자 앞 edittext값 초기화, 저장
    fun takeMarkfront() {
        val markfront = findViewById<View>(R.id.mark_front) as EditText
        searchmarkfront = markfront.text.toString()
        searchmarkfront = if (searchmarkfront!!.length == 0) {
            null // 입력된 값이 없을때 '-'로 저장
        } else {
            searchmarkfront
        }
    }

    //식별자 뒤 edittext값 초기화, 저장
    fun takeMarkBack() {
        val markback = findViewById<View>(R.id.mark_Back) as EditText
        searchmarkback = markback.text.toString()
        searchmarkback = if (searchmarkback!!.length == 0) {
            null
        } else {
            searchmarkback
        }
    }

    // 검색을 수행하는 메서드
    private fun performSearch() {
        // 여기에 실제 검색 로직을 추가하고, 검색 결과를 처리하는 코드를 작성하세요.
        // 검색 결과를 다음 화면으로 전달하는 코드 등을 추가할 수 있습니다.

        // 예제로 검색 결과를 보여주는 화면인 FormSearchActivity로 이동하는 코드를 추가합니다.
        val intent = Intent(applicationContext, FormSearchActivity::class.java)

        // 선택한 옵션들을 검색 결과로 전달
        intent.putExtra("choosecolor", choosecolor)
        intent.putExtra("chooseshape", chooseshape)
        intent.putExtra("choosetype", choosetype)

        // 입력한 식별표시를 검색 결과로 전달
        takeMarkfront() // 식별자 앞 edit에 입력한 텍스트값 가져오기
        takeMarkBack()
        intent.putExtra("searchmarkfront", searchmarkfront)
        intent.putExtra("searchmarkback", searchmarkback)

        startActivity(intent)
    }

    // 검색 결과 버튼 클릭 이벤트
    fun click_result(view: View?) {
        // 검색을 수행하는 메서드 호출
        performSearch()
    }


    // 초기화 버튼
    fun click_research(view: View?) {
        choosecolor = null
        chooseshape = null
        choosetype = null
        searchmarkfront = null // 식별표시 앞 부분 초기화
        searchmarkback = null // 식별표시 뒷 부분 초기화

        val myToast = Toast.makeText(this.applicationContext, "선택이 초기화 되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        // 색상 초기화
        for (i in colorBtn.indices) {
            colorBtn[i]!!.setBackgroundColor(Color.WHITE)
            colorBtn[i]!!.setBackgroundResource(R.drawable.basic_button)
            colorBtn[i]!!.setTextColor(Color.BLACK)
        }

        // 모양 초기화
        for (i in shapeBtn.indices) {
            shapeBtn[i]!!.setBackgroundColor(Color.WHITE)
            shapeBtn[i]!!.setBackgroundResource(R.drawable.basic_button)
            shapeBtn[i]!!.setTextColor(Color.BLACK)
        }

        // 제형 초기화
        for (i in typeBtn.indices) {
            typeBtn[i]!!.setBackgroundColor(Color.WHITE)
            typeBtn[i]!!.setBackgroundResource(R.drawable.basic_button)
            typeBtn[i]!!.setTextColor(Color.BLACK)
        }

        // 식별표시 초기화
        val markfront = findViewById<View>(R.id.mark_front) as EditText
        markfront.text.clear()

        val markback = findViewById<View>(R.id.mark_Back) as EditText
        markback.text.clear()
    }

    companion object {
        private const val TAG = "Ma"
    }
}