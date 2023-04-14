package com.example.vknewsclient.di

import com.example.vknewsclient.ViewModelFactory
import com.example.vknewsclient.domain.entity.FeedPost
import com.example.vknewsclient.presentation.comments.CommentsViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {

    fun getCommentsViewModelFactory() : ViewModelFactory

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }

}