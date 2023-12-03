package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLEncoder

class FormMyAdapter internal constructor(
    private val mContext: Context,
    private val mList: ArrayList<FormDrug>?
) : RecyclerView.Adapter<FormMyAdapter.MyViewHolder>() {
    private var drugString: String? = null
    private val mInflate: LayoutInflater
    private var data: String? = null
    private val intent: Intent? = null
    private var searchString: String? = null

    init { //생성자를 context와 배열로 초기화해줌
        mInflate = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View = mInflate.inflate(R.layout.list_item, parent, false)

        //최초 view에 대한 list item에 대한 view를 생성함.
        //이 onBindViewHolder친구한테 실질적으로 매칭해주는 역할을 함.
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mList!![position].image)
            .into(holder.list_image)
        holder.tv_name.text = mList[position].drugName
        holder.tv_company.text = mList[position].company
        holder.tv_className.text = mList[position].className
        holder.tv_etcOtcName.text = mList[position].etcOtcName

        //해당하는 holder를 눌렀을 때 intent를 이용해서 상세정보 페이지로 넘겨줌
        holder.itemView.setOnClickListener {
            Thread { // TODO Auto-generated method stub
                //알고싶은 약의 상세정보를 누르면 그 약의 이름을 받아와 다시 파싱을 시작함
                //그렇기 때문에 약의 이름을 drugString에 저장해준 후 그 이름을 getXmlData()의 메서드로 넘겨줌
                drugString = mList[position].drugName
                data = getXmlData(drugString) //drugString에 해당하는 데이터를 string형식으로 가져와 data변수에 저장해줌


                //앞에는 key값, 뒤에는 실제 값
                intent!!.putExtra("Drug", drugString) //drug의 이름을 넘겨줌
                intent.putExtra("data", data) //파싱한 데이터들을 "data"의 키로 넘겨줌
                intent.putExtra("image", mList[position].image)
                intent.putExtra("sort", sort)

                //이미지의 용량을 작게 해주는 코드
                //-> intent로 이미지를 넘길 떼 이미지의 용량이  100kb로 제한되어있기 때문에 그 사이즈에 맞춰서 넘겨줘야함
                //이미지의 용량을 임의로 지정하여 intent로 넘겨주는 코드

                //Bitmap bitmap = StringToBitmap(mList.get(position).getImage().toString());
                //Log.e("bitmap : ",mList.get(position).getImage());
                /*
                                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                                         */
                //byte[] b = BitmapToByteArray(bitmap);
                // intent.putExtra("image",b); //image의 크기를 낮춰준 후 intent로 넘겨줌

                //전체의 intent를 실제로 넘겨주는 코드.
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }.start()
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    class MyViewHolder(var mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        var list_image: ImageView
        var tv_name: TextView
        var tv_company: TextView
        var tv_etcOtcName: TextView
        var tv_className: TextView

        init {
            list_image = itemView.findViewById<ImageView>(R.id.list_image) // 이름 list_image??
            tv_name = itemView.findViewById<TextView>(R.id.tv_name)
            tv_company = itemView.findViewById<TextView>(R.id.tv_company)
            tv_etcOtcName = itemView.findViewById<TextView>(R.id.tv_etcOtcName)
            tv_className = itemView.findViewById<TextView>(R.id.tv_className)
        }
    }

    /*
    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

     */
    //두번째 공공데이터 파싱하는 부분
    fun getXmlData(seq: String?): String {
        val buffer = StringBuffer()
        try { //인코딩을 위한 try catch문
            searchString = URLEncoder.encode(seq, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        //공공데이터 파싱을 위한 주소
        //약국 공공데이터 서비스키
        val key =
            "uyOvRzyDMGTEG8mac1aNFXB3unbuGHZkcVjILOLZDTKL9Ms5HqC%252FllygMJFyFW6QDt36muYkBKmAEtWxPzjvTg%253D%253D"
        val requestUrl =
            ("https://apis.data.go.kr/1471000/DrugPrdtPrmsnInfoService04/getDrugPrdtPrmsnInq04?" //요청 URL
                    + key + "&item_name=" + searchString) //약 이름으로 검색
        Log.e("drugSearch : ", requestUrl)
        try {
            //일단 false로 선언해준 후 파싱해온 tag이름과 같으면 true로 바꾸어 배열에 넣어줄것임
            var Nb_doc_data = false
            var doc = false
            var ee_doc_data = false
            var paragraph = false
            var ud_doc_data = false
            var article = false
            var articleEnd = false
            val tagName: String? = null

            //실질적으로 파싱해서 inputstream해주는 코드
            val url = URL(requestUrl)
            val `is` = url.openStream()
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(InputStreamReader(`is`, "UTF-8"))

            //파싱해온 주소의 eventType을 가져옴. 이것을 이용하여 파싱의 시작과 끝을 구분해좀
            var eventType = parser.eventType
            parser.next()
            //eventType이 END_DOCUMENT이 아닐때까지 while문이 돌아감
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_DOCUMENT -> {}
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "item") { //Tag 이름이 item일경우
                            Log.e("END_TAG : ", "END")
                        }
                        if (parser.name == "DOC") {
                            articleEnd = true
                        }
                        if (parser.name == "body") {
                            buffer.append("\n")
                            buffer.append("※ 허가 취소된 의약품이거나 상세정보를 제공하지 않는 의약품입니다. ※")
                        }
                    }

                    XmlPullParser.START_TAG -> {
                        if (parser.name == "item") {
                            buffer.append("\n")
                        }
                        //Tag가 시작될 때 다 true로 변경함

                        //xml파일은 Doc안에 Article안에 paragraph내에 text가 있는 구조임. 그래서 그 안의 구조를 가져오기 위해 이렇게 선언함.
                        if (parser.name == "DOC") {

                            //xml파일에서 Tag의 title에 적힌 값을 읽어오기 위한 코드.
                            val arti = parser.getAttributeValue(null, "title")
                            buffer.append("\n\n")
                            buffer.append("< ").append(arti).append(" >")
                            articleEnd = false //article의 End부분은 false로 선언해줌. 이것을 이용하여 문서의 끝을 알림.
                            doc = true
                        }
                        if (parser.name == "ARTICLE") {
                            //xml파일에서 Tag의 title에 적힌 값을 읽어오기 위한 코드.
                            val arti = parser.getAttributeValue(null, "title")
                            buffer.append(arti)
                            article = true
                        }
                        if (parser.name == "PARAGRAPH") {
                            paragraph = true
                        }
                        if (parser.name == "EE_DOC_DATA") {
                            ee_doc_data = true
                        }
                        //효능효과
                        if (parser.name == "UD_DOC_DATA") { //용법용량
                            ud_doc_data = true
                        }
                        if (parser.name == "NB_DOC_DATA") { //사용상의주의사항
                            Nb_doc_data = true
                        }
                    }

                    XmlPullParser.TEXT -> if (ee_doc_data) { //효능효과부분을 가져오는 코드
                        if (doc) { //doc 데이터 안에
                            if (!articleEnd) { //article부분이 끝날때까지 돌리기 위해 사용됨
                                if (article) { //article 부분에
                                    if (paragraph) { //paragraph부분. 이곳에 text가 있음.
                                        //parsing부분
                                        val ee_text = parser.text //text를 가져옴
                                        //Log.e("GBN_NAME : ", ee_text);
                                        //buffer.append(ee_text);//요소의 TEXT 읽어와서 문자열버퍼에 추가
                                        if (ee_text.contains("<") || ee_text.contains("&")) { //table형태 등 html문서로된 부분이 있으면 변환하여 buffer에 추가해줌
                                            buffer.append(Html.fromHtml(ee_text))
                                        } else { //html요소가 포함되어있지 않으면 그냥 buffer에 추가해줌
                                            buffer.append(ee_text)
                                        }
                                    }
                                }
                                buffer.append("\n") //꼭필요
                                break
                            }
                        }
                    } else if (ud_doc_data) { //용법용량부분을 가져오는 코드
                        if (doc) {
                            if (!articleEnd) {
                                if (article) {
                                    if (paragraph) {
                                        val ud_text = parser.text
                                        if (ud_text.contains("<") || ud_text.contains("&")) { //table형태 등 html문서로된 부분이 있으면 변환하여 buffer에 추가해줌
                                            buffer.append(Html.fromHtml(ud_text))
                                        } else { //html요소가 포함되어있지 않으면 그냥 buffer에 추가해줌
                                            buffer.append(ud_text)
                                        }
                                    }
                                }
                                buffer.append("\n")
                                break
                            }
                        }
                        ud_doc_data = false
                    } else if (Nb_doc_data) { //사용상의주의사항부분
                        if (doc) {
                            if (!articleEnd) {
                                if (article) {
                                    if (paragraph) {
                                        val nb_doc_data = parser.text
                                        //Log.e("GBN_NAME : ", nb_doc_data);
                                        if (nb_doc_data.contains("<") || nb_doc_data.contains("&")) { //table형태 등 html문서로된 부분이 있으면 변환하여 buffer에 추가해줌
                                            buffer.append(Html.fromHtml(nb_doc_data))
                                        } else { //html요소가 포함되어있지 않으면 그냥 buffer에 추가해줌
                                            buffer.append(nb_doc_data)
                                        }
                                    }
                                    buffer.append("\n")
                                }
                                break
                            }
                        }
                        ud_doc_data = false //다시 false로 돌리는 초기화함
                    }
                }
                eventType = parser.next() //다음 parser를 찾아옴
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toString() //buffer를 String형식으로 return해줌
    }

    companion object {
        private const val sort = "form"
    }
}