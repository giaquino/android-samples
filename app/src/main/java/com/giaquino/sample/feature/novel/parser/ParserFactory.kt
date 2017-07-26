package com.giaquino.sample.feature.novel.parser

import com.giaquino.sample.feature.novel.Chapter

object ParserFactory {

  fun createParser(chapter: Chapter): Parser {
    return when (chapter.source) {
      Chapter.Source.WUXIA_WORLD -> WuxiaWorldParser
      Chapter.Source.LNMTL -> LnmtlParser
    }
  }
}