package com.alpine12.spotifyclone.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alpine12.spotifyclone.R
import com.alpine12.spotifyclone.adapters.SongAdapter
import com.alpine12.spotifyclone.other.Status
import com.alpine12.spotifyclone.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var songAdapter: SongAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupRecycleView()
        subscribeToObserve()

        songAdapter.setItemCLickListener {
            mainViewModel.playOrToggleSong(it)
        }
    }

    private fun setupRecycleView() = rvAllSongs.apply {
        adapter = songAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun subscribeToObserve() {
        mainViewModel.mediaItem.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        songAdapter.songs = songs
                    }
                }

                Status.ERROR -> Unit

                Status.LOADING -> allSongsProgressBar.isVisible = true


            }
        }
    }
}