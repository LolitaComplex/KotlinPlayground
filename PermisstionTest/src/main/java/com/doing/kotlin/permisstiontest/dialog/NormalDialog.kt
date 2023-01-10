package com.doing.kotlin.permisstiontest.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.annotation.ColorInt
import androidx.core.app.DialogFragment
import androidx.core.app.FragmentManager
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.TextView


class NormalDialog : DialogFragment(){

    private var mTitle: String? = null
    private var mMessage: String? = null
    private var mNavigationText: String? = null
    private lateinit var mPositiveText: String

    @ColorInt
    private var mButtonTextColor = Color.DKGRAY

    var mNegativeListener: ((dialog: DialogInterface , witch: Int) -> Unit)? = null
    var mPositiveListener: ((dialog: DialogInterface , witch: Int) -> Unit)? = null

    companion object {
        fun newInstance(title: String?, message: String?, positiveText: String): NormalDialog {
            return NormalDialog().apply {
                init(title, message, positiveText, "")
            }
        }

        fun newInstance(title: String?, message: String?, positiveText: String, navigationText: String):
                NormalDialog {
            return NormalDialog().apply {
                init(title, message, positiveText, navigationText)
            }
        }
    }

    private fun init(title: String?, message: String?, positiveText: String, navigationText: String?) {
        this.mTitle = title
        this.mMessage = message
        this.mPositiveText = positiveText
        this.mNavigationText = navigationText
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity as Context).apply {
            mTitle?.let {
                setTitle(mTitle)
            }

            mMessage?.let {
                setMessage(mMessage)
            }

            if (!TextUtils.isEmpty(mNavigationText) && mNegativeListener != null) {
                setNegativeButton(mNavigationText, mNegativeListener)
            }

            mPositiveListener?.let{
                setPositiveButton(mPositiveText, mPositiveListener)
            }
        }.create()
    }

    private fun getOutTag(): String {
        return this.javaClass.simpleName + " : " + dialog?.hashCode()
    }

    fun show(manager: FragmentManager?) {
        if (!isVisible) {
            super.show(manager, getOutTag())
        }
    }

    override fun dismiss() {
        if (isVisible) {
            super.dismiss()
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        super.show(manager, tag)
        Handler().post {
            val alert = AlertDialog::class.java.getDeclaredField("mAlert")
            alert.isAccessible = true
            val alertController = alert.get(dialog)
            var btnField = alertController.javaClass.getDeclaredField("mButtonPositive")
            btnField.isAccessible = true
            val btnPositive = btnField.get(alertController) as TextView
            btnPositive.setTextColor(mButtonTextColor)
            btnField = alertController.javaClass.getDeclaredField("mButtonNegative")
            btnField.isAccessible = true
            val btnNegative = btnField.get(alertController) as TextView
            btnNegative.setTextColor(mButtonTextColor)
        }
    }
}