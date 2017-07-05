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
import com.giaquino.sample.service.shortcut.ShortcutService
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

inline fun <reified T : Any> anys() = Mockito.any(T::class.java) ?: mock(T::class.java)

@TargetApi(VERSION_CODES.N_MR1) class ShortcutViewModelTest {

  private fun <T> any(): T {
    return uninitialized()
  }

  private fun <T> uninitialized(): T = null as T

  lateinit var service: ShortcutService
  lateinit var viewModel: ShortcutViewModel
  lateinit var shortcuts: MutableList<ShortcutInfo>

  @Before fun setup() {
    service = mock(ShortcutService::class.java)
    shortcuts = MutableList(10, { i -> mock(ShortcutInfo::class.java) })

    `when`(service.getShortcuts()).thenReturn(shortcuts)

    `when`(service.createShortcutForUrl(anyString(), anyInt()))
        .thenReturn(mock(ShortcutInfo::class.java))

    `when`(service.removeShortcut(any()))
        .then { shortcuts.removeAt(0) }

    `when`(service.addShortcuts(anyList())).then {
      shortcuts.add(mock(ShortcutInfo::class.java))
    }

    viewModel = ShortcutViewModel(service)
  }

  @Test fun refreshingUpdateShortcuts() {
    val observer = viewModel.shortcuts.test()
    observer.assertValueCount(1).assertValue { it == shortcuts }
    viewModel.refreshShortcuts()
    observer.assertValueCount(2).assertValueAt(1, { it == shortcuts })
    viewModel.refreshShortcuts()
    observer.assertValueCount(3).assertValueAt(1, { it == shortcuts })
    viewModel.refreshShortcuts()
    observer.assertValueCount(4).assertValueAt(1, { it == shortcuts })
  }

  @Test fun creatingUpdateShortcuts() {
    val observer = viewModel.shortcuts.test()
        .assertValueCount(1)
        .assertValue { it.size == 10 }
        .assertValue { it == shortcuts }

    viewModel.createShortcut("http://www.google.com").test()
        .assertValueCount(1)
        .assertNoErrors()
        .assertComplete()
        .assertValue { it == "http://www.google.com" }

    observer.assertValueCount(2)
        .assertValueAt(1, { it.size == 11 })
        .assertValueAt(1, { it == shortcuts })
  }
}