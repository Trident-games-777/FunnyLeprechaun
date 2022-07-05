package com.hp.printercontro.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ConstantsModule {
    @Provides
    @Named("base_url")
    fun provideBaseUrl(): String = "https://fannyleprich.monster/funnys.php"

    @Provides
    @Named("apps_id")
    fun provideAppId(): String = "SwNPembhWhXVnHzsYuqZme"

    @Provides
    @Named("one_signal_id")
    fun provideOneSignalId(): String = "32044c80-6f33-49f6-b959-c8b662b20ff3"
}