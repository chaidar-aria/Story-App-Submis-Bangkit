package com.chaidar.storyappsubmis.frontend.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaidar.storyappsubmis.R
import com.chaidar.storyappsubmis.backend.data.preferences.UserPreference
import com.chaidar.storyappsubmis.backend.data.preferences.dataStore
import com.chaidar.storyappsubmis.backend.response.ListStoryItem
import com.chaidar.storyappsubmis.databinding.ActivityMainBinding
import com.chaidar.storyappsubmis.frontend.ViewModelFactory
import com.chaidar.storyappsubmis.frontend.maps.MapsActivity
import com.chaidar.storyappsubmis.frontend.profile.ProfileActivity
import com.chaidar.storyappsubmis.frontend.settings.SettingsActivity
import com.chaidar.storyappsubmis.frontend.upload.UploadActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> {
        MainViewModel.ViewModelFactory(
            this
        )
    }

    private lateinit var pref: UserPreference

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menu_maps -> {
                    val intent = Intent(this, MapsActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_logout -> {
                    lifecycleScope.launch {
                        pref.logout()
                    }
                    true
                }

                else -> false
            }
        }


        setupView()
//        setupAction()
//        onResume()

        binding.fabTambahStory.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

    }


//    override fun onResume() {
//        super.onResume()
//        mainViewModel.getStories()
//    }

    private fun setupView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        recycleViewSetup()
    }


//    private fun loadingProgress(value: Boolean) {
//        binding.loadingProgressBar.isVisible = value
//        binding.recyclerView.isVisible = !value
//    }

    private fun recycleViewSetup() {
        val storyAdapter = MainAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { storyAdapter.retry() }
            )

            lifecycleScope.launchWhenCreated {
                mainViewModel.story.observe(this@MainActivity) {
                    storyAdapter.submitData(lifecycle, it)
                }
            }
        }
    }


}