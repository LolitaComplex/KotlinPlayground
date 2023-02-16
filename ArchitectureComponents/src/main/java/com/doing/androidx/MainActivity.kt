package com.doing.androidx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.doing.androidx.mvp.MvpActivity
import com.doing.androidx.mvvm.MvvmDataBindingActivity
import com.doing.androidx.mvvm.MvvmLiveDataActivity
import com.doing.androidx.postview.PostViewActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.mBtnMvp).setOnClickListener {
            startActivity(Intent(this, MvpActivity::class.java))
        }

        findViewById<Button>(R.id.mBtnMvvmDatabinding).setOnClickListener {
            startActivity(Intent(this, MvvmDataBindingActivity::class.java))
        }

        findViewById<Button>(R.id.mBtnMvvmLiveData).setOnClickListener {
            startActivity(Intent(this, MvvmLiveDataActivity::class.java))
        }

        findViewById<Button>(R.id.mBtnMvvmPostView).setOnClickListener {
            startActivity(Intent(this, PostViewActivity::class.java))
        }
    }
}