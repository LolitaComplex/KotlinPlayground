package com.doing.androidx.mvvm.viewmodel

import androidx.databinding.ObservableField
import com.doing.androidx.mvp.model.UserInfo
import com.doing.androidx.mvvm.User

class MvvmViewModel {

    private val mUserField: ObservableField<User> = ObservableField()

    fun refreshUserInfo() {
        val user = User()
        user.userName = "Doing"
        user.nick = "滤水槽·热油"
        mUserField.set(user)
    }

    fun getUserInfo(): ObservableField<User> {
        return mUserField
    }
}