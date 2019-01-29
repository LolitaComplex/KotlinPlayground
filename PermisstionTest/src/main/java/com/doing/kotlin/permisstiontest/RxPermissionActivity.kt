package com.doing.kotlin.permisstiontest

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.doing.kotlin.permisstiontest.dialog.NormalDialog
import com.jakewharton.rxbinding3.view.clicks
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_rx_permission.*


class RxPermissionActivity: AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_permission)

        val rxPermissions = RxPermissions(this)
        mBtnRpCamera.clicks()
            .compose(rxPermissions.ensureEach(Manifest.permission.CAMERA))
            .subscribe { permission ->
                when {
                    permission.granted -> clickRpCamera()
                    permission.shouldShowRequestPermissionRationale -> showCameraDialog()
                    else -> onCameraNeverAsk()
                }
            }

        mBtnRpMicrophone.clicks()
            .compose(rxPermissions.ensureEach(Manifest.permission.RECORD_AUDIO))
            .subscribe { permission ->
                when {
                    permission.granted -> clickRpMicrophone()
                    permission.shouldShowRequestPermissionRationale -> showMicrophoneDialog()
                    else -> onMicrophoneNeverAsk()
                }
            }

        mBtnRpSdcard.clicks()
            .compose(rxPermissions.ensureEach(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            .subscribe{ permission ->
                when {
                    permission.granted -> clickRpSdcard()
                    permission.shouldShowRequestPermissionRationale -> showSdcardDialog()
                    else -> onSdcardNeverAsk()
                }
            }

        mBtnRpCamera.setTag(R.id.ClickTag, false)
        mBtnRpMicrophone.setTag(R.id.ClickTag, false)
        mBtnRpSdcard.setTag(R.id.ClickTag, false)

        val checkPermission = checkPermission(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        mTvPermissionAllOpen.visibility = if(checkPermission) View.VISIBLE else View.GONE
    }

    private fun isShowPermissionFinish() {
        val getIsShowFlagFunc = {views: Array<View> ->
            var result = false
            for (view in views) {
                val id = R.id.ClickTag
                if (!(view.getTag(id) as Boolean)) {
                    result = false
                    break
                }
                result = true
            }
            result
        }
        val flag = getIsShowFlagFunc(arrayOf(mBtnRpCamera,
            mBtnRpMicrophone, mBtnRpSdcard))
        mTvPermissionAllOpen.visibility = if(flag) View.VISIBLE else View.GONE
    }

    private fun clickRpCamera() {
        mBtnRpCamera.setText("相机权限申请完成")
        mBtnRpCamera.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnRpCamera.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun clickRpMicrophone() {
        mBtnRpMicrophone.setText("麦克风权限申请完成")
        mBtnRpMicrophone.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnRpMicrophone.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun clickRpSdcard() {
        mBtnRpSdcard.setText("sd卡文件读写权限申请完成")
        mBtnRpSdcard.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnRpSdcard.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun showCameraDialog() {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用CAMERA权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> this.finish() }
        dialog.mNegativeListener = {_, _-> this.finish() }
        dialog.show(supportFragmentManager)
    }

    private fun showMicrophoneDialog() {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用RECORD_AUDIO权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> this.finish() }
        dialog.mNegativeListener = {_, _-> this.finish() }
        dialog.show(supportFragmentManager)
    }

    private fun showSdcardDialog() {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用WRITE_EXTERNAL_STORAGE与" +
                    "Manifest.permission.READ_EXTERNAL_STORAGE权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> this.finish() }
        dialog.mNegativeListener = {_, _-> this.finish() }
        dialog.show(supportFragmentManager)
    }


    private fun onMicrophoneNeverAsk() {
        Toast.makeText(this, "永远拒绝了麦克风权限", Toast.LENGTH_LONG).show()
    }

    private fun onCameraNeverAsk() {
        Toast.makeText(this, "永远拒绝了相机权限", Toast.LENGTH_LONG).show()
    }

    private fun onSdcardNeverAsk() {
        Toast.makeText(this, "永远拒绝了sd卡读写权限", Toast.LENGTH_LONG).show()
    }

    private fun checkPermission(vararg permissions: String): Boolean {
        val lambda = {per: String ->
            ContextCompat.checkSelfPermission(this, per) == PackageManager.PERMISSION_GRANTED
        }
        for (permission in permissions) {
            if (!lambda(permission)) {
                return false
            }
        }
        return true
    }
}