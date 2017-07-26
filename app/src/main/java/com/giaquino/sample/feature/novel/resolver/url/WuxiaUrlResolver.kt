package com.giaquino.sample.feature.novel.resolver.url

import com.giaquino.sample.feature.novel.Chapter
import java.text.MessageFormat

object WuxiaUrlResolver : UrlResolver {

  private val formatter = MessageFormat("http://www.wuxiaworld.com/{0}-index/{0}-chapter-{2}/")

  override fun resolveUrl(chapter: Chapter) = formatter.format(chapter.arguments())
}