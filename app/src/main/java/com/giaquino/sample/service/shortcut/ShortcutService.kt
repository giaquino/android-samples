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
import android.support.annotation.WorkerThread

interface ShortcutService {

  /**
   * Return all published dynamic or pinned shortcut.
   */
  fun getShortcuts(): List<ShortcutInfo>

  /**
   * Publish the list of dynamic shortcut.  If there are already dynamic or pinned shortcut with
   * the same IDs, each mutable shortcut is updated.
   *
   * <p>This API will be rate-limited.
   *
   * @return {@code true} if the call has succeeded. {@code false} if the call is rate-limited.
   *
   * @throws IllegalArgumentException if {@link #getMaxShortcutCountPerActivity()} is exceeded,
   * or when trying to update immutable shortcut.
   *
   * @throws IllegalStateException if the user is locked.
   */
  @Throws(IllegalArgumentException::class, IllegalStateException::class)
  fun addShortcuts(shortcuts: List<ShortcutInfo>): Boolean

  /**
   * Deletes a dynamic shortcut.
   *
   * @return {@code true} if the call has succeeded. {@code false} if the call failed because
   * the shortcut is not dynamic or user is locked.
   */
  fun removeShortcut(shortcutInfo: ShortcutInfo): Boolean

  /**
   * Enables a dynamic shortcut.
   *
   * @return {@code true} if the call has succeeded. {@code false} if the call failed because
   * the user is locked.
   */
  fun enableShortcut(shortcutInfo: ShortcutInfo): Boolean

  /**
   * Disables a dynamic shortcut.
   *
   * @return {@code true} if the call has succeeded. {@code false} if the call failed because
   * the user is locked.
   */
  fun disableShortcut(shortcutInfo: ShortcutInfo): Boolean

  /**
   * Return the maximum number of static and dynamic shortcut that each launcher icon
   * can have at a time.
   */
  fun getMaxShortcutCountPerActivity(): Int

  /**
   * Apps that publish shortcut should call this method whenever the user
   * selects the shortcut containing the given ID or when the user completes
   * an action in the app that is equivalent to selecting the shortcut.
   * For more details, see the Javadoc for the {@link ShortcutManager} class
   *
   * <p>The information is accessible via {@link UsageStatsManager#queryEvents}
   * Typically, launcher apps use this information to build a prediction model
   * so that they can promote the shortcut that are likely to be used at the moment.
   *
   * @return {@code true} if the call succeeded. {@code false} if the call failed because
   * the user is locked.
   */
  fun reportShortcutUsed(id: String): Boolean

  /**
   * Create a [ShortcutInfo] for the given url.
   *
   * <p>This method will fetch necessary information like favicon from the given url.
   *
   * @throws UnsupportedOperationException if sdk version does not meet the required version.
   */
  @WorkerThread @Throws(UnsupportedOperationException::class)
  fun createShortcutForUrl(url: String, defaultIconResource: Int): ShortcutInfo
}