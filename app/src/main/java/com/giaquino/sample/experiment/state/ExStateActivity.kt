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

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.giaquino.sample.R
import com.giaquino.sample.common.app.ViewModelActivity
import com.giaquino.sample.databinding.ExStateActivityBinding
import com.giaquino.sample.experiment.state.ExStateActivityViewModel.ExStateActivityState

class ExStateActivity : ViewModelActivity<ExStateActivityViewModel, ExStateActivityState>() {

  companion object {

    const val EXTRA_NAME = "com.giaquino.sample.EXTRA_NAME"

    fun createIntent(context: Context, name: String): Intent {
      return Intent(context, ExStateActivity::class.java)
          .apply {
            putExtra(EXTRA_NAME, name)
          }
    }
  }

  private lateinit var binding: ExStateActivityBinding

  override fun createViewModel(state: ExStateActivityState?) = when (state) {
    null -> ExStateActivityViewModel(intent.getStringExtra(EXTRA_NAME))
    else -> ExStateActivityViewModel(state)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.ex_state_activity)
    binding.activity = this
    setSupportActionBar(binding.includeToolbar.toolbar)

    if (supportFragmentManager.findFragmentByTag(ExStateFragment::class.java.name) == null) {
      supportFragmentManager.beginTransaction()
          .replace(R.id.container,
              ExStateFragment.create(viewModel.greetings), ExStateFragment::class.java.name)
          .commit()
    }
  }

  fun onStartActivity() {
    startActivity(ExStateActivity.createIntent(this, viewModel.name + " foobar"))
  }
}