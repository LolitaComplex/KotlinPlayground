package com.doing.kotlin.permisstiontest

import android.Manifest
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.doing.kotlin.permisstiontest.dialog.NormalDialog
import kotlinx.android.synthetic.main.activity_permission_dispatcher.*
import permissions.dispatcher.*

@RuntimePermissions
class PermissionDispatcherActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_dispatcher)

        mBtnPdCamera.setOnClickListener(this::clickPdCamera)
        mBtnPdMicrophone.setOnClickListener(this::clickPdMicrophone)
        mBtnPdSdcard.setOnClickListener(this::clickPkSdcard)
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun clickPdCamera(view: View) {
        mBtnPdCamera.setText("相机权限申请完成")
        mBtnPdCamera.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    fun clickPdMicrophone(view: View) {
        mBtnPdCamera.setText("麦克风权限申请完成")
        mBtnPdCamera.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun clickPkSdcard(view: View) {
        mBtnPdCamera.setText("sd卡文件读写权限申请完成")
        mBtnPdCamera.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showCameraDialog(request: PermissionRequest) {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用CAMERA权限，下一步继续请求权限",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> request.proceed() }
        dialog.mNegativeListener = {_, _-> request.cancel() }
        dialog.show(supportFragmentManager)
    }

    @OnShowRationale(Manifest.permission.RECORD_AUDIO)
    fun showMicphoneDialog(request: PermissionRequest) {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用RECORD_AUDIO权限，下一步继续请求权限",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> request.proceed() }
        dialog.mNegativeListener = {_, _-> request.cancel() }
        dialog.show(supportFragmentManager)
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showSdcardDialog(request: PermissionRequest) {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用WRITE_EXTERNAL_STORAGE与" +
                    "Manifest.permission.READ_EXTERNAL_STORAGE权限，下一步继续请求权限",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> request.proceed() }
        dialog.mNegativeListener = {_, _-> request.cancel() }
        dialog.show(supportFragmentManager)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        Toast.makeText(this, "拒绝了相机权限", Toast.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAsk() {
        Toast.makeText(this, "永远拒绝了相机权限", Toast.LENGTH_LONG).show()
    }

    @OnPermissionDenied(Manifest.permission.RECORD_AUDIO)
    fun onMicrophoneDenied() {
        Toast.makeText(this, "拒绝了麦克风权限", Toast.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(Manifest.permission.RECORD_AUDIO)
    fun onMicrophoneNeverAsk() {
        Toast.makeText(this, "永远拒绝了麦克风权限", Toast.LENGTH_LONG).show()
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onSdcardDenied() {
        Toast.makeText(this, "拒绝了sd卡读写权限", Toast.LENGTH_LONG).show()
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onSdcardNeverAsk() {
        Toast.makeText(this, "永远拒绝了sd卡读写权限", Toast.LENGTH_LONG).show()
    }
}