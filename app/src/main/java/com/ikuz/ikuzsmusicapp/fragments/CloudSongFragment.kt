package com.ikuz.ikuzsmusicapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ikuz.ikuzsmusicapp.databinding.FragmentCloudSongBinding

class CloudSongFragment : Fragment() {

    private var _binding : FragmentCloudSongBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCloudSongBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        binding.ComingSoonText.visibility = View.VISIBLE
    }
}