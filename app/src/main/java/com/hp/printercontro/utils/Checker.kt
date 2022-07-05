package com.hp.printercontro.utils

import android.content.Context
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

class Checker @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dirs = listOf(
        "/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
        "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"
    )

    fun good(): Boolean {
        val r: Boolean = try {
            var result = true
            for (dir in dirs) {
                if (File(dir + "su").exists()) {
                    result = false
                    break
                }
            }
            result
        } catch (t: Throwable) {
            true
        }

        val a: Boolean =
            Settings.Global.getString(context.contentResolver, Settings.Global.ADB_ENABLED) != "1"

        return r && a
    }
}