package th.co.mfec.androidsecuritydemo.utils

import android.content.Context

class ApplicationContext {
    var context: Context? = null

    companion object {
        val instant: ApplicationContext by lazy { ApplicationContext() }
    }
}