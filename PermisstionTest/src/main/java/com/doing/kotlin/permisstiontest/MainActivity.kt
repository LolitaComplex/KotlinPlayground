package com.doing.kotlin.permisstiontest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBtnPermissionDispatcher.setOnClickListener {
            startActivity<PermissionDispatcherActivity>()
        }

        mBtnRxPermission.setOnClickListener {
            startActivity<RxPermissionActivity>()
        }

        mBtnEasyPermission.setOnClickListener {
            startActivity<EasyPermissionActivity>()
        }

        mBtnAndPermission.setOnClickListener {
            startActivity<AndPermissionActivity>()
        }

        mBtnPermission4M.setOnClickListener {
            startActivity<Permissions4MActivity>()
        }
    }
}
