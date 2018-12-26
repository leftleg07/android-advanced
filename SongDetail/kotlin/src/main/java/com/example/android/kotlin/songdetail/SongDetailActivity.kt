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

import android.content.Intent
import android.os.Bundle

import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.ViewModelProviders

import com.example.android.kotlin.songdetail.content.SongUtils

/**
 * An activity that shows a fragment with song detail.
 */
class SongDetailActivity : AppCompatActivity() {

    private val mSongModel by lazy { ViewModelProviders.of(this).get(SongViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)
        val toolbar = findViewById<View>(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Show the Up button in the action bar.
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            // Get the selected song position from the intent extra.
            val selectedSong = intent.getIntExtra(SongUtils.SONG_ID_KEY, 0)
            // Create instance of the detail fragment and add it to the activity
            // using a fragment transaction.
            mSongModel.selectedSong.value = selectedSong
        }
    }

    /**
     * Performs action if the user selects the Up button.
     *
     * @param item Menu item selected (Up button)
     * @return True if options menu item is selected.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            // NavUtils allows users to navigate up one level in the
            // application structure.
            NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
