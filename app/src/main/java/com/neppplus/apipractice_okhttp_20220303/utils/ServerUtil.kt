package com.neppplus.apipractice_okhttp_20220303.utils

import okhttp3.FormBody
import okhttp3.Request

class ServerUtil {

//    서버에 Request를 날리는 역할.
//    함수를 만들려고 하는데, 어떤 객체가 실행해도 결과만 잘 나오는 함수를 만들자.
//    코틀린에서 static에 해당하는 개념? companion object { } 에 만들자.
    companion object {
        private val BASE_URL = "http://54.180.52.26"

//      로그인 기능 호출 함수

        fun postRequestLogin( id:String, pw:String ) {
//           Request 제작 -> 실제 호출  -> 서버의 응답을 화면에 전달
//            제작 1) 어느 주소 (url)로 접근할지? => 서버주소 + 기능 주소
            val urlString = "${BASE_URL}/user"
//            제작 2) 파라미터 담아주기 => 어떤 이름표 / 어느 공간에
            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()
//            제작 3) 모든 Request 정보를 종합한 객체 생성. (어는 주소로
            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .build()


        }
    }
}