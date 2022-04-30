package com.example.ikuzsmusicapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ikuzsmusicapp.R
import com.example.ikuzsmusicapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.localButton.setOnClickListener{
            val intent = Intent(this, LocalActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit)
        }
    }
}