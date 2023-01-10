package com.doing.androidx.presenter

import com.doing.androidx.model.UserInfo
import com.doing.androidx.view.MainContact

class MainPresenter : MainContact.IMainPresenter() {
    override fun getUserInfo() {
        val view : MainContact.IMainView = mView ?: return

        val user = UserInfo("", 1, "")
        view.onUserInfoGet(user)
    }
}