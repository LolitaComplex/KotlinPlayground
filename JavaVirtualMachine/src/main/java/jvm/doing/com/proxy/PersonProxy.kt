package jvm.doing.com.proxy

import android.util.Log
import jvm.doing.com.ProxyActivity
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class PersonProxy : InvocationHandler {

    private lateinit var mPerson: Human

    fun bind(person: Person): Human {
        mPerson = person
        return Proxy.newProxyInstance(person.javaClass.classLoader, Person::class.java.interfaces, this) as Human
    }

    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
        Log.d(ProxyActivity.TAG, "你他妈吃了吗？")
        return method.invoke(mPerson, args?.get(0))
    }
}