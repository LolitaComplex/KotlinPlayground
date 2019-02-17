package jvm.doing.com

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import jvm.doing.com.proxy.Food
import jvm.doing.com.proxy.Person
import jvm.doing.com.proxy.PersonProxy

class ProxyActivity : AppCompatActivity() {

    companion object {
        const val TAG  = "ProxyActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_proxy)

        val feeling = PersonProxy().bind(Person()).eat(Food("牛肉双层汉堡"))
        Log.d(TAG, feeling)
    }
}