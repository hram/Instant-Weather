package com.mayokunadeniyi.instantweather.di.module

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.mayokunadeniyi.instantweather.BuildConfig
import com.mayokunadeniyi.instantweather.utils.AppIdInterceptor
import com.mayokunadeniyi.instantweather.utils.LocationLiveData
import com.mayokunadeniyi.instantweather.utils.SharedPreferenceHelper
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by Mayokun Adeniyi on 1/18/21.
 */

@Module
open class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesHelper(context: Context): SharedPreferenceHelper {
        return SharedPreferenceHelper.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideLocationLiveData(context: Context): LocationLiveData {
        return LocationLiveData(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(appIdInterceptor: AppIdInterceptor, loggingInterceptor: HttpLoggingInterceptor, chuckInterceptor: ChuckInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(appIdInterceptor)
        builder.addInterceptor(loggingInterceptor)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(chuckInterceptor)
        }
        builder.readTimeout(60, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideAppIdInterceptor(): AppIdInterceptor {
        return AppIdInterceptor()
    }

    @Provides
    @Singleton
    fun provideChuckInterceptor(context: Context): ChuckInterceptor {
        return ChuckInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        client: Lazy<OkHttpClient>,
        converterFactory: GsonConverterFactory,
        @Named("BASE_URL") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client.get())
            .addConverterFactory(converterFactory).build()
    }
}
