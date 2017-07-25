package com.giaquino.sample.feature.novel.parser

import org.jsoup.Jsoup

object WuxiaWorldParser : Parser {

  override fun parse(data: String): String {
    val elements = Jsoup.parse(data).getElementsByAttributeValue("itemprop", "articleBody")
    if (elements.isEmpty()) {
      throw IllegalArgumentException("Response is different from what is expected.")
    }
    elements.select("a[href]").remove() // remove navigation
    elements.select("span").remove()
    elements.select("hr").remove()
    elements.select("p").filter { !it.hasText() }.forEach { it.remove() } // remove empty paragraph
    return elements.html().replace("> ", ">") // remove trailing whitespace
  }
}