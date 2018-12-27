package com.doing.httptest.manager

import android.app.DownloadManager
import android.util.Log
import java.io.InputStream
import java.io.OutputStream



fun InputStream.copyTo(out: OutputStream, bufferSize: Int, call: (maxLength: Long) -> Unit): Long{
    val buffer = ByteArray(bufferSize)
    var len = 0; var bytesCopied = 0L
    call(0)
    while (read(buffer).apply { len = this } >= 0) {
        out.write(buffer, 0, len)
        bytesCopied += len
        call(bytesCopied)
        Log.i(HttpDownloadManager.TAG, "MaxLength：$bytesCopied + Len：$len")
    }
    return bytesCopied
}