@file:Suppress("BlockingMethodInNonBlockingContext")

package com.hp.printercontro.view_model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.hp.printercontro.hilt.OneSignalFactory
import com.hp.printercontro.hilt.UrlFactory
import com.hp.printercontro.loaders.LocalLoader
import com.hp.printercontro.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LeprechaunViewModel @Inject constructor(
    /**
     * Needed for starting AppsFlyer
     */
    loader: LocalLoader,
    checker: Checker,
    @ApplicationContext context: Context,
    @Named("base_url") private val baseUrl: String,
    @Named("key") private val urlKey: String,
    private val urlFactory: UrlFactory,
    private val oneSignalFactory: OneSignalFactory,
    private val dataStore: DataStore<Preferences>,
    private val appsChannel: Channel<MutableMap<String, Any>?>,
    private val deepChannel: Channel<String>
) : ViewModel() {

    private val _state: MutableStateFlow<State<String>> = MutableStateFlow(Empty)
    val state: StateFlow<State<String>> = _state

    init {
        if (checker.good()) {
            viewModelScope.launch {
                val url = dataStore.data.map { it[stringPreferencesKey(urlKey)] }.first() ?: baseUrl
                if (url.contains(baseUrl)) {
                    val appsData = appsChannel.receive()
                    val deepLink = deepChannel.receive()
                    val gadid = withContext(Dispatchers.IO) {
                        AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
                    }
                    val createdUrl =
                        urlFactory.create(gadid, appsData, deepLink)
                            .fromString(baseUrl)
                    oneSignalFactory.create(gadid, appsData, deepLink).send()
                    _state.value = Loaded(createdUrl)
                } else {
                    _state.value = Loaded(url)
                }
            }
        } else {
            _state.value = Blocked
        }

    }

}