package com.doing.httptest.manager

import java.io.File

data class DownloadInfo(var mFileLength: Long, var mFileName: String, var mUrl: String,
                        var mDownloadFile: File?, var mDownloadProgress: Float
){

    constructor():this(0L, "", "", null, 0.0F)
}