package com.doing.androidx.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.doing.androidx.R
import com.doing.androidx.databinding.ActivityMvvpLiveDataBinding
import com.doing.androidx.mvvm.viewmodel.LiveDataViewModel

class MvvmLiveDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvp_live_data)

        val binding: ActivityMvvpLiveDataBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_mvvp_live_data)

        val model = ViewModelProvider(this).get(LiveDataViewModel::class.java)
        val liveData = model.refreshUserInfo()
        liveData.observe(this) { user ->
            binding.user = user
        }

        binding.mEtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Doing", "LiveData Change: ${liveData.value?.userName}")
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }
}