package com.giaquino.sample.feature.novel.resolver.chapter

import com.giaquino.sample.feature.novel.Chapter

object DefaultChapterResolver : ChapterResolver {

  override fun resolveNextChapter(chapter: Chapter, notFound: Boolean): Chapter {
    if (notFound) return chapter
    return Chapter(chapter.source, chapter.title, chapter.volume, chapter.chapter + 1)
  }
}