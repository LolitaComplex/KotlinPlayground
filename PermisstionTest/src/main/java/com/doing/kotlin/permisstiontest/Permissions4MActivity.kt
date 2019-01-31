package com.doing.kotlin.permisstiontest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.doing.kotlin.permisstiontest.dialog.NormalDialog
import com.joker.annotation.PermissionsDenied
import com.joker.annotation.PermissionsGranted
import com.joker.annotation.PermissionsNonRationale
import com.joker.api.Permissions4M
import kotlinx.android.synthetic.main.activity_permissions_4m.*

class Permissions4MActivity : AppCompatActivity(){

    private companion object {
        private const val CAMERA_PERMISSION_CODE = 1009
        private const val MICROPHONE_PERMISSION_CODE = 1010
        private const val SDCARD_PERMISSION_CODE = 1011
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permissions_4m)


        mBtnP4mCamera.setOnClickListener {
            Permissions4M.get(this)
                .requestPermissions(Manifest.permission.CAMERA)
                .requestCodes(CAMERA_PERMISSION_CODE)
                .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                .request()
        }

        mBtnP4mMicrophone.setOnClickListener {
            Permissions4M.get(this)
                .requestPermissions(Manifest.permission.RECORD_AUDIO)
                .requestCodes(MICROPHONE_PERMISSION_CODE)
                .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                .request()
        }

        mBtnP4mSdcard.setOnClickListener {
            Permissions4M.get(this)
                .requestPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .requestCodes(SDCARD_PERMISSION_CODE)
                .requestPageType(Permissions4M.PageType.ANDROID_SETTING_PAGE)
                .request()
        }

        mBtnP4mCamera.setTag(R.id.ClickTag, false)
        mBtnP4mMicrophone.setTag(R.id.ClickTag, false)
        mBtnP4mSdcard.setTag(R.id.ClickTag, false)

        val checkPermission = checkPermission(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        mTvPermissionAllOpen.visibility = if(checkPermission) View.VISIBLE else View.GONE

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Permissions4M.onRequestPermissionsResult(this, requestCode, grantResults)
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
        val flag = getIsShowFlagFunc(arrayOf(mBtnP4mCamera,
            mBtnP4mMicrophone, mBtnP4mSdcard))
        mTvPermissionAllOpen.visibility = if(flag) View.VISIBLE else View.GONE
    }

    @PermissionsGranted(CAMERA_PERMISSION_CODE)
    fun clickRpCamera() {
        mBtnP4mCamera.setText("相机权限申请完成")
        mBtnP4mCamera.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnP4mCamera.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    @PermissionsGranted(MICROPHONE_PERMISSION_CODE)
    fun clickRpMicrophone() {
        mBtnP4mMicrophone.setText("麦克风权限申请完成")
        mBtnP4mMicrophone.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnP4mMicrophone.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    @PermissionsGranted(SDCARD_PERMISSION_CODE)
    fun clickRpSdcard() {
        mBtnP4mSdcard.setText("sd卡文件读写权限申请完成")
        mBtnP4mSdcard.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnP4mSdcard.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    @PermissionsDenied(CAMERA_PERMISSION_CODE)
    fun showCameraDialog() {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用CAMERA权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> /*this.finish()*/ }
        dialog.mNegativeListener = {_, _-> /*this.finish()*/ }
        dialog.show(supportFragmentManager)
    }

    @PermissionsDenied(MICROPHONE_PERMISSION_CODE)
    fun showMicrophoneDialog() {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用RECORD_AUDIO权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> /*this.finish() */}
        dialog.mNegativeListener = {_, _-> /*this.finish()*/ }
        dialog.show(supportFragmentManager)
    }

    @PermissionsDenied(SDCARD_PERMISSION_CODE)
    fun showSdcardDialog() {
        val dialog = NormalDialog.newInstance(
            "申请相机权限", "使用此功能需要使用WRITE_EXTERNAL_STORAGE与" +
                    "Manifest.permission.READ_EXTERNAL_STORAGE权限，不然无法使用该功能",
            "确定", "取消")
        dialog.mPositiveListener = {_, _-> /*this.finish()*/ }
        dialog.mNegativeListener = {_, _-> /*this.finish()*/ }
        dialog.show(supportFragmentManager)
    }

    @PermissionsNonRationale(MICROPHONE_PERMISSION_CODE)
    fun onMicrophoneNeverAsk(intent: Intent) {
        startActivity(intent)
        Toast.makeText(this, "永远拒绝了麦克风权限", Toast.LENGTH_LONG).show()
    }

    @PermissionsNonRationale(CAMERA_PERMISSION_CODE)
    fun onCameraNeverAsk(intent: Intent) {
        startActivity(intent)
        Toast.makeText(this, "永远拒绝了相机权限", Toast.LENGTH_LONG).show()
    }

    @PermissionsNonRationale(SDCARD_PERMISSION_CODE)
    fun onSdcardNeverAsk(intent: Intent) {
        startActivity(intent)
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