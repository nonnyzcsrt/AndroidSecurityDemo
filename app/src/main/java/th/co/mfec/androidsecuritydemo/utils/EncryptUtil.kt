package th.co.mfec.androidsecuritydemo.utils

import android.util.Base64
import java.math.BigInteger
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.InvalidKeySpecException
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


/**
 * Created by Pornthep Jaroen-ngam on 3/25/2019 AD.
 */

object EncryptUtil {
    fun encryptBase64(input: String?): String {
        // This is base64 encoding, which is not an encryption
        return when {
            !input.isNullOrEmpty() -> Base64.encodeToString(input.toByteArray(), Base64.DEFAULT)
            else -> ""
        }
    }

    fun decryptBase64(input: String?): String {
        return when {
            !input.isNullOrEmpty() -> String(Base64.decode(input, Base64.DEFAULT))
            else -> ""
        }
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
    fun validatePassword(originalPassword: String, storedPassword: String): Boolean {
        val parts = storedPassword.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val iterations = Integer.parseInt(parts[0])
        val salt = fromHex(parts[1])
        val hash = fromHex(parts[2])

        val spec = PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.size * 8)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val testHash = skf.generateSecret(spec).encoded

        var diff = hash.size xor testHash.size
        var i = 0
        while (i < hash.size && i < testHash.size) {
            diff = diff or (hash[i].toInt() xor testHash[i].toInt())
            i++
        }
        return diff == 0
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeySpecException::class)
     fun generateStrongPasswordHash(password: String): String {
        val iterations = 1000
        val chars = password.toCharArray()
        val salt = getSalt()

        val spec = PBEKeySpec(chars, salt, iterations, 64 * 8)
        val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val hash = skf.generateSecret(spec).getEncoded()
        return iterations.toString() + ":" + toHex(salt) + ":" + toHex(hash)
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun getSalt(): ByteArray {
        val sr = SecureRandom.getInstance("SHA1PRNG")
        val salt = ByteArray(16)
        sr.nextBytes(salt)
        return salt
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun toHex(array: ByteArray): String {
        val bi = BigInteger(1, array)
        val hex = bi.toString(16)
        val paddingLength = array.size * 2 - hex.length
        return if (paddingLength > 0) {
            String.format("%0" + paddingLength + "d", 0) + hex
        } else {
            hex
        }
    }

    @Throws(NoSuchAlgorithmException::class)
    private fun fromHex(hex: String): ByteArray {
        val bytes = ByteArray(hex.length / 2)
        for (i in bytes.indices) {
            bytes[i] = Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16).toByte()
        }
        return bytes
    }
}