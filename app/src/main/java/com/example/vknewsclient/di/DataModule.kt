package com.example.vknewsclient.di

import android.content.Context
import com.example.vknewsclient.data.network.ApiFactory
import com.example.vknewsclient.data.network.ApiService
import com.example.vknewsclient.data.repository.NewsFeedRepositoryImpl
import com.example.vknewsclient.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindRepository(impl : NewsFeedRepositoryImpl):NewsFeedRepository

    companion object{

        @Provides
        fun provideApiService():ApiService{
            return ApiFactory.apiService
        }

        @Provides
        fun provideStorage(context: Context): VKPreferencesKeyValueStorage{
            return VKPreferencesKeyValueStorage(context)
        }
    }
}