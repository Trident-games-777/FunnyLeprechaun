package com.hp.printercontro.hilt

import com.hp.printercontro.utils.OneSignalWrapper
import com.hp.printercontro.utils.Url
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface UrlFactory {
    fun create(
        @Assisted("google_advertising_id") gAdId: String,
        @Assisted("apps_flyer_data") appsData: MutableMap<String, Any>?,
        @Assisted("deep_link") deepLink: String
    ): Url
}

@AssistedFactory
interface OneSignalFactory {
    fun create(
        @Assisted("google_advertising_id") gAdId: String,
        @Assisted("apps_flyer_data") appsData: MutableMap<String, Any>?,
        @Assisted("deep_link") deepLink: String
    ): OneSignalWrapper
}