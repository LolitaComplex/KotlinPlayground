package com.doing.androidx.mvp.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class MvpBaseActivity<IPresenter : BasePresenter<out BaseView>> : AppCompatActivity(),
    BaseView {

    protected lateinit var mPresenter: IPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val genericSuperclass = this.javaClass.genericSuperclass

        if (genericSuperclass is ParameterizedType) {
            val arguments: Array<Type> = genericSuperclass.actualTypeArguments
            if (arguments[0] is Class<*>) {
                try {
                    val presenter = (arguments[0] as Class<*>).newInstance()
                    if (presenter is BasePresenter<*>) {
                        mPresenter = presenter as IPresenter
                    }
                } catch (e: Exception) {
                    Log.e("Doing", "Message: ${e.message}", e)
                }
            }
        }

//        mPresenter.attach()
    }

    override fun isAlive(): Boolean {
        return !isDestroyed && !isFinishing
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
    }
}