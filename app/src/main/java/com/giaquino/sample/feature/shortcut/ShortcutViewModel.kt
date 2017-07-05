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

import android.content.pm.ShortcutInfo
import com.giaquino.sample.R
import com.giaquino.sample.common.app.ViewModel
import com.giaquino.sample.service.shortcut.ShortcutService
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ShortcutViewModel(val shortcutService: ShortcutService) : ViewModel() {

  private val publisher = BehaviorSubject.create<List<ShortcutInfo>>()

  val shortcuts = publisher.publish().autoConnect()

  init {
    refreshShortcuts()
  }

  /**
   * Sync shortcuts from service.
   */
  fun refreshShortcuts() = publisher.onNext(shortcutService.getShortcuts())

  /**
   * Create a shortcut from the given url.
   */
  fun createShortcut(url: String) = Observable.fromCallable {
    val shortcut = shortcutService.createShortcutForUrl(url, R.drawable.ic_web)
    shortcutService.addShortcuts(listOf(shortcut))
    refreshShortcuts()
    return@fromCallable url
  }

  fun deleteShortcut(shortcut: ShortcutInfo) = shortcutService.removeShortcut(shortcut).let {
    if (it) refreshShortcuts()
  }
}
