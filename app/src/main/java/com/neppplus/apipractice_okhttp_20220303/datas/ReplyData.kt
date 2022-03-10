package com.neppplus.apipractice_okhttp_20220303.datas

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import org.json.JSONObject

class ReplyData(
    var id : Int,
    var content: String
)  {

    var writer = UserData() // 모든 뎃글에는 작성자가 있다. null 가능성이 없다.

    var selectedSide = SideData()

//    일시 데이터를 변경 => 내부의 숫자만 변경
//    val createdAt = java.util.Calendar.getInstance()

    //    보조 생성자 추가 연습 : 파라미터 x.
    constructor() : this(0, "내용없음")

//    fun getFormattedCreatedAt() : String {
//        가공 양식 지정
//        val sdf = SimpleDateFormat("M월 d일 a h시 m분")

//        val localCal = Calendar.getInstance()
//        작성일시의 일시값을 그래도 복사 (원래값 : 현
//      return
//    }
    companion object {
        fun getReplayDataFromJson(jsonObj : JSONObject) : ReplyData {
            val replyData = ReplyData()

//            JSON 정보 > 멤버변수 채우기
            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")
            replyData.writer = UserData.getUserDataFromJson(jsonObj.getJSONObject("user"))

            replyData.selectedSide = SideData.getSideDataFromJson(jsonObj.getJSONObject("selected_side"))

//            replyData.createdAt.set(2022, 1, 12,10,55,35)
//            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//
//            val createdAtStr = jsonObj.getString("create_at")
//
//            replyData.createdAt.time = sdf.parse(createdAtStr)


            return replyData
        }
    }
}