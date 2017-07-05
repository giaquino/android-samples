/*
 * Copyright 2017 Gian Darren Azriel I. Aquino
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

package com.giaquino.sample.experiment.state

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.giaquino.sample.R
import com.giaquino.sample.common.app.ViewModelFragment
import com.giaquino.sample.databinding.ExStateFragmentBinding
import com.giaquino.sample.experiment.state.ExStateFragmentViewModel.ExStateFragmentState
import timber.log.Timber

class ExStateFragment : ViewModelFragment<ExStateFragmentViewModel, ExStateFragmentState>() {

  companion object {

    const val EXTRA_GREETINGS = "com.giaquino.sample.EXTRA_GREETINGS"

    fun create(greetings: String) = ExStateFragment().apply {
      arguments = Bundle().apply { putString(EXTRA_GREETINGS, greetings) }
    }
  }

  private lateinit var binding: ExStateFragmentBinding

  override fun createViewModel(state: ExStateFragmentState?) = when (state) {
    null -> ExStateFragmentViewModel(arguments.getString(EXTRA_GREETINGS))
    else -> ExStateFragmentViewModel(state)
  }

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.ex_state_fragment, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Timber.d("Fragment View Created : %s", viewModel.greetings)
    if (savedInstanceState == null) updateUi()
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    updateUi()
  }

  fun updateUi() {
    binding.text.text = viewModel.greetings
  }
}