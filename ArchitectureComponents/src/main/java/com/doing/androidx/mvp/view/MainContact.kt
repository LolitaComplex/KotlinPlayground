package com.doing.androidx.mvp.view

import com.doing.androidx.mvp.base.BasePresenter
import com.doing.androidx.mvp.base.BaseView
import com.doing.androidx.mvp.model.UserInfo

interface MainContact {

    interface IMainView : BaseView {
        fun onUserInfoGet(user: UserInfo)
    }

    abstract class IMainPresenter : BasePresenter<IMainView>() {
        abstract fun getUserInfo()
    }
}