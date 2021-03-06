package com.doing.httptest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnOkHttp.setOnClickListener { startActivity<OkHttpActivity>() }
        mBtnRetrofit.setOnClickListener { startActivity<RetrofitActivity>() }
    }
}