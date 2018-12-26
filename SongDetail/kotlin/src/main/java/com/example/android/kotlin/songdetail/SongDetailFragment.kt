/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.kotlin.songdetail


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.android.kotlin.songdetail.content.SongUtils
import com.example.android.kotlin.songdetail.content.SongUtils.SONG_ITEMS

/**
 * A simple [Fragment] subclass that displays the song
 * detail based on the song selected from a list.
 */
class SongDetailFragment : Fragment() {

    // Song includes the song title and detail.
    var mSong: SongUtils.Song? = null

    private val mSongModel by lazy { activity?.run { ViewModelProviders.of(this).get(SongViewModel::class.java) } }


    private lateinit var mDetailText: TextView

    /**
     * This method inflates the fragment's view and shows the song
     * detail information.
     *
     * @param inflater LayoutInflater object to inflate views
     * @param container ViewGroup that the fragment's UI should be attached to
     * @param savedInstanceState Bundle containing previous state
     * @return Fragment view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.song_detail, container, false)
        // Show the detail information in a TextView.
        mDetailText = rootView.findViewById(R.id.song_detail)

        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mSongModel?.selectedSong?.observe(this, Observer {
            mDetailText.text = SONG_ITEMS[it].details
        })
    }
}
