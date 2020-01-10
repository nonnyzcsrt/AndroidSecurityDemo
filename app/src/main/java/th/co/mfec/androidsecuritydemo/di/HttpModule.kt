package com.example.natandroid.dev.di

import android.content.Context
import com.example.natandroid.dev.http.ReadJsonFromAssetInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import th.co.mfec.androidsecuritydemo.BuildConfig
import th.co.mfec.androidsecuritydemo.R
import th.co.mfec.androidsecuritydemo.common.BaseViewModel
import th.co.mfec.androidsecuritydemo.constant.AppConstant
import th.co.mfec.androidsecuritydemo.utils.NetworkHelper
import java.util.concurrent.TimeUnit

fun provideHttpModule() = module {
    factory { NetworkHelper(androidContext()) }
    factory { ReadJsonFromAssetInterceptor(androidContext()) }
    factory { provideOkHttpClient(get()) }
    factory { provideRetrofit(androidContext(), get(), get()) }

}

fun provideOkHttpClient(readJsonFromAssetInterceptor: ReadJsonFromAssetInterceptor): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder().apply {
        connectTimeout(60, TimeUnit.SECONDS)
        readTimeout(60, TimeUnit.SECONDS)
        writeTimeout(60, TimeUnit.SECONDS)
        addInterceptor(interceptor)
        if (AppConstant.IS_OPEN_STUB) {
            addInterceptor(readJsonFromAssetInterceptor)
        }
        addInterceptor { chain ->
            return@addInterceptor chain.proceed(chain.request())
        }
    }.build()
}

fun provideRetrofit(context: Context, okHttpClient: OkHttpClient,baseViewModel : BaseViewModel): Retrofit {
    return Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.serverUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
}

