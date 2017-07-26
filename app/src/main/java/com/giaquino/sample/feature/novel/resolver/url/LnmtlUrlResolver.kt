package com.giaquino.sample.feature.novel.resolver.url

import com.giaquino.sample.feature.novel.Chapter
import java.text.MessageFormat

object LnmtlUrlResolver : UrlResolver {

  private val formatter1 = MessageFormat("https://lnmtl.com/chapter/{0}-book-{1}-chapter-{2}")
  private val formatter2 = MessageFormat("https://lnmtl.com/chapter/{0}-chapter-{2}")

  override fun resolveUrl(chapter: Chapter): String {
    if (chapter.hasVolume()) {
      return formatter1.format(chapter.arguments())
    }
    return formatter2.format(chapter.arguments())
  }
}