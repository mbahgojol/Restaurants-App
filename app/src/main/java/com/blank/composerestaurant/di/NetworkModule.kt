package com.blank.composerestaurant.di

import android.app.Application
import android.content.Context
import com.blank.composerestaurant.BuildConfig
import com.blank.composerestaurant.data.Repository
import com.blank.composerestaurant.data.RepositoryImpl
import com.blank.composerestaurant.data.local.RestaurantDao
import com.blank.composerestaurant.data.remote.RestaurantApi
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    fun provideBaseUrl() = "https://restaurant-api.dicoding.dev/"

    @Provides
    fun provideOkHttpClient(cache: Cache, @ApplicationContext context: Context) =
        OkHttpClient.Builder().cache(cache).apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
                addInterceptor(
                    ChuckerInterceptor.Builder(context).build()
                )
            }
        }.readTimeout(25, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(300, TimeUnit.SECONDS).build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    fun provideApiRestaurant(retrofit: Retrofit) = retrofit.create<RestaurantApi>()

    @Provides
    fun provideRepository(
        service: RestaurantApi, dao: RestaurantDao
    ): Repository = RepositoryImpl(service, dao)
}