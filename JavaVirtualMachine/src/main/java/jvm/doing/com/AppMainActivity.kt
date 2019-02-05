package jvm.doing.com

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AppMainActivity : AppCompatActivity() {

    companion object {
        const val _1MB = 1024 * 1024
        const val _10MB = 10 * _1MB
        const val _100MB = 100 * _1MB

        lateinit var mAllocation1: ByteArray
        lateinit var mAllocation2: ByteArray
        lateinit var mAllocation3: ByteArray
        lateinit var mAllocation4: ByteArray
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAllocation1 = ByteArray(_100MB)
//        mAllocation2 = ByteArray(_100MB)
//        mAllocation = ByteArray(_100MB)
//        mAllocation1 = ByteArray(_100MB)
    }
}
