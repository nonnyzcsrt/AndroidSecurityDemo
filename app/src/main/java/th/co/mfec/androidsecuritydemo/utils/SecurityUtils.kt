package th.co.mfec.androidsecuritydemo.utils

import android.content.pm.PackageManager
import android.os.Build
import th.co.mfec.androidsecuritydemo.application.MainApplication
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object SecurityUtils {
    val context by lazy { ApplicationContext.instant.context ?: MainApplication.instance }

    //Generate From EncryptUtil.generateStrongPasswordHash([SHA Keystore]) : Example in 'SignatureKeyGenerator.kt'
    private val arg = "1000:8692efde97c0369e6181a8a2626c90f8:870f1a6c016753c8e9a48f7cf6816aff1e6d896ed93e7055d14b2e2aee32b83e92dceda9862c338caba44a6fcf4193184fee8a0ad8144d33b511fe8876145428"

    @Throws(NoSuchAlgorithmException::class)
    private fun calcSHA1(signature: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA1")
        digest.update(signature)
        val signatureHash = digest.digest()
        return bytesToHex(signatureHash)
    }

    private fun bytesToHex(bytes: ByteArray): String {
        val hexArray = "0123456789ABCDEF".toCharArray()
        val hexChars = CharArray(bytes.size * 2)
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = hexArray[v.ushr(4)]
            hexChars[j * 2 + 1] = hexArray[v and 0x0F]
        }
        return String(hexChars)
    }

    private fun validateAppSignature(): Boolean {
        try {

            val appSignatures = getApplicationSignature()
            for (signature in appSignatures) {
                val currentSignature = signature
                val isValidatePass = EncryptUtil.validatePassword(currentSignature, arg)
                return isValidatePass
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return false
    }

    private fun getApplicationSignature(packageName: String = context.packageName): List<String> {
        val signatureList: List<String>
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                // New signature
                val sig = context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                ).signingInfo
                signatureList = if (sig.hasMultipleSigners()) {
                    // Send all with apkContentsSigners
                    sig.apkContentsSigners.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                } else {
                    // Send one with signingCertificateHistory
                    sig.signingCertificateHistory.map {
                        val digest = MessageDigest.getInstance("SHA")
                        digest.update(it.toByteArray())
                        bytesToHex(digest.digest())
                    }
                }
            } else {
                val sig = context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                ).signatures
                signatureList = sig.map {
                    val digest = MessageDigest.getInstance("SHA")
                    digest.update(it.toByteArray())
                    bytesToHex(digest.digest())
                }
            }

            return signatureList
        } catch (e: Exception) {
            // Handle error
        }
        return emptyList()
    }

    fun executeIntegrityChecker(): Boolean {
        return validateAppSignature()
    }

}