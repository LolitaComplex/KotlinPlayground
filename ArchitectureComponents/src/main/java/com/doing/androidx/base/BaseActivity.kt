package com.doing.androidx.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

open class BaseActivity<IPresenter : BasePresenter<out BaseView>> : AppCompatActivity(), BaseView {

    protected lateinit var mPresenter: IPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val genericSuperclass = this.javaClass.genericSuperclass

        if (genericSuperclass is ParameterizedType) {
            val arguments: Array<Type> = genericSuperclass.actualTypeArguments
            if (arguments[0] is BasePresenter<*>) {
                try {
                    mPresenter = arguments[0].javaClass.newInstance() as IPresenter
                } catch (e: Exception) {
                }
            }
        }

//        mPresenter.attach(this)
    }

    override fun isAlive(): Boolean {
        return !isDestroyed && !isFinishing
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detach()
    }
}