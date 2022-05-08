package com.ikuz.ikuzsmusicapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.ikuz.ikuzsmusicapp.R
import com.ikuz.ikuzsmusicapp.databinding.ActivityMainBinding
import com.ikuz.ikuzsmusicapp.fragments.PlayerFragment

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
        binding.cloudButton.setOnClickListener{
            val intent = Intent(this, CloudActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit)
        }
        binding.updateNoteButton.setOnClickListener{
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit)
        }
        val fragmentTransition : FragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransition.replace(R.id.fragmentContainerView, PlayerFragment())
        fragmentTransition.addToBackStack(null)
        fragmentTransition.commit()
    }
}