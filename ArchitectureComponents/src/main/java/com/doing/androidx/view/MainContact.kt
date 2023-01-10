package com.doing.androidx.view

import com.doing.androidx.base.BasePresenter
import com.doing.androidx.base.BaseView
import com.doing.androidx.model.UserInfo

interface MainContact {

    interface IMainView : BaseView{
        fun onUserInfoGet(user: UserInfo)
    }

    abstract class IMainPresenter : BasePresenter<IMainView>() {
        abstract fun getUserInfo()
    }
}