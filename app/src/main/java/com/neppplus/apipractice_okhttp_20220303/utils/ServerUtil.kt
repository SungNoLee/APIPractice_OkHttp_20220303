package com.neppplus.apipractice_okhttp_20220303.utils

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {
//    서버유틸로 돌아온 응답을
//    나에게 생긴일을 > 다른 클래스에게 처리 요청 : interface 할용

    interface JsonResponseHandler {
        fun onResponse(jsonObject: JSONObject)
    }
//    서버에 Request를 날리는 역할.
//    함수를 만들려고 하는데, 어떤 객체가 실행해도 결과만 잘 나오는 함수를 만들자.
//    코틀린에서 static에 해당하는 개념? companion object { } 에 만들자.
    companion object {
        private val BASE_URL = "http://54.180.52.26"

//      로그인 기능 호출 함수
//      handler : 이 함수를 쓰는 화면에서 JSON 분석을 어떻게/UI에서 어떻게 활용할지 방안.(인터페이스)
//        - 처리방안을 임시로 비워두려면, null 대입 허용
    
        fun postRequestLogin( id:String, pw:String, handler: JsonResponseHandler? ) {
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
//            종합한 Request도 실제 호출을 해 줘야 API 호출이 실행됨.(startActivity 같은 동자 필요함)
//            실제 호출 : 클라이언트로써 동작 > OkHttpClient 클래스
            val client = OkHttpClient()

//           OkHttpClient 객체를 이용 > 서버에 로그인 기능 실제 호출
//            호출을 했으면, 서버가 수행한 결과를 받아서 처리.
//            => 서버에 다녀와서 할 일을 등록 : enqueue(Callback)

            client.newCall(request).enqueue( object : Callback {

                override fun onFailure(call: Call, e: IOException) {
//                실패 : 서버 연결 자체를 실패. 응답이 오지 않았다.
//                    ex. 인터넷 끊김, 서버 접속 불가 등등 물리적 연결 실패
//                    ex. 비번 틀려서 로그인 실패 : 서버 연결 성공, 응답도 돌아왔는데 -> 그 내용만 실패.(물리적 실패 x)
                }

                override fun onResponse(call: Call, response: Response) {
//                    어떤 내용이던, 응답 자체는 잘 돌아온 경우. (그 내용은 성공/실패 일 수 있다)
//                    응답 : reponse  변수 > 응답의 본문 (body) 만 보자.

                    val bodyString = response.body!!.string()   // toString() 아님! string() 기능은 1회용.

//                    응답의 본문을 String으로 변환하면 JSON Encoding 적용된 상태.(한글 깨짐)
//                    JSONObject 객체로 응답본문String을 변환해주면, 한글이 복구됨.
//                    => UI에서도 JSONObject를 이용해서, 데이터 추출/ 실제 활용
                    val jsonObj = JSONObject(bodyString)

                    Log.d("서버응답", jsonObj.toString())
//                    실제 : handler 변수에 jsonObj 를 가지고 화면에서 어떻게 처리할지 계획이 들어와있다.
//                    (계획리 되어있을때만) 해당 계획을 실행하자

                    handler?.onResponse(jsonObj)
                }

            })
        }

        fun putRequestSignUp(email: String, pw: String, nickname:String, handler: JsonResponseHandler?) {
            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()
            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue( object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })
        }

//      중복 체크
        fun getRequestDuplicatedCheck( type: String, inputValue:String, handler: JsonResponseHandler? ) {

//            1) 어느 주소로 가야하는가? + 어떤 파라미터를 첨부하는가 도 주소에 같이 포함.
//              => 라이브러리의 도움을 받자.   HttpUrl 클래스(OkHttp 소속)
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
                .addEncodedQueryParameter("type", type)
                .addEncodedQueryParameter("value", inputValue)
                .build()

            val urlString = urlBuilder.toString()

            Log.d("중복확인", urlString)
            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()
//          3) Request 완성 > 서버에 호출, 응답을 화면에 넘기자.
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }

//      토큰은 ContextUtil클래스에서 getToken 함수로 꺼내올 수 있다.
//      토큰값 자체는 파라미터로 받아올 필요 없다. => ContextUtil을 불러다 사용하자.

        fun getRequestMyInfo(context: Context, handler : JsonResponseHandler? ) {
            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
                .build()

            val urlString = urlBuilder.toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header( "X-Http-Token", ContextUtil.getToken(context) )   // Context을 통해, 저장된 토큰을 받아와
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    Log.d("서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }

            })

        }
    }
}