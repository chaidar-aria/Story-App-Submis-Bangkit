package com.chaidar.storyappsubmis.frontend.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.chaidar.storyappsubmis.backend.data.preferences.UserPreference
import com.chaidar.storyappsubmis.backend.data.preferences.dataStore
import com.chaidar.storyappsubmis.databinding.ActivityWelcomeBinding
import com.chaidar.storyappsubmis.frontend.ViewModelFactory
import com.chaidar.storyappsubmis.frontend.login.LoginActivity
import com.chaidar.storyappsubmis.frontend.main.MainActivity
import com.chaidar.storyappsubmis.frontend.main.MainViewModel

class SplashScreenActivity : AppCompatActivity() {

    private val welcomeViewModel by viewModels<WelcomeViewModel> {
        ViewModelFactory(UserPreference.getInstance(dataStore))
    }

    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        welcomeViewModel.getSession().observe(this) { user ->
            if (user.isLogin) {
                UserPreference.setToken(user.tokenAuth)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}