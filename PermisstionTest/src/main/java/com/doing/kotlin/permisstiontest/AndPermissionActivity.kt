package com.doing.kotlin.permisstiontest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.doing.kotlin.permisstiontest.dialog.NormalDialog
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.RequestExecutor
import kotlinx.android.synthetic.main.activity_and_permission.*
import java.io.*

class AndPermissionActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_and_permission)

        val runtime = AndPermission.with(this).runtime()
        mBtnApCamera.setOnClickListener {
            runtime
                .permission(Manifest.permission.CAMERA)
                .rationale { _, _, executor ->
                    showCameraDialog(executor)
                }.onGranted {
                    clickApCamera()
                }.onDenied {
                    if (AndPermission.hasAlwaysDeniedPermission(this, it)) {
                        runtime.setting().start()
                    }
                    onCameraNeverAsk()
                }.start()
        }

        mBtnApMicrophone.setOnClickListener {
            runtime
                .permission(Manifest.permission.RECORD_AUDIO)
                .rationale { _, _, executor ->
                    showMicrophoneDialog(executor)
                }.onGranted {
                    clickApMicrophone()
                }.onDenied {
                    if (AndPermission.hasAlwaysDeniedPermission(this, it)) {
                        runtime.setting().start()
                    }
                    onMicrophoneNeverAsk()
                }.start()
        }

        mBtnApSdcard.setOnClickListener {
            runtime
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale { _, _, executor ->
                    showSdcardDialog(executor)
                }.onGranted {
                    clickApSdcard()
                }.onDenied {
                    if (AndPermission.hasAlwaysDeniedPermission(this, it)) {
                        runtime.setting().start()
                    }
                    onSdcardNeverAsk()
                }.start()
        }

        mBtnApCamera.setTag(R.id.ClickTag, false)
        mBtnApMicrophone.setTag(R.id.ClickTag, false)
        mBtnApSdcard.setTag(R.id.ClickTag, false)

        val checkPermission = checkPermission(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        mTvPermissionAllOpen.visibility = if(checkPermission) View.VISIBLE else View.GONE
    }

    private fun clickApCamera() {
        mBtnApCamera.setText("相机权限申请完成")
        mBtnApCamera.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnApCamera.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun clickApMicrophone() {
        mBtnApMicrophone.setText("麦克风权限申请完成")
        mBtnApMicrophone.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnApMicrophone.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun clickApSdcard() {
        mBtnApSdcard.setText("sd卡文件读写权限申请完成")
        mBtnApSdcard.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnApSdcard.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun showCameraDialog(executor: RequestExecutor) {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用CAMERA权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> executor.execute() }
        dialog.mNegativeListener = {_, _-> executor.cancel() }
        dialog.show(supportFragmentManager)
    }

    private fun showMicrophoneDialog(executor: RequestExecutor) {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用RECORD_AUDIO权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> executor.execute() }
        dialog.mNegativeListener = {_, _-> executor.cancel() }
        dialog.show(supportFragmentManager)
    }

    private fun showSdcardDialog(executor: RequestExecutor) {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用WRITE_EXTERNAL_STORAGE与" +
                    "Manifest.permission.READ_EXTERNAL_STORAGE权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> executor.execute() }
        dialog.mNegativeListener = {_, _-> executor.cancel() }
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
        val flag = getIsShowFlagFunc(arrayOf(mBtnApCamera,
            mBtnApMicrophone, mBtnApSdcard))
        mTvPermissionAllOpen.visibility = if(flag) View.VISIBLE else View.GONE
    }
}