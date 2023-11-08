package com.chaidar.storyappsubmis.frontend.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.chaidar.storyappsubmis.backend.api.ApiConfig
import com.chaidar.storyappsubmis.backend.data.UserModel
import com.chaidar.storyappsubmis.backend.data.UserPreference
import com.chaidar.storyappsubmis.backend.response.AllStoryResponse
import com.chaidar.storyappsubmis.backend.response.ListStoryItem
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    private val _listStory = MutableLiveData<List<ListStoryItem>>()
    val listStory: LiveData<List<ListStoryItem>> = _listStory

    private val _loadingScreen = MutableLiveData<Boolean>()
    val loadingScreen: LiveData<Boolean> = _loadingScreen


    fun getSession(): LiveData<UserModel> {
        return pref.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }

    fun getStories() {
        _loadingScreen.value = true
        val storiesResult = ApiConfig.getService().getStories()
        storiesResult.enqueue(object : Callback<AllStoryResponse> {
            override fun onResponse(
                call: Call<AllStoryResponse>,
                response: Response<AllStoryResponse>
            ) {
                if (response.isSuccessful) {
                    _loadingScreen.value = false
                    var responseBody = response.body()
                    _listStory.value = responseBody?.listStory ?: emptyList()
                    Log.d("INI-ISI-STORY", responseBody?.message.toString())
//                    Log.d("INI-TOKEN-ISI", "Ini token: ${getTokenMain().toString()}")
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = response.message()
                    val errorCode = response.code()

                    Log.e("INI-ISI-STORY", "onFailure1: HTTP $errorCode $errorMessage")

                    // Log the error body if available
                    if (!errorBody.isNullOrBlank()) {
                        Log.e("INI-ISI-STORY", "Error Body: $errorBody")
                    }
//                    Log.e("INI-ISI-STORY", "onFailure1: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                _loadingScreen.value = false
                Log.e("INI-ISI-STORY", "onFailure2: Gagal")
            }

        })
    }

}