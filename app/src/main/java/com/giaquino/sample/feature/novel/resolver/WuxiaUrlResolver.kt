package com.giaquino.sample.feature.novel.resolver

import com.giaquino.sample.feature.novel.Novel
import java.text.MessageFormat

object WuxiaUrlResolver : UrlResolver {

  private val formatter = MessageFormat("http://www.wuxiaworld.com/{0}-index/{0}-chapter-{2}/")

  override fun resolveUrl(novel: Novel) = formatter.format(novel.arguments())
}