package com.doing.kotlin.permisstiontest

import android.Manifest
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_easy_permission.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

class EasyPermissionActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks{

    private companion object {
        private const val CAMERA_PERMISSION_CODE = 1009
        private const val MICROPHONE_PERMISSION_CODE = 1010
        private const val SDCARD_PERMISSION_CODE = 1011
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_permission)

        mBtnEpCamera.setOnClickListener {
            val request = PermissionRequest.Builder(this,
                CAMERA_PERMISSION_CODE, Manifest.permission.CAMERA)
                .setRationale("使用此功能需要使用CAMERA权限，不然无法使用该功能")
                .setPositiveButtonText("确定")
                .setNegativeButtonText("取消")
                .build()
            EasyPermissions.requestPermissions(request)
        }

        mBtnEpMicrophone.setOnClickListener {
            val request = PermissionRequest.Builder(this,
                MICROPHONE_PERMISSION_CODE, Manifest.permission.RECORD_AUDIO)
                .setRationale("使用此功能需要使用RECORD_AUDIO权限，不然无法使用该功能")
                .setPositiveButtonText("确定")
                .setNegativeButtonText("取消")
                .build()
            EasyPermissions.requestPermissions(request)
        }

        mBtnEpSdcard.setOnClickListener {
            val request = PermissionRequest.Builder(this,
                SDCARD_PERMISSION_CODE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setRationale("使用此功能需要使用WRITE_EXTERNAL_STORAGE与" +
                        "Manifest.permission.READ_EXTERNAL_STORAGE权限，不然无法使用该功能")
                .setPositiveButtonText("确定")
                .setNegativeButtonText("取消")
                .build()
            EasyPermissions.requestPermissions(request)
        }

        val hasPermissions = EasyPermissions.hasPermissions(
            this, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        mTvPermissionAllOpen.visibility = if(hasPermissions) View.VISIBLE else View.GONE

        mBtnEpCamera.setTag(R.id.ClickTag, false)
        mBtnEpMicrophone.setTag(R.id.ClickTag, false)
        mBtnEpSdcard.setTag(R.id.ClickTag, false)
        /**
         * 会在第一次拒绝权限后弹出此弹框
         */
//        EasyPermissions.requestPermissions(this, "申请照相、麦克风、读写SD卡三个权限", 1009,
//            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> onCameraNeverAsk()
            MICROPHONE_PERMISSION_CODE -> onMicrophoneNeverAsk()
            SDCARD_PERMISSION_CODE -> onSdcardNeverAsk()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            CAMERA_PERMISSION_CODE -> clickEpCamera()
            MICROPHONE_PERMISSION_CODE -> clickEpMicrophone()
            SDCARD_PERMISSION_CODE -> clickEpSdcard()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 这方法没用吧
     */
    @AfterPermissionGranted(1009)
    private fun onRequestPermission() {
        val hasPermissions = EasyPermissions.hasPermissions(
            this, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
        mTvPermissionAllOpen.visibility = if(hasPermissions) View.VISIBLE else View.GONE

        if (!hasPermissions) {
//            EasyPermissions.requestPermissions(this, "申请照相、麦克风、读写SD卡三个权限", 1009,
//                Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun clickEpCamera() {
        mBtnEpCamera.setText("相机权限申请完成")
        mBtnEpCamera.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnEpCamera.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun clickEpMicrophone() {
        mBtnEpMicrophone.setText("麦克风权限申请完成")
        mBtnEpMicrophone.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnEpMicrophone.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
    }

    private fun clickEpSdcard() {
        mBtnEpSdcard.setText("sd卡文件读写权限申请完成")
        mBtnEpSdcard.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        mBtnEpSdcard.setTag(R.id.ClickTag, true)
        isShowPermissionFinish()
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
        val flag = getIsShowFlagFunc(arrayOf(mBtnEpCamera,
            mBtnEpMicrophone, mBtnEpSdcard))
        mTvPermissionAllOpen.visibility = if(flag) View.VISIBLE else View.GONE
    }
}