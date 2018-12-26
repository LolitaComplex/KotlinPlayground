package com.doing.httptest.manager

import java.io.Closeable
import java.io.IOException

object IOUtils {

    fun close(stream: Closeable?) {
        if (stream !== null) {
            try { stream.close() } catch (e: IOException) {}
        }
    }
}