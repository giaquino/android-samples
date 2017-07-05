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

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutInfo.Builder
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.net.Uri
import android.os.Build.VERSION_CODES
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL


@TargetApi(VERSION_CODES.N_MR1)
class ShortcutServiceV25(val context: Context) : ShortcutService {

  private val shortcutManager: ShortcutManager
      = context.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager

  override fun getShortcuts() = shortcutManager.dynamicShortcuts
      .plus(shortcutManager.pinnedShortcuts)
      .filter { !it.isImmutable }
      .distinctBy { it.id }
      .toList()

  override fun addShortcuts(shortcuts: List<ShortcutInfo>): Boolean {
    return shortcutManager.addDynamicShortcuts(shortcuts)
  }

  override fun removeShortcut(shortcutInfo: ShortcutInfo): Boolean {
    try {
      shortcutManager.removeDynamicShortcuts(listOf(shortcutInfo.id))
      return true
    } catch (e: IllegalStateException) {
      Timber.e(e, "#removeShortcut : %s", shortcutInfo)
      return false
    }
  }

  override fun enableShortcut(shortcutInfo: ShortcutInfo): Boolean {
    try {
      shortcutManager.enableShortcuts(listOf(shortcutInfo.id))
      return true
    } catch (e: Exception) {
      Timber.e(e, "#enableShortcut : %s", shortcutInfo)
      return false
    }
  }

  override fun disableShortcut(shortcutInfo: ShortcutInfo): Boolean {
    try {
      shortcutManager.disableShortcuts(listOf(shortcutInfo.id))
      return true
    } catch (e: Exception) {
      Timber.e(e, "#disableShortcut : %s", shortcutInfo)
      return false
    }
  }

  override fun getMaxShortcutCountPerActivity() = shortcutManager.maxShortcutCountPerActivity

  override fun reportShortcutUsed(id: String): Boolean {
    try {
      shortcutManager.reportShortcutUsed(id)
      return true
    } catch (e: IllegalStateException) {
      Timber.e(e, "#reportShortcutUsed : %s", id)
      return false
    }
  }

  override fun createShortcutForUrl(url: String, defaultIconResource: Int): ShortcutInfo {
    val uri = Uri.parse(normalizeUrl(url))
    val builder = Builder(context, uri.toString())
    builder.apply {
      setIntent(Intent(Intent.ACTION_VIEW, uri))
      setShortLabel(uri.host)
      setLongLabel(uri.toString())
    }
    val favicon = fetchFavicon(uri)

    if (favicon != null) {
      builder.setIcon(Icon.createWithBitmap(favicon))
    } else {
      builder.setIcon(Icon.createWithResource(context, defaultIconResource))
    }
    return builder.build()
  }

  private fun normalizeUrl(url: String): String {
    if (url.startsWith("http://") || url.startsWith("https://")) {
      return url
    } else {
      return "http://" + url
    }
  }

  private fun fetchFavicon(uri: Uri): Bitmap? {
    val iconUri = uri.buildUpon().path("favicon.ico").build()
    var inStream: InputStream? = null
    var bufferedStream: BufferedInputStream? = null
    try {
      val connection = URL(iconUri.toString()).openConnection()
      connection.connect()
      inStream = connection.getInputStream()
      bufferedStream = BufferedInputStream(inStream, 8192)
      return BitmapFactory.decodeStream(bufferedStream)
    } catch (e: IOException) {
      return null
    } finally {
      inStream?.close()
      bufferedStream?.close()
    }
  }
}