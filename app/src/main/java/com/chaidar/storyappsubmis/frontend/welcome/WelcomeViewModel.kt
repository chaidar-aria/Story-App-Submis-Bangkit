package com.chaidar.storyappsubmis.frontend.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chaidar.storyappsubmis.backend.data.model.UserModel
import com.chaidar.storyappsubmis.backend.data.preferences.UserPreference

class WelcomeViewModel(private val pref: UserPreference) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return pref.getSession().asLiveData()
    }
}