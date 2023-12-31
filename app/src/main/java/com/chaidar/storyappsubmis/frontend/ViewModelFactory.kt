package com.chaidar.storyappsubmis.frontend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chaidar.storyappsubmis.backend.data.preferences.UserPreference
import com.chaidar.storyappsubmis.frontend.login.LoginViewModel
import com.chaidar.storyappsubmis.frontend.maps.MapsViewModel
import com.chaidar.storyappsubmis.frontend.settings.SettingsViewModel
import com.chaidar.storyappsubmis.frontend.welcome.WelcomeViewModel


class ViewModelFactory(private val pref: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }

            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(pref) as T
            }

            modelClass.isAssignableFrom(MapsViewModel::class.java) -> {
                MapsViewModel(pref) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java)->{
                SettingsViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}