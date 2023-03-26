package com.sumin.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.PostComment
import com.sumin.vknewsclient.domain.StatisticItem
import com.sumin.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(
                FeedPost(it)
            )
        }
    }

    private val initialComments = mutableListOf<PostComment>().apply {
        repeat(10){
            add(
                PostComment(it)
            )
        }
    }

    private val initialState = HomeScreenState.FeedPosts( posts =  initialList)

    private val _screenState =MutableLiveData<HomeScreenState>(initialState)
    val screenState : LiveData<HomeScreenState> get() = _screenState

    private var savedState : HomeScreenState? = initialState
    fun showComments(feedPost: FeedPost){
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(comments = initialComments, feedPost = feedPost)
    }

    fun closeScreen(){
        _screenState.value = savedState
    }

    fun updateCount(feedPost: FeedPost ,item: StatisticItem){
        val currentState = screenState.value
        if(currentState !is HomeScreenState.FeedPosts) return
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
        _screenState.value = HomeScreenState.FeedPosts(posts = newPosts)
    }


    fun remove(feedPost: FeedPost){
        val currentState = screenState.value
        if(currentState !is HomeScreenState.FeedPosts) return
        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.FeedPosts(oldPosts)
    }
}