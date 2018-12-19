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

package com.example.android.kotlin.fragmentexample

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView

/**
 * A simple [Fragment] subclass to display
 * radio buttons.
 */
class SimpleFragment : Fragment() {

    companion object {

        // The radio button choice has 3 states: 0 = yes, 1 = no,
        // 2 = default (no choice). Using only 0 and 1.
        private const val YES = 0
        private const val NO = 1
    }

    /**
     * Creates the view for the fragment.
     *
     * @param inflater           LayoutInflater
     * @param container          ViewGroup
     * @param savedInstanceState Bundle
     * @return View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment.
        val rootView = inflater.inflate(R.layout.fragment_simple, container, false)
        val radioGroup = rootView.findViewById<RadioGroup>(R.id.radio_group)

        // Set the radioGroup onCheckedChanged listener.
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId)
            val index = radioGroup.indexOfChild(radioButton)
            val textView = rootView.findViewById<TextView>(R.id.fragment_header)
            textView.text = when (index) {
                // User chose "Yes".
                YES -> getString(R.string.yes_message)
                // User chose "No".
                NO -> getString(R.string.no_message)
                // No choice made.
                else -> "" // Do nothing.
            }
        }

        // Return the View for the fragment's UI.
        return rootView
    }


}// Required empty public constructor
