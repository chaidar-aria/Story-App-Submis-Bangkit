package com.chaidar.storyappsubmis.frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chaidar.storyappsubmis.backend.data.UserPreference
import com.chaidar.storyappsubmis.frontend.login.LoginViewModel
import com.chaidar.storyappsubmis.frontend.main.MainViewModel
import com.chaidar.storyappsubmis.frontend.settings.SettingsViewModel


class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}