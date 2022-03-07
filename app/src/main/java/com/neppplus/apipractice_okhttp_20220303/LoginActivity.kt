package com.neppplus.apipractice_okhttp_20220303

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.apipractice_okhttp_20220303.databinding.ActivityLoginBinding
import com.neppplus.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class LoginActivity :  BaseActivity() {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.btnSignUp.setOnClickListener {
            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)
        }

        binding.btnLogin.setOnClickListener {
            val inputId = binding.edtId.text.toString()
            val inputPw = binding.edtPassword.text.toString()
//            API서버에 아이디/비번을 보내서 실제로 회원인지 검사 => 로그인 시도

            ServerUtil.postRequestLogin(inputId, inputPw, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

//                    화면의 입장에서 로그인 결과를 받아서 처리할 코드
//                    서버에 다녀오고 실행 : 라이브러리가 자동을 백그라운드에서 돌도록 만들어 둔 코드.

                    val code = jsonObject.getInt("code")
                    if (code == 200) {
                        val dataObj = jsonObject.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")
                        runOnUiThread {
                            Toast.makeText(mContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                        }
                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                    }
                    else {
                        val message = jsonObject.getString("message")
//                        토스트 : UI 조작. => 백그라운드에서 UI를 건드리면
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            })
        }

    }

    override fun setValues() {

    }
}