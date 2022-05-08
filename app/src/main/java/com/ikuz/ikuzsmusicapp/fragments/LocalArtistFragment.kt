package com.ikuz.ikuzsmusicapp.fragments

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ikuz.ikuzsmusicapp.adapters.LocalArtistAdapter
import com.ikuz.ikuzsmusicapp.databinding.FragmentLocalArtistBinding
import com.ikuz.ikuzsmusicapp.models.AlbumModel
import com.ikuz.ikuzsmusicapp.models.ArtistModel


class LocalArtistFragment : Fragment() {

    private var _binding : FragmentLocalArtistBinding? = null
    private val binding get() = _binding!!
    var ArtistList : ArrayList<ArtistModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocalArtistBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createListItem()
    }

    fun createListItem (){
        val projection : Array<String> = arrayOf(
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        )

        val resolver = requireActivity().contentResolver

        val cursor : Cursor = resolver.query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI, projection, null, null, null)!!

        while(cursor.moveToNext()){
            val ArtistData = ArtistModel(cursor.getString(0), cursor.getInt(1))
            ArtistList.add(ArtistData)
        }
        if (ArtistList.size == 0){
            binding.noArtistText.visibility = View.VISIBLE
        } else {
            binding.artistRecView.layoutManager = LinearLayoutManager(requireContext())
            binding.artistRecView.adapter = LocalArtistAdapter(ArtistList, requireContext())
        }

    }
}