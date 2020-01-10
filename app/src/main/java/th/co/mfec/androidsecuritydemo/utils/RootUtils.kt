package th.co.mfec.androidsecuritydemo.utils

import android.os.Build
import java.io.File
import java.io.BufferedReader
import java.io.InputStreamReader


object RootUtils {
    private fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
    }

    fun isRootedFromFabric(): Boolean {
        val isEmulator = isEmulator()
        val buildTags = Build.TAGS
        if (!isEmulator && buildTags != null && buildTags.contains("test-keys")) {
            return true
        } else {
            var file = File("/system/app/Superuser.apk")
            if (file.exists()) {
                return true
            } else {
                file = File("/system/xbin/su")
                return !isEmulator && file.exists()
            }
        }
    }

    /** @author Kevin Kowalewski
     */
    val isDeviceRooted: Boolean
        get() = checkRootMethod1() || checkRootMethod2() || checkRootMethod3()

    private fun checkRootMethod1(): Boolean {
        val buildTags = android.os.Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRootMethod2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun checkRootMethod3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val input = BufferedReader(InputStreamReader(process!!.inputStream))
            input.readLine() != null
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }

}