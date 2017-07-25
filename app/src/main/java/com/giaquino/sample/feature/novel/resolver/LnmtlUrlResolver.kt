package com.giaquino.sample.feature.novel.resolver

import com.giaquino.sample.feature.novel.Novel
import java.text.MessageFormat

object LnmtlUrlResolver : UrlResolver {

  private val formatter1 = MessageFormat("https://lnmtl.com/chapter/{0}-book-{1}-chapter-{2}")
  private val formatter2 = MessageFormat("https://lnmtl.com/chapter/{0}-chapter-{2}")

  override fun resolveUrl(novel: Novel): String {
    if (novel.isVolumeAvailable()) {
      return formatter1.format(novel.arguments())
    }
    return formatter2.format(novel.arguments())
  }
}