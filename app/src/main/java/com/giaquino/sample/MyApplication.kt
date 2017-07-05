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

package com.giaquino.sample

import android.app.Application
import android.os.StrictMode
import android.support.v7.app.AppCompatDelegate
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApplication : Application() {

  companion object {
    init {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
  }

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
          .penaltyLog()
          .detectAll()
          .build())
      StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
          .penaltyFlashScreen()
          .detectAll()
          .build())
      Timber.plant(DebugTree())
    }
  }
}