package com.aaron.androidbaseapplication

/**
 * desc   : App 配置管理类
 */
object AppConfig {

    /**
     * 当前是否为 Debug 模式
     */
    val isDebug: Boolean
        get() = BuildConfig.DEBUG

    /**
     * 获取当前应用的包名
     */
    val packageName: String
        get() = BuildConfig.APPLICATION_ID

    /**
     * 获取当前应用的版本名
     */
    val versionName: String
        get() = BuildConfig.VERSION_NAME

    /**
     * 获取当前应用的版本码
     */
    val versionCode: Int
        get() = BuildConfig.VERSION_CODE

    /**
     * 获取当前应用的渠道名
     */
    val productFlavors: String
        get() = BuildConfig.FLAVOR
}