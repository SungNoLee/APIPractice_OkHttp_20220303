package com.neppplus.apipractice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

abstract class BaseActivity : AppCompatActivity() {
//  Context

//    미리 nContext 변수에 화면의 this를 담아두고 => 모든
    val mContext = this
//
    abstract fun setupEvents()
    abstract fun setValues()
}