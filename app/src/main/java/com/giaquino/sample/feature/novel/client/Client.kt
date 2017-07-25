package com.giaquino.sample.feature.novel.client

interface Client {
  fun request(url: String): String
}