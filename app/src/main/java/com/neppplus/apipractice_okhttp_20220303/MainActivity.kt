package com.neppplus.apipractice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.apipractice_okhttp_20220303.databinding.ActivityMainBinding
import com.neppplus.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()

    }

    fun setupEvents() {
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
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
//                        토스트 : UI 조작. => 백그라운드에서 UI를 건드리면
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            })
        }

    }

    fun setValues() {

    }
}