package com.ugurbuga.whereami


data class WAIConfig(
    val consoleLogEnabled: Boolean = false,
    val pushEnabled: Boolean = false,
    val toastEnabled: Boolean = false,
    val consoleLogLevel: WAILogLevel = WAILogLevel.BASIC,
) {
    fun isLogEnabled(): Boolean {
        return consoleLogEnabled || pushEnabled || toastEnabled
    }

}