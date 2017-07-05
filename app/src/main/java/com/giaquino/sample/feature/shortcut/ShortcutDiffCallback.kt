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

package com.giaquino.sample.feature.shortcut

import android.annotation.TargetApi
import android.content.pm.ShortcutInfo
import android.os.Build.VERSION_CODES
import android.support.v7.util.DiffUtil

@TargetApi(VERSION_CODES.N_MR1) class ShortcutDiffCallback(
    val old: List<ShortcutInfo>,
    val new: List<ShortcutInfo>
) : DiffUtil.Callback() {

  override fun areContentsTheSame(oldPosition: Int, newPosition: Int)
      = old[oldPosition].id == new[newPosition].id

  override fun areItemsTheSame(oldPosition: Int, newPosition: Int)
      = old[oldPosition].id == new[newPosition].id

  override fun getOldListSize() = old.size

  override fun getNewListSize() = new.size
}