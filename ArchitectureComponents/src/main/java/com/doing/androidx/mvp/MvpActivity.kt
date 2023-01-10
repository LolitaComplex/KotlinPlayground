package com.doing.androidx.mvp

import android.os.Bundle
import android.util.Log
import com.doing.androidx.R
import com.doing.androidx.mvp.base.MvpBaseActivity
import com.doing.androidx.mvp.model.UserInfo
import com.doing.androidx.mvp.presenter.MvpPresenter
import com.doing.androidx.mvp.view.MainContact

class MvpActivity : MvpBaseActivity<MvpPresenter>(), MainContact.IMainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp)

        mPresenter.getUserInfo()
    }

    override fun onUserInfoGet(user: UserInfo) {
        // 刷新View
        Log.d("Doing", "NickName: ${user.nick}")
    }

}
