package dev.lstr.llevateclaro

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric


/**
 * Created by lesternr on 5/5/18.
 */
class App:Application(){

    override fun onCreate() {
        super.onCreate()

        initFabricCrashlytics()
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    private fun initFabricCrashlytics(){
        Fabric.with(this, Crashlytics())
    }
}