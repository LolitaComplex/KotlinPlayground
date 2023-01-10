package com.doing.androidx.mvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.doing.androidx.R
import com.doing.androidx.databinding.ActivityMvvmDatabindingBinding
import com.doing.androidx.mvvm.viewmodel.MvvmViewModel

class MvvmDataBindingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_databinding)

        val binding : ActivityMvvmDatabindingBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_mvvm_databinding)

        val viewModel = MvvmViewModel()
        binding.viewModel = viewModel
        viewModel.refreshUserInfo()

        binding.mEtText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("Doing", "TextChange: ${viewModel.getUserInfo().get()?.userName}")
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }
}