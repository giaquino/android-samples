package com.giaquino.sample.feature.novel.client

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class OkHttpClient : Client {

  private val client = OkHttpClient()

  override fun request(url: String): String {
    val request = Request.Builder().url(url).build()
    var response: Response? = null
    try {
      response = client.newCall(request).execute()
      if (response.isSuccessful) return response.body()!!.string()
      else throw IOException("$url results to ${response.code()}: ${response.message()}")
    } finally {
      response?.close()
    }
  }
}