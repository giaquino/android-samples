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

import com.giaquino.sample.service.GooglePlayService.ConnectionListener
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.reset
import org.mockito.Mockito.verify

class GooglePlayServiceTest {

  lateinit var googlePlayServices: GooglePlayService
  lateinit var googleClient: GoogleApiClient

  @Before fun setup() {
    googleClient = mock(GoogleApiClient::class.java)
    googlePlayServices = GooglePlayService(googleClient)
  }

  @Test fun notifyListenersOnConnected() {
    val listener1 = mock(ConnectionListener::class.java)
    val listener2 = mock(ConnectionListener::class.java)

    googlePlayServices.run {
      addConnectionListener(listener1)
      addConnectionListener(listener2)
      onConnected(null)
    }

    verify(listener1).onConnected(any())
    verify(listener2).onConnected(any())
  }

  @Test fun notifyListenersOnSuspended() {
    val listener1 = mock(ConnectionListener::class.java)
    val listener2 = mock(ConnectionListener::class.java)

    googlePlayServices.run {
      addConnectionListener(listener1)
      addConnectionListener(listener2)
      onConnectionSuspended(ConnectionCallbacks.CAUSE_NETWORK_LOST)
    }

    verify(listener1).onConnectionSuspended(ConnectionCallbacks.CAUSE_NETWORK_LOST)
    verify(listener2).onConnectionSuspended(ConnectionCallbacks.CAUSE_NETWORK_LOST)
  }

  @Test fun notifyListenersOnFailed() {
    val listener1 = mock(ConnectionListener::class.java)
    val listener2 = mock(ConnectionListener::class.java)
    val result = ConnectionResult(ConnectionResult.TIMEOUT)

    googlePlayServices.run {
      addConnectionListener(listener1)
      addConnectionListener(listener2)
      onConnectionFailed(result)
    }

    verify(listener1).onConnectionFailed(result)
    verify(listener2).onConnectionFailed(result)
  }

  @Test fun removedListenersWillNotBeNotified() {
    val listener = mock(ConnectionListener::class.java)
    val result = ConnectionResult(ConnectionResult.TIMEOUT)

    googlePlayServices.run {
      addConnectionListener(listener)
      onConnected(null)
      onConnectionFailed(result)
      onConnectionSuspended(ConnectionCallbacks.CAUSE_NETWORK_LOST)
    }

    verify(listener).onConnected(any())
    verify(listener).onConnectionFailed(result)
    verify(listener).onConnectionSuspended(ConnectionCallbacks.CAUSE_NETWORK_LOST)

    reset(listener)

    googlePlayServices.run {
      removeConnectionListener(listener)
      onConnected(null)
      onConnectionFailed(result)
      onConnectionSuspended(ConnectionCallbacks.CAUSE_NETWORK_LOST)
    }

    verify(listener, never()).onConnected(any())
    verify(listener, never()).onConnectionFailed(result)
    verify(listener, never()).onConnectionSuspended(ConnectionCallbacks.CAUSE_NETWORK_LOST)
  }

  @Test fun serviceShouldConnectOnlyOnce() {
    googlePlayServices.connect()
    verify(googleClient).connect()

    reset(googleClient)
    `when`(googleClient.isConnected).thenReturn(true)

    googlePlayServices.connect()
    verify(googleClient, never()).connect()

    reset(googleClient)
    `when`(googleClient.isConnecting).thenReturn(true)

    googlePlayServices.connect()
    verify(googleClient, never()).connect()
  }
}