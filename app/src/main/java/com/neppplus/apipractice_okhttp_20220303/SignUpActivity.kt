package com.neppplus.apipractice_okhttp_20220303

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neppplus.apipractice_okhttp_20220303.databinding.ActivitySignUpBinding
import com.neppplus.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setValues()
        setupEvents()
    }

    override fun setupEvents() {
        binding.edtEmail.addTextChangedListener {
            Log.d("입력내용", it.toString())
//            내용이 한글자라도 변경되면 재검사 요구 문장.
            binding.txtEmailCheckResult.text = "중복 확인을 해주세요."
        }
        binding.btnEmailCheck.setOnClickListener {
//            입력 이메일 값 추출
            val inputEmail = binding.edtEmail.text.toString()
//            서버의 중복확인 기능(user_check - GET) API 활용 > ServerUtil에 함수 추가, 가져다 활용.
//            그 응답 code값에 따라 다은 문구 배치.
//            obect 는 익명 클래스
            ServerUtil.getRequestDuplicatedCheck("EMAIL", inputEmail, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")

                    runOnUiThread {
                        when(code) {
                            200 -> {
                                binding.txtEmailCheckResult.text = "사용해도 좋은 이메일입니다."
                            }
                            else -> {
                                binding.txtEmailCheckResult.text = "다른 이메일로 다시 시도해 주세요."
                            }
                        }
                    }
                }

            })
        }
        binding.btnSignUp.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPass = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

            ServerUtil.putRequestSignUp(inputEmail,inputPass,inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObject: JSONObject) {
//                        회원가입 성공/ 실패 분기
                        val code = jsonObject.getInt("code")
                        if (code == 200) {
                            val dataObj = jsonObject.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val nickname = userObj.getString("nick_name")
                            runOnUiThread {
                                Toast.makeText(mContext, "${nickname}님 가입을 축하합니다!", Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        } else {
                            val message = jsonObject.getString("message")
                            runOnUiThread {
                                Toast.makeText(mContext, "${message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }


            )
        }
    }

    override fun setValues() {
    }

}