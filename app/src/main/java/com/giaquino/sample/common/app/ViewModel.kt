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
import android.os.Parcelable
import android.support.annotation.CallSuper

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
  open class State : Parcelable {

    companion object {
      @JvmField val CREATOR = object : Parcelable.Creator<State> {
        override fun createFromParcel(source: Parcel) = State(source)
        override fun newArray(size: Int): Array<State?> = arrayOfNulls(size)
      }
    }

    constructor(viewModel: ViewModel)

    constructor (source: Parcel)

    override fun describeContents() = 0

    @CallSuper override fun writeToParcel(dest: Parcel, flags: Int) {
    }
  }
}
