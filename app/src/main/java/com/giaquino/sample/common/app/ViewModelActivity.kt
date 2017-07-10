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
import android.support.v7.app.AppCompatActivity
import timber.log.Timber

abstract class ViewModelActivity<VIEW_MODEL : ViewModel, STATE : ViewModel.State>
  : AppCompatActivity() {

  companion object {
    const val EXTRA_VIEW_MODEL_STATE = "ViewModelActivity.EXTRA_VIEW_MODEL_STATE"
  }

  protected lateinit var viewModel: VIEW_MODEL

  protected abstract fun createViewModel(state: STATE?): VIEW_MODEL

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    Timber.d("Called onSaveInstanceState")
    outState.putParcelable(EXTRA_VIEW_MODEL_STATE, viewModel.state)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
    super.onRestoreInstanceState(savedInstanceState)
    Timber.d("Called onRestoreInstanceState")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    if (savedInstanceState != null) {
      viewModel = createViewModel(savedInstanceState.getParcelable(EXTRA_VIEW_MODEL_STATE))
    } else {
      viewModel = createViewModel(null)
    }
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
