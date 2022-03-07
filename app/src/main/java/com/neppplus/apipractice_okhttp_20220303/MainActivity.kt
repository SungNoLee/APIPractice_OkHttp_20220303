package com.neppplus.apipractice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.neppplus.apipractice_okhttp_20220303.databinding.ActivityMainBinding
import com.neppplus.apipractice_okhttp_20220303.datas.TopicData
import com.neppplus.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {
    lateinit var binding : ActivityMainBinding
//    실제로 서버
    val mTopicList = ArrayList<TopicData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    override fun setupEvents() {

    }

    override fun setValues() {

//        메인 화면 정보를 가져오기 => API 호출 / 응답 처리
        getTopicListFromServer()
    }

    fun getTopicListFromServer() {
        ServerUtil.getRequestMainInfo(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {
//                서버가 주는 토론 주제 목록 파싱 => mTopicList에 추가하기.
                val dataObj = jsonObj.getJSONObject("data")

                val topicsArr = dataObj.getJSONArray("topics")

//                topicsArr 내부를 하나씩 추출(JSONObject{ }) => TopicData() 로 변환.
//                JSONArray는 for-each 문법 지원 X. (차후 : ArrayList의 for-each 활용 예정)
//                JAVA : for (int i=0; i < 배열.length; i++) 와 완전히 동일한 문법.
                for (i in 0 until topicsArr.length()) {
//                    [] => {}, {}, 순서에 맞는 {}를 변수에 담자.
//                    JSON파싱의 {} => (JSONArray에게서) JSONObject로 추출

                    val topicObj = topicsArr.getJSONObject( i )
                    Log.d("받아낸주제", topicObj.toString())
//                    TopicData 변수 생성 => 멤버변수에 topicObj가 들고있는 값들을 대입.
                    val topicData = TopicData()
                    topicData.id = topicObj.getInt("id")
                    topicData.title = topicObj.getString("title")
                    topicData.imageURL = topicObj.getString("img_url")
                    topicData.replyConunt = topicObj.getInt("reply_count")

//                    완성된 TopicData 객체를 목록에 추가.
                    mTopicList.add(topicData)
                }
            }

        })
    }

}