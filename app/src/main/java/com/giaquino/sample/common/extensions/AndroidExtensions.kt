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

package com.giaquino.sample.common.extensions

import android.app.Activity
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

fun Activity.toast(message: String?)
    = message?.let { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }

fun Activity.toast(@StringRes res: Int)
    = Toast.makeText(this, getString(res), Toast.LENGTH_SHORT).show()

fun inflate(view: View, @LayoutRes res: Int, parent: ViewGroup? = null, attach: Boolean = false)
    = LayoutInflater.from(view.context).inflate(res, parent, attach)!!