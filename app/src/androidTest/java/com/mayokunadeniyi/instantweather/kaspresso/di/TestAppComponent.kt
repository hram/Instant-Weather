package com.mayokunadeniyi.instantweather.kaspresso.di

import android.app.Application
import com.mayokunadeniyi.instantweather.InstantWeatherApplication
import com.mayokunadeniyi.instantweather.di.component.AppComponent
import com.mayokunadeniyi.instantweather.di.module.AlgoliaModule
import com.mayokunadeniyi.instantweather.di.module.AppModule
import com.mayokunadeniyi.instantweather.di.module.DataSourcesModule
import com.mayokunadeniyi.instantweather.di.module.DatabaseModule
import com.mayokunadeniyi.instantweather.di.module.DispatcherModule
import com.mayokunadeniyi.instantweather.di.module.MainActivityModule
import com.mayokunadeniyi.instantweather.di.module.NetworkModule
import com.mayokunadeniyi.instantweather.di.module.RepositoryModule
import com.mayokunadeniyi.instantweather.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class, DispatcherModule::class, RepositoryModule::class, AlgoliaModule::class, MockBaseUrlModule::class,
        NetworkModule::class, DataSourcesModule::class, DatabaseModule::class, MainActivityModule::class, AppModule::class, ViewModelModule::class
    ]
)
interface TestAppComponent : AppComponent {

    override fun inject(application: InstantWeatherApplication)

    fun getMockWebServer(): MockWebServer

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestAppComponent
    }
}