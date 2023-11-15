package com.chaidar.storyappsubmis.frontend.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaidar.storyappsubmis.backend.api.ApiConfig
import com.chaidar.storyappsubmis.backend.data.preferences.UserPreference
import com.chaidar.storyappsubmis.backend.response.AllStoryResponse
import com.chaidar.storyappsubmis.backend.response.ListStoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val pref: UserPreference) : ViewModel() {

    private val _mapsStory = MutableLiveData<List<ListStoryItem>>()
    val mapsStory: MutableLiveData<List<ListStoryItem>> = _mapsStory

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = _snackBarText

    fun getMaps(){
        val mapsResult = ApiConfig.getService().getStoriesWithLocation()
        mapsResult.enqueue(object : Callback<AllStoryResponse> {
            override fun onResponse(
                call: Call<AllStoryResponse>,
                response: Response<AllStoryResponse>
            ) {
                if (response.isSuccessful){
                    val responseBody = response.body()
                    _mapsStory.value = responseBody?.listStory ?: emptyList()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = response.message()
                    val errorCode = response.code()

                    Log.e("INI-MAPS-STORY", "onFailure1: HTTP $errorCode $errorMessage")

                }
            }

            override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}