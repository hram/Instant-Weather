package com.mayokunadeniyi.instantweather.di.module

import com.algolia.instantsearch.helper.searcher.SearcherSingleIndex
import com.algolia.search.client.ClientSearch
import com.algolia.search.client.Index
import com.algolia.search.configuration.CallType
import com.algolia.search.configuration.ConfigurationSearch
import com.algolia.search.configuration.RetryableHost
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import com.mayokunadeniyi.instantweather.BuildConfig
import com.mayokunadeniyi.instantweather.utils.ALGOLIA_INDEX_NAME
import dagger.Module
import dagger.Provides
import io.ktor.client.features.logging.LogLevel
import javax.inject.Named
import javax.inject.Singleton

@Module
class AlgoliaModule {

    @Provides
    @Singleton
    fun provideConfigurationSearch(@Named("SEARCH_HOSTS") hosts: List<RetryableHost>): ConfigurationSearch {
        return ConfigurationSearch(
            applicationID = ApplicationID(BuildConfig.ALGOLIA_APP_ID),
            apiKey = APIKey(BuildConfig.ALGOLIA_API_KEY),
            hosts = hosts,
            logLevel = LogLevel.ALL
        )
    }

    @Provides
    @Singleton
    fun provideClientSearch(configuration: ConfigurationSearch): ClientSearch {
        return ClientSearch(configuration)
    }

    @Provides
    @Singleton
    fun provideIndex(client: ClientSearch): Index {
        return client.initIndex(IndexName(ALGOLIA_INDEX_NAME))
    }

    @Provides
    @Singleton
    fun provideSearcherSingleIndex(index: Index): SearcherSingleIndex {
        return SearcherSingleIndex(index)
    }
}