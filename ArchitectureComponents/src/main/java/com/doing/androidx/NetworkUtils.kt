package com.doing.androidx

import android.annotation.SuppressLint
import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.NetworkCapabilities
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import java.util.*

object NetworkUtils {

    @SuppressLint("HardwareIds")
    fun getNetStats(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        var netDataRx = 0L // 接收
        var netDataTx = 0L // 发送
        val service = context.getSystemService(
            Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
        val telephonyManager = context.getSystemService(
            Context.TELEPHONY_SERVICE) as TelephonyManager

        val networkStats = service.querySummary(
            NetworkCapabilities.TRANSPORT_WIFI, telephonyManager.subscriberId,
            getTimesMonthMorning(), System.currentTimeMillis())

        // 数据结构离散桶
        val bucket = NetworkStats.Bucket()
        while (networkStats.hasNextBucket()) {
            networkStats.getNextBucket(bucket)
            val uid = bucket.uid
            if (getUidByPackageName(context) == uid) {
                netDataRx += bucket.rxBytes
                netDataTx += bucket.txBytes
            }
        }

        Log.d("Doing", "AppNetUse rx: $netDataRx tx: $netDataTx")
    }

    // 获取当月第一天0点时间
    private fun getTimesMonthMorning(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DATE))
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        return calendar.timeInMillis
    }

    private fun getUidByPackageName(context: Context): Int {
        val manager = context.packageManager
        val packageInfo = manager.getPackageInfo(
            context.packageName,
            PackageManager.GET_ACTIVITIES
        )
        return packageInfo.applicationInfo.uid
    }
}