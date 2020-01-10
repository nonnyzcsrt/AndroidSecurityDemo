package th.co.mfec.androidsecuritydemo.ui

import android.os.Bundle
import android.os.StrictMode
import androidx.recyclerview.widget.LinearLayoutManager
import com.scottyab.rootbeer.RootBeer
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request
import th.co.mfec.androidsecuritydemo.Keys
import th.co.mfec.androidsecuritydemo.R
import th.co.mfec.androidsecuritydemo.utils.RootUtils
import th.co.mfec.androidsecuritydemo.utils.SecurityUtils
import javax.net.ssl.SSLPeerUnverifiedException


class MainActivity : BaseActivity() {

    var menu = arrayOf(
        "Integrity Checker",
        "Root Detection RootBeer",
        "Root Detection Fabric",
        "Certificate Pinning"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showSuccessDialog("", Keys.signatureKey())
        

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv_menu?.layoutManager = LinearLayoutManager(this)
        val adapter = MenuAdapter(this, menu, object : OnItemClickListener {
            override fun onItemClicked(menuTxt: String, position: Int) {
                when (position) {
                    0 -> initIntegrityChecker()
                    1 -> initRootDetect()
                    2 -> initRootDetectFabricMethod()
                    3 -> initCertificatePinning()
                }
            }

        })
        rv_menu.adapter = adapter
    }

    fun initIntegrityChecker() {
        if (SecurityUtils.executeIntegrityChecker()) {
            showSuccessDialog("Integrity Checker", "Integrity Checker Successful.")
        } else {
            // exitProcess(0)
            showErrorDialog("Integrity Checker", "Integrity Checker Fail.")
        }
    }

    fun initRootDetect() {
        val rootBeer = RootBeer(this)
        rootBeer.setLogging(false)
        if (rootBeer.isRooted) {
            showErrorDialog("Root Detection", "This device is Rooted.")
        } else {
            showSuccessDialog("Root Detection", "This device is not Root.")
        }
    }

    fun initRootDetectFabricMethod() {
        if (RootUtils.isRootedFromFabric()) {
            showErrorDialog("Root Detection Fabric", "This device is Rooted.")
        } else {
            showSuccessDialog("Root Detection Fabric", "This device is not Root.")
        }
    }

    fun initCertificatePinning() {

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            val hostname = "publicobject.com"
//            val certificatePinner = CertificatePinner.Builder()
//                .add(hostname, "sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=")
//                .build()

            val certificatePinner = CertificatePinner.Builder()
                .add("publicobject.com", "sha1/DmxUShsZuNiqPQsX2Oi9uv2sCnw=")
                .add("publicobject.com", "sha1/SXxoaOSEzPC6BgGmxAt/EAcsajw=")
                .add("publicobject.com", "sha1/blhOM3W9V/bVQhsWAcLYwPU6n24=")
                .add("publicobject.com", "sha1/T5x9IXmcrQ7YuQxXnxoCmeeQ84c=")
                .build();

            val client = OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .build()

            val request = Request.Builder()
                .url("https://$hostname")
                .build()
            client.newCall(request).execute()
        } catch (e: SSLPeerUnverifiedException) {
            e.printStackTrace()
            val newLine = System.getProperty("line.separator")
            val errorMsg =
                "SSLPeerUnverifiedException : " + newLine + e.message.toString()
            showErrorDialog("Certificate Pinning", errorMsg)
        }

    }
}
