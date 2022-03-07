package com.neppplus.apipractice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.apipractice_okhttp_20220303.databinding.ActivityMainBinding
import com.neppplus.apipractice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    override fun setupEvents() {

    }

    override fun setValues() {
        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {
                val dataObj = jsonObject.getJSONObject("data")
                val userObj = dataObj.getJSONObject("user")
                val nickname = userObj.getString("nick_name")
                runOnUiThread {
                    binding.txtLoginUserName.text = nickname
                }
            }

        })

        }
    }

}