package com.example.vknewsclient.di

import android.content.Context
import android.provider.ContactsContract.Data
import com.example.vknewsclient.ViewModelFactory
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun getViewModelFactory():ViewModelFactory

    fun getCommentsScreenComponent():CommentsScreenComponent.Factory

    @ApplicationScope
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }

}