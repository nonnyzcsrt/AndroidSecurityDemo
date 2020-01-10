package th.co.mfec.androidsecuritydemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

interface NetworkMonitor {
    fun isNetworkConnect(): Boolean
    fun isGpsEnable(): Boolean
    fun isBluetoothEnable(): Boolean
    fun isWifiEnable(): Boolean
    fun isNetworkEnable(): Boolean
}

class NetworkHelper constructor(
        private val mContext: Context
) : NetworkMonitor {
    override fun isGpsEnable(): Boolean {
        return false
    }

    override fun isBluetoothEnable(): Boolean {
        return false
    }

    override fun isWifiEnable(): Boolean {
        return false
    }

    override fun isNetworkEnable(): Boolean {
        return false
    }

    override fun isNetworkConnect(): Boolean {
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }
}