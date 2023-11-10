package com.chaidar.storyappsubmis.frontend.detail

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.chaidar.storyappsubmis.R
import com.chaidar.storyappsubmis.backend.api.ApiConfig
import com.chaidar.storyappsubmis.backend.response.GetStoryResponse
import com.chaidar.storyappsubmis.backend.response.Story
import com.chaidar.storyappsubmis.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getStringExtra("userId") ?: ""
        val title = intent.getStringExtra("title") ?: ""
        val description = intent.getStringExtra("content") ?: ""
        val photoUrl = intent.getStringExtra("photoUrl") ?: ""

        if (isOnline()) {
            // Jika online, panggil API
            val service = ApiConfig.getService().getDetailStory(userId)
            service.enqueue(object : Callback<GetStoryResponse> {
                override fun onResponse(
                    call: Call<GetStoryResponse>,
                    response: Response<GetStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val detailResponse = response.body()
                        setupView(
                            detailResponse
                        )
                        Log.d("INI-DETAIL-STORY", detailResponse?.message.toString())
                        Log.d("INI-DETAIL-STORY-ONLINE", "title: ${title}, Deskripsi: ${description}")

                    } else {
                        // Handle kesalahan saat mengambil data dari server
                    }
                }

                override fun onFailure(call: Call<GetStoryResponse>, t: Throwable) {
                    Log.e("INI-DETAIL-STORY", "onFailure: Gagal")
                }
            })
        } else {
            Log.d("INI-DETAIL-STORY-OFFLINE", "title: ${title}, Deskripsi: ${description}")
            with(binding) {
                Glide.with(this@DetailActivity).load(photoUrl)
                    .into(imageViewDetail)
                textViewJudulDetail.text = title
                textViewIsiDetail.text = description
            }
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    private fun setupView(detailResponse: GetStoryResponse?) {
        with(binding) {
            Glide.with(this@DetailActivity).load(detailResponse?.story?.photoUrl)
                .into(imageViewDetail)
            textViewJudulDetail.text = detailResponse?.story?.name
            textViewIsiDetail.text = detailResponse?.story?.description
        }
    }
}