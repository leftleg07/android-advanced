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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.android.kotlin.songdetail.content.SongUtils


/**
 * An activity representing a list of song titles (items). When one is
 * touched in vertical orientation (single pane), an intent starts
 * [SongDetailActivity] representing song details. When one is
 * touched in horizontal orientation on a tablet screen (two panes),
 * the activity shows a fragment.
 */
class MainActivity : AppCompatActivity() {

    // Default layout is one pane, not two.
    private var mTwoPane = false

    private val mSongModel by lazy { ViewModelProviders.of(this).get(SongViewModel::class.java) }
    /**
     * Sets up a song list as a RecyclerView, and determines
     * whether the screen is wide enough for two-pane mode.
     * The song_detail_container view for MainActivity will be
     * present only if the screen's width is 900dp or larger,
     * because it is defined only in the "song_list.xml (w900dp).xml"
     * layout, not in the default "song_list.xml" layout for smaller
     * screen sizes. If this view is present, then the activity
     * should be in two-pane mode.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        // Set the toolbar as the app bar.
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        // Get the song list as a RecyclerView.
        val recyclerView = findViewById<RecyclerView>(R.id.song_list)
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(SongUtils.SONG_ITEMS)

        // Is the container layout available? If so, set mTwoPane to true.
        if (findViewById<View>(R.id.song_detail_container) != null) {
            mTwoPane = true
        }
    }

    /**
     * The RecyclerView for the song list.
     */
    internal inner class SimpleItemRecyclerViewAdapter(private val mValues: List<SongUtils.Song>) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        /**
         * This method inflates the layout for the song list.
         * @param parent ViewGroup into which the new view will be added.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.song_list_content, parent, false)
            return ViewHolder(view)
        }

        /**
         * This method implements a listener with setOnClickListener().
         * When the user taps a song title, the code checks if mTwoPane
         * is true, and if so uses a fragment to show the song detail.
         * If mTwoPane is not true, it starts SongDetailActivity
         * using an intent with extra data about which song title was selected.
         *
         * @param holder   ViewHolder
         * @param position Position of the song in the array.
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mItem = mValues[position]
            holder.mIdView.text = (position + 1).toString()
            holder.mContentView.text = mValues[position].song_title
            holder.itemView.setOnClickListener { v ->
                if (mTwoPane) {
                    // Get selected song position in song list.
                    mSongModel.selectedSong.value = holder.adapterPosition

                } else {
                    // Send an intent to the SongDetailActivity
                    // with intent extra of the selected song position.
                    val context = v.context
                    val intent = Intent(context,
                            SongDetailActivity::class.java)
                    intent.putExtra(SongUtils.SONG_ID_KEY,
                            holder.adapterPosition)
                    context.startActivity(intent)
                }
            }
        }

        /**
         * Get the count of song list items.
         * @return Integer count
         */
        override fun getItemCount(): Int {
            return mValues.size
        }

        /**
         * ViewHolder describes an item view and metadata about its place
         * within the RecyclerView.
         */
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mIdView = view.findViewById<TextView>(R.id.id)
            val mContentView = view.findViewById<TextView>(R.id.content)
            var mItem: SongUtils.Song? = null

        }
    }
}
