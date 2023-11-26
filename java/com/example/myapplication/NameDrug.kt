package com.example.myapplication

import android.graphics.Bitmap

//getter와 setter를 정의함. 이것을 이용해서 값을 저장하고 불러올것임
class NameDrug {
    var image: Bitmap? = null //image는 Bitmap값을 이용해야한다.
    var drugName: String? = null
    var company: String? = null
    var className: String? = null
    var etcOtcName: String? = null
}