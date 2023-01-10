package com.doing.androidx.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doing.androidx.mvvm.User

class LiveDataViewModel : ViewModel() {

    fun refreshUserInfo(): LiveData<User> {
        return MutableLiveData<User>().apply {
            postValue(User().apply {
                nick = "Joker"
                userName = "Archer"
                sex = 1
            })
        }
    }
}