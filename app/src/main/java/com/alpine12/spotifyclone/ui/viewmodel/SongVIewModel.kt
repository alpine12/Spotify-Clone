package com.alpine12.spotifyclone.ui.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alpine12.spotifyclone.exoplayer.MusicService
import com.alpine12.spotifyclone.exoplayer.MusicServiceConnection
import com.alpine12.spotifyclone.exoplayer.currentPlaybackPosition
import com.alpine12.spotifyclone.other.Constant.UPDATE_PLAYER_POSITION_INTERVAL
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SongVIewModel @ViewModelInject constructor(
    musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    private val playbackState = musicServiceConnection.playbackState

    private val _curSongDuration = MutableLiveData<Long>()
    val curSongDuration : LiveData<Long> = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>()
    val curPlayerPosition : LiveData<Long> = _curPlayerPosition


    init {
        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition(){
        viewModelScope.launch {
            while (true){
                    val pos = playbackState.value?.currentPlaybackPosition
                if (curPlayerPosition.value != pos){
                    _curSongDuration.postValue(pos)
                    _curSongDuration.postValue(MusicService.curSongDuration)
                }
                delay(UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }

}