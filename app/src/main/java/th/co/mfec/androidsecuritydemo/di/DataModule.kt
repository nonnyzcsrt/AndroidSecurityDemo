package com.example.natandroid.dev.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import th.co.mfec.androidsecuritydemo.common.BaseViewModel
import th.co.mfec.androidsecuritydemo.database.AppDatabase
import th.co.mfec.androidsecuritydemo.utils.PreferenceUtils

fun provideDatabaseModule() = module {
    single { AppDatabase.getInstance(androidContext()) }
}

fun providePrefModule() = module {
    single{ PreferenceUtils.instance }
    single{ BaseViewModel() }
}

