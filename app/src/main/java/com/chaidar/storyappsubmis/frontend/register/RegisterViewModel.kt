package com.chaidar.storyappsubmis.frontend.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaidar.storyappsubmis.backend.api.ApiConfig
import com.chaidar.storyappsubmis.backend.response.RegisterResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {


    private val _registrationResult = MutableLiveData<Boolean>()

    private val _errorMessage = MutableLiveData<String>()

    val registrationResult: LiveData<Boolean>
        get() = _registrationResult

    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun register(email: String, password: String, name: String) {
        val registerProccess = ApiConfig.getService().register(name, email, password)
        registerProccess.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _registrationResult.value = true
                    responseBody?.let {
                        // Log the JSON response
                        Log.d("Registration Response", "JSON Response: $it")
                        Log.d("Registration Value", "Email: ${email}, Password: ${password}, Name: ${name}")
                    }
                } else {
                    _registrationResult.value = false
                    // Handle specific error message
                    val errorResponse = response.errorBody()?.string()
                    errorResponse?.let {
                        try {
                            val errorJson = JSONObject(it)
                            val errorMessage = errorJson.getString("message")
                            _errorMessage.value = errorMessage
                            // Log the error message
                            Log.e("Registration Error", "Error message: $errorMessage")
                            Log.d("Registration Value", "Email: ${email}, Password: ${password}, Name: ${name}")
                        } catch (e: JSONException) {
                            Log.e("Registration Error", "Error parsing error JSON: $it")
                            Log.d("Registration Value", "Email: ${email}, Password: ${password}, Name: ${name}")
                        }
                    }
                    Log.e("Registration Error", "Error1: ${response.code()}")
                    Log.d("Registration Value", "Email: ${email}, Password: ${password}, Name: ${name}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("Registration Error", "Error2: ${t.message}")
            }


        })
    }


}