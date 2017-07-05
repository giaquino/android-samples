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

package com.giaquino.sample.feature.sharing

import android.databinding.DataBindingUtil
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import com.giaquino.sample.R
import com.giaquino.sample.common.app.ViewModel.State
import com.giaquino.sample.common.app.ViewModelActivity
import com.giaquino.sample.common.utils.AndroidUtils
import com.giaquino.sample.databinding.SharingActivityBinding

class SharingActivity : ViewModelActivity<SharingViewModel, State>() {

  private lateinit var sharingLayout: SharingActivityBinding

  override fun createViewModel(state: State?): SharingViewModel {
    return SharingViewModel(SharingUtils)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    sharingLayout = DataBindingUtil.setContentView(this, R.layout.sharing_activity)
    sharingLayout.activity = this
  }

  fun onShareTextClick() {
    val text = sharingLayout.inputText.text.toString()
    viewModel.shareText(this, text, getString(R.string.sharing_share))
    sharingLayout.root.background =
        BitmapDrawable(resources, AndroidUtils.screenshot(window.decorView))
  }
}