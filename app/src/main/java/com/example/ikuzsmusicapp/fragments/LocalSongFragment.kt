package com.example.ikuzsmusicapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ikuzsmusicapp.adapters.LocalSongAdapter
import com.example.ikuzsmusicapp.databinding.FragmentLocalSongBinding
import com.example.ikuzsmusicapp.models.SongModel
import java.io.File


class LocalSongFragment : Fragment() {

    var songList : ArrayList<SongModel> = ArrayList()
    private var _binding : FragmentLocalSongBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalSongBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val projection : Array<String> = arrayOf(
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM
        )

        val selection : String = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val resolver = requireActivity().contentResolver

        val cursor : Cursor? = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null)
        while (cursor!!.moveToNext()){
            val songData = SongModel(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))
            if(File(songData.path).exists()){
                songList.add(songData)
            }
        }
        if (songList.size == 0){
            val noSongText : TextView = binding.noSongText
            noSongText.visibility = View.VISIBLE
        } else{
            val localSongRecView : RecyclerView = binding.songRecView
            localSongRecView.layoutManager = LinearLayoutManager(requireActivity())
            localSongRecView.adapter = LocalSongAdapter(songList,requireActivity().applicationContext)
        }
    }

}