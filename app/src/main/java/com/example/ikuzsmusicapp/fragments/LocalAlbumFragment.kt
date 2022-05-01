package com.example.ikuzsmusicapp.fragments

import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikuzsmusicapp.adapters.LocalAlbumAdapter
import com.example.ikuzsmusicapp.databinding.FragmentLocalAlbumBinding
import com.example.ikuzsmusicapp.models.AlbumModel
import mu.KotlinLogging


class LocalAlbumFragment : Fragment() {

    private var _binding : FragmentLocalAlbumBinding? = null
    private val binding get() = _binding!!
    private val logger = KotlinLogging.logger {}
    var AlbumList : ArrayList<AlbumModel> = ArrayList()
    lateinit var cursor: Cursor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        createListItem()
    }

    fun createListItem (){

        val projection : Array<String> = arrayOf(
            MediaStore.Audio.Albums.ALBUM,
//            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ARTIST,
//            MediaStore.Audio.Albums.ALBUM_ART
        )

        val resolver = requireActivity().contentResolver

        cursor =
            resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null,null)!!

        while (cursor.moveToNext()){
            val AlbumData = AlbumModel(cursor.getString(0), cursor.getLong(1), cursor.getLong(2), cursor.getInt(3), cursor.getString(4))
            AlbumList.add(AlbumData)
        }
        if (AlbumList.size == 0){
            binding.noAlbumText.visibility = View.VISIBLE
        } else {
            binding.albumRecView.layoutManager = LinearLayoutManager(requireActivity())
            binding.albumRecView.adapter = LocalAlbumAdapter(AlbumList, requireActivity().applicationContext)
        }

    }
    fun Context.resIdByName(resIdName: String?, resType: String): Int {
        resIdName?.let {
            return resources.getIdentifier(it, resType, packageName)
        }
        throw Resources.NotFoundException()
    }
}