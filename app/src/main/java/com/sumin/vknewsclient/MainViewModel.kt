package com.sumin.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sumin.vknewsclient.domain.FeedPost
import com.sumin.vknewsclient.domain.StatisticItem
import java.util.Collections.replaceAll

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(10){
            add(
                FeedPost(it)
            )
        }

    }

    private val _feedPosts =MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts : LiveData<List<FeedPost>> get() = _feedPosts

    fun updateCount(feedPost: FeedPost ,item: StatisticItem){
        val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
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
        _feedPosts.value = oldPosts.apply {
            replaceAll{
                if( it.id == newFeedPost.id ){
                    newFeedPost
                }else{
                    it
                }
            }
        }
    }


    fun remove(feedPost: FeedPost){
        val oldPosts = feedPosts.value?.toMutableList() ?: mutableListOf()
        oldPosts.remove(feedPost)
        _feedPosts.value = oldPosts
    }
}