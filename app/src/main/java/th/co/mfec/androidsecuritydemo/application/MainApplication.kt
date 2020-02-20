package th.co.mfec.androidsecuritydemo.application

import android.app.Application
import com.example.natandroid.dev.di.provideDatabaseModule
import com.example.natandroid.dev.di.provideHttpModule
import com.example.natandroid.dev.di.providePrefModule
import com.example.natandroid.dev.di.provideUiModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import th.co.mfec.androidsecuritydemo.BuildConfig
import timber.log.Timber


class MainApplication : Application() {

    companion object {
        lateinit var instance: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initKoin()
        initTimber()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            androidFileProperties()
            modules(
                listOf(
                    providePrefModule(),
                    provideHttpModule(),
                    provideDatabaseModule(),
                    provideUiModule()
                )
            )
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}