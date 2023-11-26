package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.URL
import java.net.URLEncoder

class NameMyAdapter internal constructor(
    private val mContext: Context,
    private val mList: ArrayList<NameDrug?>?
) : RecyclerView.Adapter<NameMyAdapter.MyViewHolder>() {
    private var drugString: String? = null
    private var searchString: String? = null
    private val intent: Intent? = null
    private var data: String? = null
    private val mInflate: LayoutInflater

    init {
        mInflate = LayoutInflater.from(mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = mInflate.inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    // NameMyAdapter 클래스 내부의 onBindViewHolder 함수 일부
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mList!![position]?.image).into(holder.list_image)
        holder.tv_name.text = mList[position]!!.drugName
        holder.tv_company.text = mList[position]!!.company
        holder.tv_className.text = mList[position]!!.className
        holder.tv_etcOtcName.text = mList[position]!!.etcOtcName

        holder.itemView.setOnClickListener {
            val drugString = mList[position]!!.drugName

            Thread {
                val data = getXmlData(drugString)

                val intent = Intent(mContext, NameMainActivity::class.java) // 대상 액티비티 클래스로 변경
                intent.putExtra("Drug", mList[position]!!.drugName)
                intent.putExtra("data", data)
                val bitmap = mList[position]!!.image
                val stream = ByteArrayOutputStream()
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val b = stream.toByteArray()
                intent.putExtra("image", b)
                intent.putExtra("count", 1)

                // UI 조작은 메인 스레드에서 실행되어야 함
                (mContext as NameMainActivity).runOnUiThread {
                    mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                }
            }.start()
        }
    }


    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    class MyViewHolder(var mView: View) : RecyclerView.ViewHolder(mView) {
        var list_image: ImageView
        var tv_name: TextView
        var tv_company: TextView
        var tv_etcOtcName: TextView
        var tv_className: TextView

        init {
            list_image = itemView.findViewById(R.id.list_image)
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_company = itemView.findViewById(R.id.tv_company)
            tv_etcOtcName = itemView.findViewById(R.id.tv_etcOtcName)
            tv_className = itemView.findViewById(R.id.tv_className)
        }
    }

    fun getXmlData(string: String?): String {
        val buffer = StringBuffer()
        try {
            searchString = URLEncoder.encode(string, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        val key = "gyhnkvw8BuHNtPGQzXT5Nluh3Ri3hGlcpEnheMdjI1gjDbZhPSEpy05ofIMaFu2a96c%2FUX%2FzOVblYrTa%2B%2Fu%2Bjg%3D%3D"
        val requestUrl = ("http://apis.data.go.kr/1471057/MdcinPrductPrmisnInfoService/getMdcinPrductItem?ServiceKey="
                + key + "&item_name=" + searchString)
        Log.e("drugSearch : ", requestUrl)
        try {
            var Nb_doc_data = false
            var doc = false
            var ee_doc_data = false
            var paragraph = false
            var ud_doc_data = false
            var article = false
            var articleEnd = false

            val url = URL(requestUrl)
            val `is` = url.openStream()
            val factory = XmlPullParserFactory.newInstance()
            val parser = factory.newPullParser()
            parser.setInput(InputStreamReader(`is`, "UTF-8"))

            var eventType = parser.eventType
            parser.next()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_DOCUMENT -> {}
                    XmlPullParser.END_TAG -> {
                        if (parser.name == "item") {
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
                        if (parser.name == "DOC") {
                            val arti = parser.getAttributeValue(null, "title")
                            buffer.append("\n\n")
                            buffer.append("< ").append(arti).append(" >")
                            articleEnd = false
                            doc = true
                        }
                        if (parser.name == "ARTICLE") {
                            val arti = parser.getAttributeValue(null, "title")
                            buffer.append(arti)
                            article = true
                        }
                        if (parser.name == "PARAGRAPH") {
                            paragraph = true
                        }
                        if (parser.name == "EE_DOC_DATA") ee_doc_data = true
                        if (parser.name == "UD_DOC_DATA") {
                            ud_doc_data = true
                        }
                        if (parser.name == "NB_DOC_DATA") {
                            Nb_doc_data = true
                        }
                    }

                    XmlPullParser.TEXT -> if (ee_doc_data) {
                        if (doc) {
                            if (!articleEnd) {
                                if (article) {
                                    if (paragraph) {
                                        val ee_text = parser.text
                                        buffer.append(ee_text)
                                    }
                                }
                                buffer.append("\n")
                                break
                            }
                        }
                        ee_doc_data = false
                    } else if (ud_doc_data) {
                        if (doc) {
                            if (!articleEnd) {
                                if (article) {
                                    if (paragraph) {
                                        val ud_text = parser.text
                                        if (ud_text.contains("<") || ud_text.contains("&")) {
                                            buffer.append(HtmlCompat.fromHtml(ud_text, HtmlCompat.FROM_HTML_MODE_LEGACY))
                                        } else {
                                            buffer.append(ud_text)
                                        }
                                    }
                                }
                                buffer.append("\n")
                                break
                            }
                        }
                        ud_doc_data = false
                    } else if (Nb_doc_data) {
                        if (doc) {
                            if (!articleEnd) {
                                if (article) {
                                    if (paragraph) {
                                        val nb_doc_data = parser.text
                                        if (nb_doc_data.contains("<")

                                            || nb_doc_data.contains("&")) {
                                            buffer.append(HtmlCompat.fromHtml(nb_doc_data, HtmlCompat.FROM_HTML_MODE_LEGACY))
                                        } else {
                                            buffer.append(nb_doc_data)
                                        }
                                    }
                                    buffer.append("\n")
                                }
                                break
                            }
                        }
                        ud_doc_data = false
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return buffer.toString()
    }

    companion object {
        private const val sort = "name"
    }
}