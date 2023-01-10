package com.doing.androidx.mvp.base

abstract class BasePresenter<IView : BaseView> {

    protected var mView: IView? = null

    fun attach(view: IView) {
        mView = view
    }

    fun detach() {
        mView = null
    }
}