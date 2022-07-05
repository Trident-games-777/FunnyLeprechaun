package com.hp.printercontro.loaders

import android.content.Context
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class LocalLoader @Inject constructor(
    @ApplicationContext private val context: Context,
    @Named("apps_id") private val appsId: String,
    private val appsFlyerChannel: Channel<MutableMap<String, Any>?>,
    private val deepLinkChannel: Channel<String>
) {
    init {
        AppsFlyerLib.getInstance().init(
            appsId,
            object : AppsFlyerConversionListener {
                override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) {
                    appsFlyerChannel.trySend(p0)
                }

                override fun onConversionDataFail(p0: String?) {}

                override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {}

                override fun onAttributionFailure(p0: String?) {}
            },
            context
        )
        AppsFlyerLib.getInstance().start(context)
        AppLinkData.fetchDeferredAppLinkData(context) {
            deepLinkChannel.trySend(it?.targetUri.toString())
        }
    }
}