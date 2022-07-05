package com.hp.printercontro.utils

import android.content.Context
import com.onesignal.OneSignal
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

class OneSignalWrapper @AssistedInject constructor(
    @ApplicationContext context: Context,
    @Named("one_signal_id") appId: String,
    @Assisted("google_advertising_id") gAdId: String,
    @Assisted("apps_flyer_data") private val app: MutableMap<String, Any>?,
    @Assisted("deep_link") private val deep: String
) {
    init {
        OneSignal.initWithContext(context)
        OneSignal.setAppId(appId)
        OneSignal.setExternalUserId(gAdId)
    }

    fun send() {
        val campaign = app?.get("campaign").toString()

        if (campaign == "null" && (deep == "null")) {
            OneSignal.sendTag("key2", "organic")
        } else if (deep != "null") {
            OneSignal.sendTag("key2", deep.replace("myapp://", "").substringBefore("/"))
        } else if (campaign != "null") {
            OneSignal.sendTag("key2", campaign.substringBefore("_"))
        }
    }
}