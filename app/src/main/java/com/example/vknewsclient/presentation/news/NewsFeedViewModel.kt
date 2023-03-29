package com.example.vknewsclient.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem

class NewsFeedViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(
                FeedPost(it)
            )
        }
    }

    private val initialState = NewsFeedScreenState.FeedPosts( posts =  initialList)

    private val _screenState =MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState : LiveData<NewsFeedScreenState> get() = _screenState


    fun updateCount(feedPost: FeedPost ,item: StatisticItem){
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.FeedPosts) return
        val oldPosts = currentState.posts.toMutableList()
        val oldStatistic = feedPost.statistics
        val newStatistic = oldStatistic.toMutableList().apply {
            replaceAll{ oldItem ->
                if(oldItem.type == item.type){
                    oldItem.copy(count = oldItem.count + 1)
                }else{
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistic)
        val newPosts = oldPosts.apply {
            replaceAll{
                if( it.id == newFeedPost.id ){
                    newFeedPost
                }else{
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.FeedPosts(posts = newPosts)
    }


    fun remove(feedPost: FeedPost){
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.FeedPosts) return
        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.FeedPosts(oldPosts)
    }
}