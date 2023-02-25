package com.doing.androidx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os
import android.view.Choreographer
import android.widget.Button
import com.doing.androidx.aidl.BigBitmapActivity
import com.doing.androidx.aspectj.MethodTrace
import com.doing.androidx.mvp.MvpActivity
import com.doing.androidx.mvvm.MvvmDataBindingActivity
import com.doing.androidx.mvvm.MvvmLiveDataActivity
import com.doing.androidx.postview.PostViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    @MethodTrace
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnMvp.setOnClickListener {
            startActivity(Intent(this, MvpActivity::class.java))
        }

        mBtnMvvmDatabinding.setOnClickListener {
            startActivity(Intent(this, MvvmDataBindingActivity::class.java))
        }

        mBtnMvvmLiveData.setOnClickListener {
            startActivity(Intent(this, MvvmLiveDataActivity::class.java))
        }

        mBtnMvvmPostView.setOnClickListener {
            startActivity(Intent(this, PostViewActivity::class.java))
        }

        mBtnBigBitmap.setOnClickListener {
            startActivity(Intent(this, BigBitmapActivity::class.java))
        }

        NetworkUtils.getNetStats(this)
    }
}