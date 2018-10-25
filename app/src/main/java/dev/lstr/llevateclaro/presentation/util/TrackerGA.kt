package dev.lstr.llevateclaro.presentation.util

import android.content.Context
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.firebase.analytics.FirebaseAnalytics
import dev.lstr.llevateclaro.BuildConfig
import android.os.Bundle
import dev.lstr.llevateclaro.data.model.UserE


/**
 * Created by lesternr on 5/6/18.
 */
class TrackerGA(context: Context) {
    var mTracker: Tracker? = null
    var mTrackerFB: FirebaseAnalytics? = null

    companion object {
        private var instance: TrackerGA? = null

        fun getInstance(context: Context): TrackerGA{
            if(instance == null){
                instance = TrackerGA(context)
            }
            return instance!!
        }
    }

    init {
        if (mTracker == null){
            val analytics = GoogleAnalytics.getInstance(context)
            mTracker = analytics.newTracker(BuildConfig.GA_ID)
        }
        if (mTrackerFB == null){
            mTrackerFB = FirebaseAnalytics.getInstance(context)
        }
    }

    fun registerScreen(name: String) {
        mTracker!!.setScreenName("" + name)
        mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
    }

    fun registerEvent(category: String, action: String, label: String) {
        mTracker!!.send(HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build())

        val bundle = Bundle()
        if(label != ""){
            bundle.putString(action, label)
        }else{
            bundle.putString("result", action)
        }
        mTrackerFB!!.logEvent(category, bundle)
    }

    fun setUserInfo(user: UserE){
        mTrackerFB!!.setUserId(user.email)
        mTracker!!.setClientId(user.email)
    }
}