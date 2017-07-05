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

import android.content.pm.ShortcutInfo

class ShortcutServiceBase : ShortcutService {

  override fun getShortcuts() = emptyList<ShortcutInfo>()

  override fun addShortcuts(shortcuts: List<ShortcutInfo>) = false

  override fun removeShortcut(shortcutInfo: ShortcutInfo) = false

  override fun enableShortcut(shortcutInfo: ShortcutInfo) = false

  override fun disableShortcut(shortcutInfo: ShortcutInfo) = false

  override fun getMaxShortcutCountPerActivity() = 0

  override fun reportShortcutUsed(id: String) = false

  override fun createShortcutForUrl(url: String, defaultIconResource: Int): ShortcutInfo {
    throw UnsupportedOperationException("Operation is not supported on the current sdk version.")
  }
}