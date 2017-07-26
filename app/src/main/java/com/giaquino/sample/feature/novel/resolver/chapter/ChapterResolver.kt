package com.giaquino.sample.feature.novel.resolver.chapter

import com.giaquino.sample.feature.novel.Chapter

interface ChapterResolver {

  fun resolveNextChapter(chapter: Chapter, notFound: Boolean = false): Chapter
}