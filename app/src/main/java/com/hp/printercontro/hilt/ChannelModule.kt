package com.hp.printercontro.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.Channel
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChannelModule {

    @Singleton
    @Provides
    fun provideAppsChannel(): Channel<MutableMap<String, Any>?> {
        return Channel(capacity = Channel.CONFLATED)
    }

    @Singleton
    @Provides
    fun provideDeepChannel(): Channel<String> {
        return Channel(capacity = Channel.CONFLATED)
    }
}