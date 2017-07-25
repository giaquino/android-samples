package com.giaquino.sample.feature.novel.parser

import org.jsoup.Jsoup

object LnmtlParser : Parser {

  override fun parse(data: String): String {
    val sentences = Jsoup.parse(data).select("sentence[class=translated]")
    if (sentences.isEmpty()) {
      throw IllegalArgumentException("Response is different from what is expected.")
    }
    sentences.tagName("p") // replace sentence tags to paragraph
    sentences.removeAttr("class")

    sentences.select("w").unwrap() // remove w tags

    sentences.select("t").apply { // bold keywords
      removeAttr("data-title")
      tagName("strong")
    }

    sentences.select("dq").tagName("i") // italize quoted texts

    return sentences.outerHtml().replace("„", "“")
  }
}