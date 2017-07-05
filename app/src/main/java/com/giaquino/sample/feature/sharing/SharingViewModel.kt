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

import android.content.Context
import com.giaquino.sample.common.app.ViewModel

class SharingViewModel(private val sharingUtils: SharingUtils) : ViewModel() {

  fun shareText(context: Context, text: String, title: String)
      = sharingUtils.shareText(context, text, title)
}