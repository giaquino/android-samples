package com.giaquino.sample.feature.novel.resolver.url

import com.giaquino.sample.feature.novel.Chapter

object UrlResolverFactory {

  fun createUrlResolver(chapter: Chapter): UrlResolver {
    return when (chapter.source) {
      Chapter.Source.WUXIA_WORLD -> WuxiaUrlResolver
      Chapter.Source.LNMTL -> LnmtlUrlResolver
    }
  }
}