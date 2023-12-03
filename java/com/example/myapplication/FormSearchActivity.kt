package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class FormSearchActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var list: ArrayList<FormDrug>? = null
    private var mAdapter: FormMyAdapter? = null
    private var choosecolor: String? = null
    private var chooseshape: String? = null
    private var choosetype: String? = null
    private var searchmarkfront: String? = null
    private var searchmarkback: String? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var drug_result: TextView? = null
    private var itemName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_shape_search_list)

        drug_result = findViewById<View>(R.id.drug_result) as TextView
        choosecolor = intent.getStringExtra("choosecolor")
        chooseshape = intent.getStringExtra("chooseshape")
        choosetype = intent.getStringExtra("choosetype")
        searchmarkfront = intent.getStringExtra("searchmarkfront")
        searchmarkback = intent.getStringExtra("searchmarkback")
        itemName = intent.getStringExtra("item_name")

        Log.e(
            "result : ",
            "$choosecolor/ $chooseshape/ $choosetype/$searchmarkfront/$searchmarkback/$itemName"
        )

        list = ArrayList()

        searchJson()

        recyclerView = findViewById<View>(R.id.rv_recyclerview) as RecyclerView
        recyclerView!!.setHasFixedSize(true)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = linearLayoutManager

        mAdapter = FormMyAdapter(applicationContext, list)
        recyclerView!!.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()

        updateResultText()
    }

    private fun searchJson() {
        try {
            assets.open("druglist.json").bufferedReader().use { reader ->
                val json = reader.readText()
                val jsonArray = JSONObject(json).getJSONArray("druglist")

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)

                    val colorMatches = choosecolor == null || jsonObject.getString("색상앞").contains(
                        choosecolor!!
                    )
                    val shapeMatches = chooseshape == null || chooseshape == jsonObject.getString("의약품제형")
                    val typeMatches = choosetype == null || choosetype!!.contains(jsonObject.getString("제형코드명"))

                    val markFrontMatches = searchmarkfront == null || searchmarkfront == jsonObject.getString("표시앞")
                    val markBackMatches = searchmarkback == null || searchmarkback == jsonObject.getString("표시뒤")
                    //이거바꿨음
                    val itemNameMatches =
                        itemName.isNullOrEmpty() || jsonObject.getString("품목명").contains(itemName!!)


                    if (colorMatches && shapeMatches && typeMatches && markFrontMatches && markBackMatches && itemNameMatches) {
                        addDrugToList(jsonObject)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun addDrugToList(jsonObject: JSONObject) {
        val formDrug = FormDrug().apply {
            image = jsonObject.getString("큰제품이미지")
            drugName = jsonObject.getString("품목명")
            company = jsonObject.getString("업소명")
            className = jsonObject.getString("분류명")
            etcOtcName = jsonObject.getString("전문일반구분")
        }
        list!!.add(formDrug)
    }

    private fun updateResultText() {
        drug_result!!.text =
            if (list!!.size != 0) " " else "검색 결과와 일치하는 약이 없습니다."
    }
}