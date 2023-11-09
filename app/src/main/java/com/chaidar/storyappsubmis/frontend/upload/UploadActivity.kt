package com.chaidar.storyappsubmis.frontend.upload

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.chaidar.storyappsubmis.R
import com.chaidar.storyappsubmis.backend.api.ApiConfig
import com.chaidar.storyappsubmis.backend.response.AddStoryResponse
import com.chaidar.storyappsubmis.databinding.ActivityUploadBinding
import com.chaidar.storyappsubmis.backend.utils.getImageUri
import com.chaidar.storyappsubmis.backend.utils.reduceFileImage
import com.chaidar.storyappsubmis.backend.utils.uriToFile
import com.chaidar.storyappsubmis.frontend.main.MainActivity
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UploadActivity : AppCompatActivity() {

    companion object {
        private const val REQUIRED_PERMISSION = android.Manifest.permission.CAMERA
    }

    private lateinit var binding: ActivityUploadBinding

    private var currentImageUri: Uri? = null


    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.cameraButton.setOnClickListener { startCamera() }
        binding.galleryButton.setOnClickListener { openGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val description = binding.edAddDescription.text.toString()
            showLoading(true)

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            val apiService = ApiConfig.getService()
            val successResponse = apiService.uploadImage(multipartBody, requestBody)

            successResponse.enqueue(object : Callback<AddStoryResponse> {
                override fun onResponse(
                    call: Call<AddStoryResponse>,
                    response: Response<AddStoryResponse>
                ) {
                    if (response.isSuccessful) {
                        val addStoryResponse = response.body()
                        addStoryResponse?.let {
                            if (it.error == false) {
                                // Upload success
                                Log.d("UPLOAD", "Upload Success: ${it.message}")
                                showToast("Upload Success")
                                val intent = Intent(this@UploadActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                // Upload failed with a server error
                                Log.d("UPLOAD", "Upload Failed: ${it.message}")
                                showToast("Upload Failed: ${it.message}")
                                hideLoading()
                            }
                        } ?: run {
                            // Response body is null
                            Log.e("UPLOAD", "Upload Failed: Response body is null")
                            showToast("Upload Failed: Response body is null")
                        }
                    } else {
                        // HTTP error
                        Log.e("UPLOAD", "HTTP Error: ${response.code()}")
                        showToast("Upload Failed: HTTP Error ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<AddStoryResponse>, t: Throwable) {
                    // Request failure
                    Log.e("UPLOAD", "Request Failure: ${t.message}")
                    showToast("Upload Failed: Request Failure")
                }
            })


        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun openGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.thumbnailUpload.setImageURI(it)
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun hideLoading() {
        binding.loadingProgressBar.visibility = View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}