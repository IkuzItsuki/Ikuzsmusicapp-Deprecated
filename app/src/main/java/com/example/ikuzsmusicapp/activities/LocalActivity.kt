package com.example.ikuzsmusicapp.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ikuzsmusicapp.fragments.LocalAlbumFragment
import com.example.ikuzsmusicapp.fragments.LocalArtistFragment
import com.example.ikuzsmusicapp.fragments.LocalSongFragment
import com.example.ikuzsmusicapp.R
import com.example.ikuzsmusicapp.databinding.ActivityLocalBinding
import com.example.ikuzsmusicapp.models.SongModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LocalActivity : FragmentActivity() {

    private lateinit var binding: ActivityLocalBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tablayout: TabLayout
    private var tabTitel = arrayOf("Song", "Artist", "Album")
    var songList : ArrayList<SongModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLocalBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(!checkPermission()){
            perminsionInfo()
        }

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.SongSearchBar.setOnClickListener {
            binding.SongSearchBar.onActionViewExpanded()
        }

        tablayout = binding.LocalSongTabLayout
        viewPager = binding.LocalSongVp
        viewPager.adapter = SongPageAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(tablayout, viewPager){
            tab, position ->
                tab.text = tabTitel[position]
        }.attach()


//        tablayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                val tablt : LinearLayout = tab?.let {
//                    (tablayout.getChildAt(0) as ViewGroup).getChildAt(
//                        it.position)
//                } as LinearLayout
//                val text : TextView = tablt.getChildAt(1) as TextView
//                text.setTextAppearance(R.style.NoAllCaps)
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//                val tablt : LinearLayout = tab?.let {
//                    (tablayout.getChildAt(0) as ViewGroup).getChildAt(
//                        it.position)
//                } as LinearLayout
//                val text : TextView = tablt.getChildAt(1) as TextView
//                text.setTextAppearance(R.style.NoAllCaps)
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//
//        })
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

    private class SongPageAdapter (fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle){
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> LocalSongFragment()
                1 -> LocalArtistFragment()
                2 -> LocalAlbumFragment()
                else -> LocalSongFragment()
            }
        }
    }

    private fun perminsionInfo() : Unit{
        var infoDialog : AlertDialog.Builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
        val dialogView : View = layoutInflater.inflate(R.layout.alertdialog_permission_request, null)
        var confirmBtn : Button = dialogView.findViewById<Button>(R.id.ConfirmBtn) as Button
        infoDialog.setView(dialogView)
        var dialog : AlertDialog = infoDialog.create()
        dialog.show()
        dialog.setCanceledOnTouchOutside(false);

        confirmBtn.setOnClickListener {
            requestPermission()
            dialog.dismiss()
        }
    }

    private fun checkPermission(): Boolean {
        val result : Int = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true
        } else{
            return false
        }
    }

    private fun requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit)
    }
}