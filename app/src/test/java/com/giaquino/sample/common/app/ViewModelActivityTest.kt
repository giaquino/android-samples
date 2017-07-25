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
import android.os.Parcel
import android.os.Parcelable
import com.giaquino.sample.BuildConfig
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, manifest = "AndroidManifest.xml")
class ViewModelActivityTest {

  private fun createAndDestroyFakeViewModelActivity() = Robolectric.buildActivity(
      FakeViewModelActivity::class.java)
      .create()
      .start()
      .resume()
      .pause()
      .stop()
      .destroy()
      .get()

  @Test fun onStartWillStartViewModel() {
    val activity = createAndDestroyFakeViewModelActivity()
    verify(activity.fakeViewModel, times(1)).onStart()
  }

  @Test fun onResumeWillResumeViewModel() {
    val activity = createAndDestroyFakeViewModelActivity()
    verify(activity.fakeViewModel, times(1)).onResume()
  }

  @Test fun onPauseWillPauseViewModel() {
    val activity = createAndDestroyFakeViewModelActivity()
    verify(activity.fakeViewModel, times(1)).onPause()
  }

  @Test fun onStopWillStopViewModel() {
    val activity = createAndDestroyFakeViewModelActivity()
    verify(activity.fakeViewModel, times(1)).onStop()
  }

  @Test fun onDestroyWillDestroyViewModel() {
    val activity = createAndDestroyFakeViewModelActivity()
    verify(activity.fakeViewModel, times(1)).onDestroy()
  }

  @Test fun activityWillRetainInstance() {
    var controller = Robolectric.buildActivity(FakeViewModelActivity::class.java).setup()

    controller.get().fakeViewModel.foobar = 14

    // Save instance of the activity
    val savedInstanceState = Bundle()
    controller.pause().saveInstanceState(savedInstanceState).stop().destroy()

    verify(controller.get().fakeViewModel, times(1)).state

    // Recreate activity from the saved instance
    controller = Robolectric.buildActivity(FakeViewModelActivity::class.java).create(
        savedInstanceState)

    assertThat(controller.get().fakeViewModel.foobar).isEqualTo(14)
  }

  private open class FakeViewModelActivity : ViewModelActivity<FakeViewModel, FakeViewModelState>() {

    lateinit var fakeViewModel: FakeViewModel

    override fun createViewModel(state: FakeViewModelState?): FakeViewModel {
      fakeViewModel = Mockito.spy(FakeViewModel(state))
      return fakeViewModel
    }
  }

  private open class FakeViewModel(state: FakeViewModelState?) : ViewModel() {

    var foobar = 0

    init {
      state?.let { foobar = state.foobar }
    }

    override val state get() = FakeViewModelState(this)
  }

  private class FakeViewModelState : ViewModel.State {

    companion object {
      @JvmField val CREATOR = object : Parcelable.Creator<FakeViewModelState> {
        override fun createFromParcel(source: Parcel) = FakeViewModelState(source)
        override fun newArray(size: Int): Array<FakeViewModelState?> = arrayOfNulls(size)
      }
    }

    var foobar = 0

    constructor(fakeViewModel: FakeViewModel) : super(fakeViewModel) {
      foobar = fakeViewModel.foobar
    }

    constructor(source: Parcel) : super(source) {
      foobar = source.readInt()
    }

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
      super.writeToParcel(dest, flags)
      dest.writeInt(foobar)
    }
  }
}