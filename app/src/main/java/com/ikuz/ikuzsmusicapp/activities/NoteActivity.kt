package com.ikuz.ikuzsmusicapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.ikuz.ikuzsmusicapp.R
import com.ikuz.ikuzsmusicapp.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNoteBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener{
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit)
    }
}