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

import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {

    private val mButton by lazy { findViewById<Button>(R.id.open_button) }
    private var isFragmentDisplayed = false
    private val mModel by lazy {ViewModelProviders.of(this).get(MainViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // If returning from a configuration change, get the
        // fragment state and set the button text.
        savedInstanceState?.run{
            isFragmentDisplayed = getBoolean(STATE_FRAGMENT)
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close)
            }
        }

        // Set the click listener for the button.
        mButton.setOnClickListener {
            if (!isFragmentDisplayed) {
                displayFragment()
            } else {
                closeFragment()
            }
        }

        mModel.choice.observe(this, Observer { choice ->
            Toast.makeText(this@MainActivity, "You choose it - $choice", Toast.LENGTH_SHORT).show()
        })

    }

    /**
     * This method is called when the user clicks the button
     * to open the fragment.
     */
    private fun displayFragment() {
        // Instantiate the fragment.
        val simpleFragment = SimpleFragment.newInstance()
        // Get the FragmentManager and start a transaction.
        val fragmentTransaction = supportFragmentManager
                .beginTransaction()

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit()

        // Update the Button text.
        mButton.setText(R.string.close)
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true
    }

    /**
     * This method is called when the user clicks the button to
     * close the fragment.
     */
    private fun closeFragment() {
        // Check to see if the fragment is already showing.
        val simpleFragment = supportFragmentManager
                .findFragmentById(R.id.fragment_container) as SimpleFragment?

        simpleFragment?.run{
            // Create and commit the transaction to remove the fragment.
            val fragmentTransaction = fragmentManager?.beginTransaction()
            fragmentTransaction?.remove(this)?.commit()
        }

        // Update the Button text.
        mButton.setText(R.string.open)
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed)
        super.onSaveInstanceState(savedInstanceState)
    }

    companion object {

        // Saved instance state key.
        const val STATE_FRAGMENT = "state_of_fragment"
    }
}
