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

class ExStateFragmentViewModel : ViewModel {

  val greetings: String

  constructor(greetings: String) {
    Timber.d("Greetings arguments :  %s", greetings);
    this.greetings = greetings
  }

  constructor(state: ExStateFragmentState) {
    Timber.d("Greetings restored : %s", state.greetings)
    this.greetings = state.greetings
  }

  override val state: State get() = ExStateFragmentState(this)

  class ExStateFragmentState : State {

    companion object CREATOR : Creator<ExStateFragmentState> {
      override fun createFromParcel(parcel: Parcel) = ExStateFragmentState(parcel)
      override fun newArray(size: Int) = arrayOfNulls<ExStateFragmentState>(size)
    }

    var greetings: String

    constructor(viewModel: ExStateFragmentViewModel) : super(viewModel) {
      greetings = viewModel.greetings
      Timber.d("Greetings created from view model : %s", greetings)
    }

    constructor(source: Parcel) : super(source) {
      greetings = source.readString()
      Timber.d("Greetings restored from parcel : %s", greetings)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
      super.writeToParcel(dest, flags)
      dest.writeString(greetings)
    }

    override fun describeContents() = 0
  }
}