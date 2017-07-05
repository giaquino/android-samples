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

package com.giaquino.sample.service

import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import timber.log.Timber
import java.util.HashSet

/**
 * Wrapper class for [GoogleApiClient], responsible for managing its connection.
 */
open class GooglePlayService(val googleClient: GoogleApiClient) :
    ConnectionCallbacks, OnConnectionFailedListener {

  interface ConnectionListener :
      ConnectionCallbacks, OnConnectionFailedListener

  init {
    googleClient.run {
      registerConnectionCallbacks(this@GooglePlayService)
      registerConnectionFailedListener(this@GooglePlayService)
    }
  }

  private val listeners = HashSet<ConnectionListener>(5)

  fun connect() {
    if (googleClient.isConnected || googleClient.isConnecting) return
    googleClient.connect()
  }

  fun addConnectionListener(listener: ConnectionListener) = listeners.add(listener)

  fun removeConnectionListener(listener: ConnectionListener) = listeners.remove(listener)

  override fun onConnected(bundle: Bundle?) {
    Timber.d("#onConnected bundle = %s", bundle)
    for (listener in listeners) listener.onConnected(bundle)
  }

  /**
   * Called when we got disconnected, google client will automatically
   * reconnect and call [onConnected].
   */
  override fun onConnectionSuspended(code: Int) {
    Timber.d("#onConnectionSuspended code = %d", code)
    for (listener in listeners) listener.onConnectionSuspended(code)
  }

  /**
   * Called when google api encountered an error. We should resolve the error
   * by showing a dialog to a user defined here https://developers.google.com/android/reference/com/google/android/gms/common/api/GoogleApiClient.OnConnectionFailedListener
   *
   * We should call [connect] again after encountering an error.
   */
  override fun onConnectionFailed(result: ConnectionResult) {
    Timber.d("#onConnectionFailed result = %s", result)
    for (i in listeners) i.onConnectionFailed(result)
  }
}
