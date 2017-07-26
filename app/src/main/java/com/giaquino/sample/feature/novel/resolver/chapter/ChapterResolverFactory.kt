package com.giaquino.sample.feature.novel.resolver.chapter

import com.giaquino.sample.feature.novel.Chapter

object ChapterResolverFactory {

  fun createChapterResolver(chapter: Chapter): ChapterResolver {
    return when (chapter.source) {
      Chapter.Source.WUXIA_WORLD -> DefaultChapterResolver
      Chapter.Source.LNMTL -> VolumeChapterResolver
    }
  }
}