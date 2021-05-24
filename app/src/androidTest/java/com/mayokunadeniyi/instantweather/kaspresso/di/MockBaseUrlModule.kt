package com.mayokunadeniyi.instantweather.kaspresso.di

import com.algolia.search.configuration.CallType
import com.algolia.search.configuration.RetryableHost
import com.infeez.mock.extensions.removeFirstAndLastSlashInUrl
import dagger.Module
import dagger.Provides
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Named
import javax.inject.Singleton


@Module
class MockBaseUrlModule {

    @Provides
    @Singleton
    fun provideMockServer(): MockWebServer {
        var mockWebServer: MockWebServer? = null
        val thread = Thread(Runnable {
            mockWebServer = MockWebServer()
            mockWebServer?.start()
        })
        thread.start()
        thread.join()
        return mockWebServer ?: throw NullPointerException()
    }

    @Provides
    @Named("BASE_URL")
    fun provideBaseurl(server: MockWebServer): String {
        var url = ""

        val t = Thread(Runnable {
            url = server.url("/").toString()
        })
        t.start()
        t.join()

        return url
    }

    @Provides
    @Named("SEARCH_HOSTS")
    fun provideSearchHosts(@Named("BASE_URL") baseUrl: String): List<RetryableHost> {
        return listOf(
            RetryableHost(baseUrl.substring(7).removeFirstAndLastSlashInUrl(), CallType.Read),
            RetryableHost(baseUrl.substring(7).removeFirstAndLastSlashInUrl(), CallType.Write)
        )
    }
}