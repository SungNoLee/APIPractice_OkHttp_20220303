package com.neppplus.apipractice_okhttp_20220303.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData : Serializable {

    var id = 0  // id는 Int라고 명시.
    var title = ""  // title은 String이라고 명시.
    var imageURL = ""   // 서버 : img_url, 앱 : imageURL 변수명 다른 경우
    var replyConunt = 0

    companion object {
//    주제 정보을 담고 있는 JSONObject가 들어오면 > TopicData형태로 변환해주는 함수 -> static 메쏘드
        fun getTopicDataFromJson( jsonObj : JSONObject) : TopicData {
//            기본 내용의 TopicData 생성
            val topicData = TopicData()

//            JsonObj에서 데이터 추출>멤버변수 대입
            topicData.id = jsonObj.getInt("id")
            topicData.title = jsonObj.getString("title")
            topicData.imageURL = jsonObj.getString("imageURL")
            topicData.replyConunt = jsonObj.getInt("reply_count")

//            sides라는 JSONArray가 들어있음.
//            =>topicData의 하위 정보로 선택진영 목록으로 저장.
//            =>
            val sidesArr = jsonObj.getJSONArray("sides")
            for (i in 0 until sidesArr.length()) {
//                선택진영 정보를 들고있는 JSONObject 추출

                val sideObj = sidesArr.getJSONObject(i)
//                sideObj도 sideData로 (선택 진영) 변환.

            }
//            완성된 TopicData 리턴
            return topicData
        }
    }

}