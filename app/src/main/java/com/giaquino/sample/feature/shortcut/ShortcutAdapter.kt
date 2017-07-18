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
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.support.v7.util.DiffUtil
import android.support.v7.util.DiffUtil.DiffResult
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.ViewGroup
import com.giaquino.sample.R
import com.giaquino.sample.common.extensions.inflate
import com.giaquino.sample.databinding.ShortcutItemBinding
import com.giaquino.sample.feature.shortcut.ShortcutAdapter.ShortcutViewHolder

class ShortcutAdapter(val callback: (ShortcutInfo) -> Unit)
  : RecyclerView.Adapter<ShortcutViewHolder>() {

  private val shortcuts = mutableListOf<ShortcutInfo>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
      = ShortcutViewHolder(parent, callback)

  override fun onBindViewHolder(holder: ShortcutViewHolder, position: Int) = holder.binding.run {
    shortcut = shortcuts[position]
    shortcutViewHolder = holder
    executePendingBindings()
  }

  override fun getItemCount() = shortcuts.size

  fun setShortcuts(items: List<ShortcutInfo>) {
    var result: DiffResult? = null
    if (VERSION.SDK_INT >= VERSION_CODES.N_MR1) {
      result = DiffUtil.calculateDiff(ShortcutDiffCallback(shortcuts, items))
    }
    shortcuts.clear()
    shortcuts.addAll(items)
    result?.dispatchUpdatesTo(this) ?: notifyDataSetChanged()
  }

  class ShortcutViewHolder(parent: ViewGroup, val callback: (ShortcutInfo) -> Unit)
    : ViewHolder(inflate(parent, R.layout.shortcut_item, parent)) {

    val binding = ShortcutItemBinding.bind(itemView)!!

    fun onClickShortcut(shortcut: ShortcutInfo) {
      callback.invoke(shortcut)
    }
  }
}