package com.doing.androidx.mvp.presenter

import com.doing.androidx.mvp.model.UserInfo
import com.doing.androidx.mvp.view.MainContact

class MvpPresenter : MainContact.IMainPresenter() {
    override fun getUserInfo() {
        val view : MainContact.IMainView = mView ?: return

        val user = UserInfo("", 1, "")
        view.onUserInfoGet(user)
    }
}