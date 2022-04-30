package com.example.ikuzsmusicapp.fragments

import android.content.ContentUris
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ikuzsmusicapp.adapters.LocalAlbumAdapter
import com.example.ikuzsmusicapp.R
import com.example.ikuzsmusicapp.databinding.FragmentLocalAlbumBinding
import com.example.ikuzsmusicapp.models.AlbumModel
import mu.KotlinLogging
import java.io.FileNotFoundException


class LocalAlbumFragment : Fragment() {

    private var _binding : FragmentLocalAlbumBinding? = null
    private val binding get() = _binding!!
    private val logger = KotlinLogging.logger {}
    var AlbumList : ArrayList<AlbumModel> = ArrayList()
    lateinit var cursor: Cursor
//    lateinit var path : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocalAlbumBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        createListItem()
    }

    fun createListItem (){

        var projection : Array<String> = arrayOf(
            MediaStore.Audio.Albums.ALBUM,
//            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Albums._ID,
            MediaStore.Audio.Albums.ALBUM_ID,
            MediaStore.Audio.Albums.NUMBER_OF_SONGS,
            MediaStore.Audio.Albums.ARTIST,
//            MediaStore.Audio.Albums.ALBUM_ART
        )

        var resolver = requireActivity().contentResolver

        cursor =
            resolver.query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null,null)!!

        val scale = resources.displayMetrics.density
        val dpAsPixels = (55.0f * scale + 0.5f).toInt()

        while (cursor!!.moveToNext()){
//            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART))
            val sArtworkUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(sArtworkUri, cursor.getLong(2))
            var bitmap: Bitmap? = null
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                    requireContext().contentResolver, albumArtUri
                )
                bitmap = Bitmap.createScaledBitmap(bitmap, dpAsPixels, dpAsPixels, true)
            } catch (exception: FileNotFoundException) {
                bitmap = BitmapFactory.decodeResource(
                    requireContext().resources,
                    R.drawable.album_p
                )
            }
            var AlbumData = AlbumModel(cursor.getString(0), cursor.getLong(1), cursor.getLong(2), cursor.getInt(3), cursor.getString(4))
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