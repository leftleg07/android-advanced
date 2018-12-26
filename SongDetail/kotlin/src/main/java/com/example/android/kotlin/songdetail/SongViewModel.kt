package com.example.android.kotlin.songdetail

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SongViewModel(application: Application): AndroidViewModel(application) {
    val selectedSong = MutableLiveData<Int>()
}