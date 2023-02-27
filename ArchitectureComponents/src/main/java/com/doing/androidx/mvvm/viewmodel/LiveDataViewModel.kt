package com.doing.androidx.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doing.androidx.mvvm.model.User

class LiveDataViewModel : ViewModel() {

    private val mutableLiveData by lazy {
        MutableLiveData<User>()
    }

    fun refreshUserInfo(): MutableLiveData<User> {
        return mutableLiveData.apply {
            postValue(User().apply {
                nick = "Joker"
                userName = "Archer"
                sex = 1
            })
        }
    }
}