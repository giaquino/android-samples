package com.giaquino.sample.feature.novel.resolver.url

import com.giaquino.sample.feature.novel.Chapter

interface UrlResolver {

  fun resolveUrl(chapter: Chapter): String
}