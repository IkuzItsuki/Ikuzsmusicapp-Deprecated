package com.ikuz.ikuzsmusicapp.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.ikuz.ikuzsmusicapp.R
import com.ikuz.ikuzsmusicapp.databinding.ActivityCloudBinding
import com.ikuz.ikuzsmusicapp.fragments.CloudAlbumFragment
import com.ikuz.ikuzsmusicapp.fragments.CloudArtistFragment
import com.ikuz.ikuzsmusicapp.fragments.CloudSongFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CloudActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCloudBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout
    private var tabTitel = arrayOf("Song", "Artist", "Album")

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCloudBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.SongSearchBar.setOnClickListener {
            binding.SongSearchBar.onActionViewExpanded()
        }

        tablayout = binding.CloudSongTabLayout
        viewPager = binding.CloudSongVp
        viewPager.adapter = SongPageAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tablayout, viewPager){
                tab, position ->
            tab.text = tabTitel[position]
        }.attach()
    }

    override fun dispatchTouchEvent(motionevent: MotionEvent): Boolean {
        if (motionevent.action == MotionEvent.ACTION_DOWN) {
            val v= currentFocus
            if (isHideKeyboard(v, motionevent)){
                hideSoftKeyboard(v!!.windowToken)
            }
        }
        return super.dispatchTouchEvent(motionevent)
    }
    private fun isHideKeyboard(v: View?, event: MotionEvent) : Boolean {
        if (v != null && v is EditText){
            val l = intArrayOf(0,0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }
    private fun hideSoftKeyboard(token: IBinder?){
        if (token != null){
            val manager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    class SongPageAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> CloudSongFragment()
                1 -> CloudArtistFragment()
                2 -> CloudAlbumFragment()
                else -> CloudSongFragment()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit)
    }
}