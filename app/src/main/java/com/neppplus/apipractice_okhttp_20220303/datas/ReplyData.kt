package com.neppplus.apipractice_okhttp_20220303.datas

import android.icu.util.Calendar
import org.json.JSONObject

class ReplyData(
    var id : Int,
    var content: String
)  {

    var writer = UserData() // 모든 뎃글에는 작성자가 있다. null 가능성이 없다.

    var selectedSide = SideData()

//    일시 데이터를 변경 => 내부의 숫자만 변경
    val createdAt = java.util.Calendar.getInstance()

    //    보조 생성자 추가 연습 : 파라미터 x.
    constructor() : this(0, "내용없음")

    companion object {
        fun getReplayDataFromJson(jsonObj : JSONObject) : ReplyData {
            val replyData = ReplyData()

//            JSON 정보 > 멤버변수 채우기
            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")
            replyData.writer = UserData.getUserDataFromJson(jsonObj.getJSONObject("user"))

            replyData.selectedSide = SideData.getSideDataFromJson(jsonObj.getJSONObject("selected_side"))

            replyData.createdAt.set(2022, 1, 12,10,55,35)

            return replyData
        }
    }
}