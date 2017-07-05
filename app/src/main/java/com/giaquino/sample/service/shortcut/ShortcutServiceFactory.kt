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

package com.giaquino.sample.service.shortcut

import android.content.Context
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES

object ShortcutServiceFactory {

  fun create(context: Context) = if (VERSION.SDK_INT >= VERSION_CODES.N_MR1) {
    ShortcutServiceV25(context)
  } else {
    ShortcutServiceBase()
  }
}