package com.chaidar.storyappsubmis.frontend.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chaidar.storyappsubmis.R
import com.chaidar.storyappsubmis.databinding.ActivityMainBinding
import com.chaidar.storyappsubmis.frontend.register.RegisterActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
    }
}