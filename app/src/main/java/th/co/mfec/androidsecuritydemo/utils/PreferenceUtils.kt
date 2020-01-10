package th.co.mfec.androidsecuritydemo.utils

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class PreferenceUtils {

    val context by lazy { ApplicationContext.instant.context }
    private val PREF_NAME = "APP_SECURE_PREF"
    val prefs = securePref()

    fun securePref(): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        val sharedPreferences = EncryptedSharedPreferences.create(
            PREF_NAME,
            masterKeyAlias,
            context!!,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // use the shared preferences and editor as you normally would
        return sharedPreferences
    }

    companion object {
        val instance: PreferenceUtils by lazy { PreferenceUtils() }
    }

    var sid: String
        get() = prefs.getString("sid", "").toString()
        set(sid) {
            prefs.edit().putString("sid", sid).apply()
        }

}