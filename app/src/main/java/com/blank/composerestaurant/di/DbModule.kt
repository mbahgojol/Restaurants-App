package com.blank.composerestaurant.di

import android.content.Context
import androidx.room.Room
import com.blank.composerestaurant.data.local.AppDb
import com.blank.composerestaurant.data.local.SharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun providePref(@ApplicationContext appContext: Context): SharedPref = SharedPref(appContext)

    @Provides
    fun provideDb(@ApplicationContext context: Context): AppDb =
        Room.databaseBuilder(context, AppDb::class.java, "resto").allowMainThreadQueries().build()

    @Provides
    fun provideDaoRestaurant(appDb: AppDb) = appDb.daoResto()
}