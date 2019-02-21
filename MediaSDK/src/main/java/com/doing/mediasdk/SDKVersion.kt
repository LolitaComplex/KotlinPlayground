package com.doing.mediasdk

object SDKVersion{

    //毫秒单位
    var MILLION_UNIT = 1000

    //自动播放阈值
    var VIDEO_SCREEN_PERCENT = 50

    var VIDEO_HEIGHT_PERCENT = 9 / 16.0f

    //自动播放条件
    enum class AutoPlaySetting {
        AUTO_PLAY_ONLY_WIFI,
        AUTO_PLAY_3G_4G_WIFI,
        AUTO_PLAY_NEVER
    }


    //素材类型
    val MATERIAL_IMAGE = "image"
    val MATERIAL_HTML = "html"

}