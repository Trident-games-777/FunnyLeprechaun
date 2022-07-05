package com.hp.printercontro.utils

import android.content.Context
import androidx.core.net.toUri
import com.appsflyer.AppsFlyerLib
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*

class Url @AssistedInject constructor(
    @ApplicationContext private val context: Context,
    @Assisted("google_advertising_id") private val gAdId: String,
    @Assisted("apps_flyer_data") private val appsData: MutableMap<String, Any>?,
    @Assisted("deep_link") private val deepLink: String
) {

    fun fromString(str: String) = str.toUri().buildUpon().apply {
        appendQueryParameter(Constants.SECURE_GET_PARAMETR, Constants.SECURE_KEY)
        appendQueryParameter(Constants.DEV_TMZ_KEY, TimeZone.getDefault().id)
        appendQueryParameter(Constants.GADID_KEY, gAdId)
        appendQueryParameter(Constants.DEEPLINK_KEY, deepLink)
        appendQueryParameter(Constants.SOURCE_KEY, appsData?.get("media_source").toString())
        appendQueryParameter(
            Constants.AF_ID_KEY,
            AppsFlyerLib.getInstance().getAppsFlyerUID(context)
        )
        appendQueryParameter(Constants.ADSET_ID_KEY, appsData?.get("adset_id").toString())
        appendQueryParameter(Constants.CAMPAIGN_ID_KEY, appsData?.get("campaign_id").toString())
        appendQueryParameter(Constants.APP_CAMPAIGN_KEY, appsData?.get("campaign").toString())
        appendQueryParameter(Constants.ADSET_KEY, appsData?.get("adset").toString())
        appendQueryParameter(Constants.ADGROUP_KEY, appsData?.get("adgroup").toString())
        appendQueryParameter(Constants.ORIG_COST_KEY, appsData?.get("orig_cost").toString())
        appendQueryParameter(Constants.AF_SITEID_KEY, appsData?.get("af_siteid").toString())
    }.toString()
}