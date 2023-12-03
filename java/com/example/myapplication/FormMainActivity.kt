package com.example.myapplication
import android.annotation.SuppressLint
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
    private var itemName: String? = null

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
    var itemNameEditText: EditText? = null

    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity_main)


        //색상 버튼 눌린것 텍스트뷰로 띄워줄것
        textcolor = findViewById<View>(R.id.choosecolor) as TextView
        textshape = findViewById<View>(R.id.chooseshape) as TextView
        texttype = findViewById<View>(R.id.choosetype) as TextView
        itemNameEditText = findViewById(R.id.item_name_edittext)

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
    // 색상 버튼 이벤트
    // 색상 버튼 이벤트
    fun settingColorbtn() {
        for (i in colorBtn.indices) {
            colorbtn_id = "color_btn" + (i + 1) // 버튼 아이디값 저장
            colorBtn[i] = findViewById(resources.getIdentifier(colorbtn_id, "id", packageName)) // 버튼 초기화
        }

        var prevColorBtn: Button? = null // 이전에 선택한 색상 버튼

        for (buttonId in colorBtn) {
            buttonId!!.setOnClickListener { v ->
                result_colorbtn = findViewById(v.id)
                result_colorbtn?.run {
                    choosecolor = getText().toString()
                    // 선택한 버튼의 백그라운드를 변경
                    when (v.id) {
                        R.id.color_btn1 -> {
                            setBackgroundColor(Color.WHITE) // 버튼1의 백그라운드를 하양색으로 변경
                        }
                        R.id.color_btn2 -> {
                            setBackgroundColor(Color.YELLOW) // 버튼2의 백그라운드를 노란색으로 변경
                        }
                        R.id.color_btn3 -> {
                            setBackgroundColor(Color.parseColor("#FFA500")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn4 -> {
                            setBackgroundColor(Color.parseColor("#FF99CC")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn5 -> {
                            setBackgroundColor(Color.parseColor("#FF0000")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn6 -> {
                            setBackgroundColor(Color.parseColor("#663300")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn7 -> {
                            setBackgroundColor(Color.parseColor("#99FF99")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn8 -> {
                            setBackgroundColor(Color.parseColor("#339933")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn9 -> {
                            setBackgroundColor(Color.parseColor("#006666")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn10 -> {
                            setBackgroundColor(Color.parseColor("#0033FF")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn11 -> {
                            setBackgroundColor(Color.parseColor("#003399")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn12 -> {
                            setBackgroundColor(Color.parseColor("#660000")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn13 -> {
                            setBackgroundColor(Color.parseColor("#663399")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn14 -> {
                            setBackgroundColor(Color.parseColor("#C5C5C5")) // 버튼3의 백그라운드를 주황색으로 변경
                        }
                        R.id.color_btn15 -> {
                            setBackgroundColor(Color.parseColor("#000000")) // 버튼3의 백그라운드를 주황색으로 변경
                        }



                    }
                    setTextColor(Color.parseColor("#FFFFFF"))

                    // 이전에 선택한 버튼의 색상을 되돌림
                    prevColorBtn?.run {
                        when (id) {
                            R.id.color_btn1 -> {
                                setBackgroundColor(Color.parseColor("#FFFFFF")) // 버튼1의 백그라운드를 하양색으로 변경
                            }
                            R.id.color_btn2 -> {
                                setBackgroundColor(Color.parseColor("#FFFF00")) // 버튼2의 백그라운드를 노란색으로 변경
                            }
                            R.id.color_btn3 -> {
                                setBackgroundColor(Color.parseColor("#FFA500")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn4 -> {
                                setBackgroundColor(Color.parseColor("#FF99CC")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn5 -> {
                                setBackgroundColor(Color.parseColor("#FF0000")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn6 -> {
                                setBackgroundColor(Color.parseColor("#663300")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn7 -> {
                                setBackgroundColor(Color.parseColor("#99FF99")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn8 -> {
                                setBackgroundColor(Color.parseColor("#339933")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn9 -> {
                                setBackgroundColor(Color.parseColor("#006666")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn10 -> {
                                setBackgroundColor(Color.parseColor("#0033FF")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn11 -> {
                                setBackgroundColor(Color.parseColor("#003399")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn12 -> {
                                setBackgroundColor(Color.parseColor("#660000")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn13 -> {
                                setBackgroundColor(Color.parseColor("#663399")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn14 -> {
                                setBackgroundColor(Color.parseColor("#C5C5C5")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                            R.id.color_btn15 -> {
                                setBackgroundColor(Color.parseColor("#000000")) // 버튼3의 백그라운드를 주황색으로 변경
                            }
                        }
                        setTextColor(Color.parseColor("#000000"))
                    }
                }

                // 현재 선택한 버튼을 이전에 선택한 버튼으로 저장
                prevColorBtn = result_colorbtn
            }
        }
    }

    // 모양 버튼 이벤트
    fun settingShapebtn() {
        for (i in shapeBtn.indices) {
            shapebtn_id = "shape_btn" + (i + 1) // 버튼 아이디값 저장
            shapeBtn[i] = findViewById(resources.getIdentifier(shapebtn_id, "id", packageName)) // 버튼 초기화
        }

        var prevShapeBtn: Button? = null // 이전에 선택한 색상 버튼

        for (buttonId in shapeBtn) {
            buttonId!!.setOnClickListener { v ->
                result_shapebtn = findViewById(v.id)
                result_shapebtn?.run {
                    chooseshape = getText().toString()
                    // 선택한 버튼의 백그라운드를 변경
                    when (v.id) {
                        0 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e1)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        1 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e2)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        2 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e3)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        3 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e4)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        4 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e5)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        5 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e6)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        6 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e7)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        7 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e8)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        8 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e9)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        9 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e10)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        10 -> {
                            shapeBtn[id]!!.setBackgroundResource(R.drawable.e11)
                            colorBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        else -> {
                            // 나머지 버튼에 대한 처리를 추가할 수 있습니다.
                        }



                    }
                    setTextColor(Color.parseColor("#FFFFFF"))

                    // 이전에 선택한 버튼의 색상을 되돌림
                    prevShapeBtn?.run {
                        when (id) {
                            0 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e1)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            1 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e2)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            2 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e3)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            3 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e4)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            4 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e5)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            5 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e6)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            6 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e7)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            7 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e8)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            8 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e9)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            9 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e10)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            10 -> {
                                shapeBtn[id]!!.setBackgroundResource(R.drawable.e11)
                                colorBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            else -> {
                                // 나머지 버튼에 대한 처리를 추가할 수 있습니다.
                            }
                        }
                        setTextColor(Color.parseColor("#000000"))
                    }
                }

                // 현재 선택한 버튼을 이전에 선택한 버튼으로 저장
                prevShapeBtn = result_shapebtn
            }
        }
    }

    // 제형 버튼 이벤트
    fun settingTypebtn() {
        for (i in typeBtn.indices) {
            typebtn_id = "type_btn" + (i + 1) //버튼 아이디값 저장
            typeBtn[i] = findViewById(resources.getIdentifier(typebtn_id, "id", packageName)) //초기화
        }
        var prevTypeBtn: Button? = null
        for (buttonId in typeBtn) {
            buttonId!!.setOnClickListener { v ->
                result_typebtn = findViewById(v.id)
                result_typebtn?.run {
                    choosetype = getText().toString()
                    when (id) {
                        0 -> {
                            typeBtn[id]!!.setBackgroundResource(R.drawable.r1)
                            typeBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        1 -> {
                            typeBtn[id]!!.setBackgroundResource(R.drawable.r2)
                            typeBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        2 -> {
                            typeBtn[id]!!.setBackgroundResource(R.drawable.r3)
                            typeBtn[id]!!.setTextColor(Color.BLACK)
                        }
                        3 -> {
                            typeBtn[id]!!.setBackgroundResource(R.drawable.r4)
                            typeBtn[id]!!.setTextColor(Color.BLACK)
                        }

                    }
                    setTextColor(Color.parseColor("#FFFFFF"))

                    prevTypeBtn?.run {
                        when (id) {
                            0 -> {
                                typeBtn[id]!!.setBackgroundResource(R.drawable.r1)
                                typeBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            1 -> {
                                typeBtn[id]!!.setBackgroundResource(R.drawable.r2)
                                typeBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            2 -> {
                                typeBtn[id]!!.setBackgroundResource(R.drawable.r3)
                                typeBtn[id]!!.setTextColor(Color.BLACK)
                            }
                            3 -> {
                                typeBtn[id]!!.setBackgroundResource(R.drawable.r4)
                                typeBtn[id]!!.setTextColor(Color.BLACK)
                            }
                        }
                        setTextColor(Color.parseColor("#000000"))
                    }
                }
                prevTypeBtn = result_typebtn

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

                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("정")) {

                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                    } else if (typeBtn[j]!!.text.toString().contains("경질")) {
                        if (!choosetype!!.contains("경질")) {

                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("경질")) {

                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                    } else if (typeBtn[j]!!.text.toString().contains("연질")) {
                        if (!choosetype!!.contains("연질")) {

                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("연질")) {

                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                    } else {
                        if (!choosetype!!.contains("껌제")) {

                            typeBtn[j]!!.setTextColor(Color.BLACK)
                        }
                        if (thisshape!!.contains("제")) {

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
    private fun takeItemName() {
        itemName = itemNameEditText?.text.toString().trim()
        itemName = if (itemName.isNullOrEmpty()) {
            null
        } else {
            itemName
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

        takeItemName()
        intent.putExtra("item_name", itemName)

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
        itemName = null

        val myToast = Toast.makeText(this.applicationContext, "선택이 초기화 되었습니다.", Toast.LENGTH_SHORT)
        myToast.show()

        // 색상 초기화
        for (i in colorBtn.indices) {
            when (i) {
                0 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                1 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#FFFF00"))
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                2 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#FFA500")) // 주황색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                3 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#FF99CC")) // 분홍색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                4 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#FF0000")) // 빨강색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                5 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#663300")) // 갈색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                6 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#99FF99")) // 연두색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                7 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#339933")) // 청록색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                8 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#006666")) // 초록색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                9 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#0033FF")) // 파랑색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                10 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#003399")) // 남색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                11 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#660000")) // 자주색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                12 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#663399")) // 보라색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                13 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#C5C5C5")) // 회색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                14 -> {
                    colorBtn[i]!!.setBackgroundColor(Color.parseColor("#000000")) // 검정색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                15 -> {
                    colorBtn[i]!!.setBackgroundDrawable(null) // 투명색
                    colorBtn[i]!!.setTextColor(Color.BLACK)
                }
                else -> {
                    // 나머지 버튼에 대한 처리를 추가할 수 있습니다.
                }
            }
        }


        // 모양 초기화
        for (i in shapeBtn.indices) {
            when (i) {
                0 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e1)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                1 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e2)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                2 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e3)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                3 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e4)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                4 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e5)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                5 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e6)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                6 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e7)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                7 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e8)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                8 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e9)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                9 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e10)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                10 -> {
                    shapeBtn[i]!!.setBackgroundResource(R.drawable.e11)
                    shapeBtn[i]!!.setTextColor(Color.BLACK)
                }
                else -> {
                    // 나머지 버튼에 대한 처리를 추가할 수 있습니다.
                }

            }
        }

        // 제형 초기화
        for (i in typeBtn.indices) {
            when (i) {
                0 -> {
                    typeBtn[i]!!.setBackgroundResource(R.drawable.r1)
                    typeBtn[i]!!.setTextColor(Color.BLACK)
                }
                1 -> {
                    typeBtn[i]!!.setBackgroundResource(R.drawable.r2)
                    typeBtn[i]!!.setTextColor(Color.BLACK)
                }
                2 -> {
                    typeBtn[i]!!.setBackgroundResource(R.drawable.r3)
                    typeBtn[i]!!.setTextColor(Color.BLACK)
                }
                3 -> {
                    typeBtn[i]!!.setBackgroundResource(R.drawable.r4)
                    typeBtn[i]!!.setTextColor(Color.BLACK)
                }
            }
        }

        // 식별표시 초기화
        val markfront = findViewById<View>(R.id.mark_front) as EditText
        markfront.text.clear()

        val markback = findViewById<View>(R.id.mark_Back) as EditText
        markback.text.clear()

        itemNameEditText?.text?.clear()
    }

    companion object {
        private const val TAG = "Ma"
    }
}