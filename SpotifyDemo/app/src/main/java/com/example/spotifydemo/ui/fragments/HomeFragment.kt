package com.example.spotifydemo.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spotifydemo.R
import com.example.spotifydemo.adapters.SongAdapter
import com.example.spotifydemo.other.Status
import com.example.spotifydemo.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :Fragment(R.layout.fragment_home) {

    lateinit var mainViewModel: MainViewModel
    lateinit var rvAllSongs :RecyclerView
    lateinit var allSongsProgressBar:ProgressBar


    @Inject
    lateinit var songAdapter:SongAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAllSongs = view.findViewById(R.id.rvAllSongs)
        allSongsProgressBar = view.findViewById(R.id.allSongsProgressBar)
        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        setupRecyclerView()
        subScribeToObservers()

        songAdapter.setOnItemClickListener {
            mainViewModel.playOrToggleSong(it)
        }

    }
    private fun setupRecyclerView()=rvAllSongs.apply {
        adapter= songAdapter
        layoutManager= LinearLayoutManager(requireContext())
    }
    private  fun subScribeToObservers(){
        mainViewModel.mediaItems.observe(viewLifecycleOwner){result->
            when(result.status){
                Status.SUCCESS->{
                    allSongsProgressBar.isVisible=false
                    result.data?.let{songs->
                        songAdapter.songs=songs
                    }

                }
                Status.ERROR ->Unit
                Status.LOADING ->allSongsProgressBar.isVisible=true
            }
        }
    }

}