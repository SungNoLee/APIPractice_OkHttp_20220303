package com.neppplus.apipractice_okhttp_20220303.datas

import org.json.JSONObject

class ReplyData(
    var id : Int,
    var content: String
)  {

    //    보조 생성자 추가 연습 : 파라미터 x.
    constructor() : this(0, "내용없음")

    companion object {
        fun getReplayDataFromJson(jsonObj : JSONObject) : ReplyData {
            val replyData = ReplyData()

//            JSON 정보 > 멤버변수 채우기
            return replyData
        }
    }
}