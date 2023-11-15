package com.chaidar.storyappsubmis.backend.data.repository

import ApiService
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.chaidar.storyappsubmis.backend.database.StoryDatabase
import com.chaidar.storyappsubmis.backend.response.AllStoryResponse
import com.chaidar.storyappsubmis.backend.response.ListStoryItem

class StoryRepository (

    private val storyDatabase: StoryDatabase,
    private val apiService: ApiService
){

    fun getStory(): LiveData<PagingData<ListStoryItem>>{
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
//            remoteMediator = StoryRemoteMediator(storyDatabase, apiService),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
//                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

}