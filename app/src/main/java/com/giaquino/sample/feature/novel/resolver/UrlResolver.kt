package com.giaquino.sample.feature.novel.resolver

import com.giaquino.sample.feature.novel.Novel

interface UrlResolver {

  fun resolveUrl(novel: Novel): String
}