package com.giaquino.sample.feature.novel.parser

import com.giaquino.sample.feature.novel.Novel

object ParserFactory {

  fun createParser(novel: Novel): Parser {
    return when (novel.source) {
      Novel.Source.WUXIA_WORLD -> WuxiaWorldParser
      Novel.Source.LNMTL -> LnmtlParser
    }
  }
}