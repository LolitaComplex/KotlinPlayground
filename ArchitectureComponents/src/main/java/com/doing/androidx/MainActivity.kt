package com.doing.androidx

import android.os.Bundle
import android.util.Log
import com.doing.androidx.base.BaseActivity
import com.doing.androidx.model.UserInfo
import com.doing.androidx.presenter.MainPresenter
import com.doing.androidx.view.MainContact

class MainActivity : BaseActivity<MainPresenter>(), MainContact.IMainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter.getUserInfo()
    }

    override fun onUserInfoGet(user: UserInfo) {
        // 刷新View
        Log.d("Doing", "NickName: ${user.nick}")
    }

}
