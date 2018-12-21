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

import android.content.Context
import android.icu.text.Normalizer.NO
import android.icu.text.Normalizer.YES
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

/**
 * A simple [Fragment] subclass that shows a question
 * with radio buttons for providing feedback. If the user
 * clicks "Yes" the text header changes to "Article: Like".
 * If the user clicks "No" the text header changes to "Thanks".
 */
class SimpleFragment : Fragment() {

    private val mModel by lazy { activity?.run { ViewModelProviders.of(this).get(MainViewModel::class.java) } }
    private lateinit var radioGroup: RadioGroup
    private lateinit var textView: TextView

    /**
     * Creates the view for the fragment.
     *
     * @param inflater           LayoutInflater to inflate any views in the fragment
     * @param container          ViewGroup of parent view to attach fragment
     * @param savedInstanceState Bundle for previous state
     * @return rootView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment.
        val rootView = inflater.inflate(R.layout.fragment_simple,
                container, false)
        radioGroup = rootView.findViewById<RadioGroup>(R.id.radio_group)
        textView = rootView.findViewById<TextView>(R.id.fragment_header)


        savedInstanceState?.let {
            val choice = it.getInt(BUNDLE_CHOICE)
            val chichedRadio = radioGroup.getChildAt(choice)
            radioGroup.check(chichedRadio.id)

        } ?: radioGroup.check(radioGroup.getChildAt(0).id)

        // Set the radioGroup onCheckedChanged listener.
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<View>(checkedId)
            val index = group.indexOfChild(radioButton)

            mModel?.choice?.value = when (index) {
                // User chose "Yes".
                YES -> getString(R.string.yes_message)
                // User chose "No".
                NO -> getString(R.string.no_message)
                // No choice made.
                else -> ""
            }// Do nothing.
        }


        // Return the View for the fragment's UI.
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mModel?.choice?.observe(activity!!, Observer { choice ->
            textView.text = choice
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val checkedId = radioGroup.checkedRadioButtonId
        val radioButton = radioGroup.findViewById<View>(checkedId)
        val index = radioGroup.indexOfChild(radioButton)
        outState.putInt(BUNDLE_CHOICE, index)
    }

    companion object {

        // The radio button choice has 3 states: 0 = yes, 1 = no,
        // 2 = default (no choice). Using only 0 and 1.
        private const val YES = 0
        private const val NO = 1

        fun newInstance(): SimpleFragment {
            return SimpleFragment()
        }

        private const val BUNDLE_CHOICE = "_bundle_choice"
    }
}// Required empty public constructor
