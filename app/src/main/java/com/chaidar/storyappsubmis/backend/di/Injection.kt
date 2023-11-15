package com.chaidar.storyappsubmis.backend.di

import android.content.Context
import com.chaidar.storyappsubmis.backend.api.ApiConfig
import com.chaidar.storyappsubmis.backend.data.repository.StoryRepository
import com.chaidar.storyappsubmis.backend.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getService()
        return StoryRepository(database, apiService)
    }
}