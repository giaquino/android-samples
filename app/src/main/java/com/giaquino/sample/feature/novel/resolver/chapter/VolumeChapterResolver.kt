package com.giaquino.sample.feature.novel.resolver.chapter

import com.giaquino.sample.feature.novel.Chapter

object VolumeChapterResolver : ChapterResolver {

  override fun resolveNextChapter(chapter: Chapter, notFound: Boolean): Chapter {
    if (notFound) return Chapter(chapter.source, chapter.title, chapter.volume + 1, chapter.chapter)
    return Chapter(chapter.source, chapter.title, chapter.volume, chapter.chapter + 1)
  }
}