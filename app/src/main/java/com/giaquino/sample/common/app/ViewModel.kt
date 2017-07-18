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

import android.os.Parcel
import com.giaquino.sample.common.extensions.KParcelable
import com.giaquino.sample.common.extensions.parcelableCreator

open class ViewModel {

  open fun onStart() {}
  open fun onResume() {}
  open fun onPause() {}
  open fun onStop() {}
  open fun onDestroy() {}
  open val state get() = State(this)

  /**
   * Base class responsible for representing the current state for the ViewModel.
   */
  open class State : KParcelable {

    companion object {
      @JvmField val CREATOR = parcelableCreator(::State)
    }

    constructor(viewModel: ViewModel)
    constructor (source: Parcel)

    override fun writeToParcel(dest: Parcel, flags: Int) {
    }
  }
}
