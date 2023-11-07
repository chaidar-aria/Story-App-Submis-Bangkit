package com.chaidar.storyappsubmis.frontend.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chaidar.storyappsubmis.R
import com.chaidar.storyappsubmis.backend.api.ApiConfig
import com.chaidar.storyappsubmis.backend.data.UserPreference
import com.chaidar.storyappsubmis.backend.data.dataStore
import com.chaidar.storyappsubmis.backend.response.ListStoryItem
import com.chaidar.storyappsubmis.databinding.ActivityMainBinding
import com.chaidar.storyappsubmis.frontend.ViewModelFactory
import com.chaidar.storyappsubmis.frontend.login.LoginActivity
import com.chaidar.storyappsubmis.frontend.profile.ProfileActivity
import com.chaidar.storyappsubmis.frontend.register.RegisterActivity
import com.chaidar.storyappsubmis.frontend.settings.SettingsActivity
import kotlinx.coroutines.flow.first

class MainActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<MainViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
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
                    mainViewModel.logout()
                    true
                }

                else -> false
            }
        }
        mainViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                UserPreference.setToken(user.tokenAuth)
                Log.d("INI-TOKEN-ISI", "Ini token tadi: ${user.tokenAuth}")
            } else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        setupView()
        setupAction()
    }

    private fun setupView() {
        mainViewModel.getStories()
    }

    private fun setupAction() {
        mainViewModel.listStory.observe(this) {
            recycleViewSetup(it)
        }
    }

    private fun recycleViewSetup(list: List<ListStoryItem>) {
        with(binding) {
            val manager = LinearLayoutManager(this@MainActivity)
            recyclerView.apply {
                adapter = MainAdapter(list)
                layoutManager = manager
            }
        }
    }
}