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

package com.giaquino.sample.common.app

import android.os.Bundle
import android.support.v4.app.Fragment
import timber.log.Timber

abstract class ViewModelFragment<VIEW_MODEL : ViewModel, STATE : ViewModel.State> : Fragment() {

  companion object {
    const val EXTRA_VIEW_MODEL_STATE = "ViewModelActivity.EXTRA_VIEW_MODEL_STATE"
  }

  protected lateinit var viewModel: VIEW_MODEL

  protected abstract fun createViewModel(state: STATE?): VIEW_MODEL

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (savedInstanceState != null) {
      viewModel = createViewModel(savedInstanceState.getParcelable(EXTRA_VIEW_MODEL_STATE))
    } else {
      viewModel = createViewModel(null)
    }
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    outState?.putParcelable(EXTRA_VIEW_MODEL_STATE, viewModel.state)
    Timber.d("Called onSaveInstanceState")
  }

  override fun onViewStateRestored(savedInstanceState: Bundle?) {
    super.onViewStateRestored(savedInstanceState)
    Timber.d("Called onViewStateRestored")
  }

  override fun onStart() {
    super.onStart()
    viewModel.onStart()
  }

  override fun onResume() {
    super.onResume()
    viewModel.onResume()
  }

  override fun onPause() {
    super.onPause()
    viewModel.onPause()
  }

  override fun onStop() {
    super.onStop()
    viewModel.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    viewModel.onDestroy()
  }
}