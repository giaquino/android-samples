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

import android.os.Parcel
import android.os.Parcelable.Creator
import com.giaquino.sample.common.app.ViewModel
import timber.log.Timber

class ExStateActivityViewModel : ViewModel {

  val name: String
  val greetings: String

  constructor(name: String) {
    Timber.d("Name arguments : %s", name)
    this.name = name
    this.greetings = "Hello $name!"
  }

  constructor(state: ExStateActivityState) {
    Timber.d("Name restored: %s", state.name)
    this.name = state.name
    this.greetings = "Hello $name!"
  }

  override val state: State get() = ExStateActivityState(this)

  class ExStateActivityState : State {

    companion object CREATOR : Creator<ExStateActivityState> {
      override fun createFromParcel(parcel: Parcel) = ExStateActivityState(parcel)
      override fun newArray(size: Int) = arrayOfNulls<ExStateActivityState>(size)
    }

    var name: String

    constructor(viewModel: ExStateActivityViewModel) : super(viewModel) {
      name = viewModel.name
      Timber.d("Name created from view model : %s", viewModel.name)
    }

    constructor(source: Parcel) : super(source) {
      name = source.readString()
      Timber.d("Name restored from parcel : %s", name)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
      super.writeToParcel(dest, flags)
      dest.writeString(name)
    }

    override fun describeContents() = 0
  }
}