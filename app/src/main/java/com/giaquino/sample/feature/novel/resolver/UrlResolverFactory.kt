package com.giaquino.sample.feature.novel.resolver

import com.giaquino.sample.feature.novel.Novel

object UrlResolverFactory {

  fun createUrlResolver(novel: Novel): UrlResolver {
    return when (novel.source) {
      Novel.Source.WUXIA_WORLD -> WuxiaUrlResolver
      Novel.Source.LNMTL -> LnmtlUrlResolver
    }
  }
}