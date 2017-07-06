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
import android.databinding.DataBindingUtil
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import com.giaquino.sample.R
import com.giaquino.sample.R.layout
import com.giaquino.sample.common.app.ViewModel.State
import com.giaquino.sample.common.app.ViewModelActivity
import com.giaquino.sample.common.extensions.toast
import com.giaquino.sample.databinding.ShortcutActivityBinding
import com.giaquino.sample.databinding.ShortcutCreateBinding
import com.giaquino.sample.service.shortcut.ShortcutServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.LazyThreadSafetyMode.NONE

class ShortcutActivity : ViewModelActivity<ShortcutViewModel, State>() {

  private val createLayout: ShortcutCreateBinding by lazy(NONE) {
    ShortcutCreateBinding.inflate(layoutInflater).apply {
      buttonCreate.setOnClickListener { createShortcut(input.text.toString()) }
    }
  }

  private val createDialog: BottomSheetDialog by lazy(NONE) {
    BottomSheetDialog(this).apply {
      setOnDismissListener { createLayout.input.setText("") }
      setContentView(createLayout.root)
    }
  }

  private lateinit var shortcutLayout: ShortcutActivityBinding
  private lateinit var adapter: ShortcutAdapter

  override fun createViewModel(state: State?)
      = ShortcutViewModel(ShortcutServiceFactory.create(this))

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    adapter = ShortcutAdapter(this::deleteShortcut)
    viewModel.shortcuts.observeOn(AndroidSchedulers.mainThread()).subscribe {
      adapter.setShortcuts(it)
    }

    shortcutLayout = DataBindingUtil.setContentView(this, layout.shortcut_activity)
    shortcutLayout.apply {
      list.adapter = adapter
      list.layoutManager = LinearLayoutManager(this@ShortcutActivity, VERTICAL, false)
      setSupportActionBar(includeToolbar.toolbar)
      activity = this@ShortcutActivity
      viewModel = this@ShortcutActivity.viewModel
    }
  }

  fun showCreateDialog() {
    createDialog.show()
  }

  fun createShortcut(input: String) {
    viewModel.createShortcut(input)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ url -> toast(url) }, { error -> toast(error.message) })
  }

  fun deleteShortcut(shortcut: ShortcutInfo): Unit {
    if (VERSION.SDK_INT < VERSION_CODES.N_MR1) return
    AlertDialog.Builder(this)
        .setMessage(
            getString(R.string.shortcut_delete_message_format, shortcut.longLabel.toString()))
        .setPositiveButton(R.string.shortcut_delete) { _, _ -> viewModel.deleteShortcut(shortcut) }
        .setNegativeButton(R.string.shortcut_cancel) { dialog, _ -> dialog.dismiss() }
        .show()
  }
}

