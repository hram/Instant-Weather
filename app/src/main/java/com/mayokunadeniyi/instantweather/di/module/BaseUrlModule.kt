package com.mayokunadeniyi.instantweather.di.module

import com.algolia.search.configuration.CallType
import com.algolia.search.configuration.RetryableHost
import com.mayokunadeniyi.instantweather.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class BaseUrlModule {

    @Provides
    @Named("BASE_URL")
    fun provideBaseurl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Named("SEARCH_HOSTS")
    fun provideSearchHosts(): List<RetryableHost> {
        return listOf(
            RetryableHost("${BuildConfig.ALGOLIA_APP_ID}-dsn.algolia.net", CallType.Read),
            RetryableHost("${BuildConfig.ALGOLIA_APP_ID}.algolia.net", CallType.Write),
            RetryableHost("${BuildConfig.ALGOLIA_APP_ID}-1.algolianet.com"),
            RetryableHost("${BuildConfig.ALGOLIA_APP_ID}-2.algolianet.com"),
            RetryableHost("${BuildConfig.ALGOLIA_APP_ID}-3.algolianet.com")
        )
    }
}