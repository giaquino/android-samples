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

package com.giaquino.sample.service.location

import android.location.Location
import android.os.Bundle
import com.giaquino.sample.service.GooglePlayService
import com.giaquino.sample.service.GooglePlayService.ConnectionListener
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.location.FusedLocationProviderApi
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import timber.log.Timber

class LocationService(
    private val googleService: GooglePlayService,
    private val locationProvider: FusedLocationProviderApi,
    private val priority: Int,
    private val interval: Long,
    private val fastInterval: Long
) : ConnectionListener, LocationListener {

  var isRunning = false

  fun start() {
    isRunning = true
    if (googleService.googleClient.isConnected) {
      requestLocation()
    }
    googleService.addConnectionListener(this)
  }

  fun stop() {
    isRunning = false
    googleService.removeConnectionListener(this)
    locationProvider.removeLocationUpdates(googleService.googleClient, this)
  }

  override fun onLocationChanged(location: Location) {
    Timber.d("#onLocationChanged location : %s", location)
  }

  override fun onConnected(p0: Bundle?) {
    if (isRunning) requestLocation()
  }

  override fun onConnectionSuspended(p0: Int) {
    locationProvider.removeLocationUpdates(googleService.googleClient, this)
  }

  override fun onConnectionFailed(p0: ConnectionResult) {
    locationProvider.removeLocationUpdates(googleService.googleClient, this)
  }

  private fun requestLocation() {
    val request = LocationRequest().apply {
      priority = this@LocationService.priority
      interval = this@LocationService.interval
      fastestInterval = this@LocationService.fastInterval
    }
    try {
      Timber.d("#requestLocation %s", request)
      locationProvider.requestLocationUpdates(googleService.googleClient, request, this)
    } catch (e: SecurityException) {
      Timber.e(e, "#requestLocation")
      stop()
    }
  }
}
