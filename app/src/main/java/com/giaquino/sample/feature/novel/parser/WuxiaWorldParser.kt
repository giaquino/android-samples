package com.giaquino.sample.feature.novel.parser

import org.jsoup.Jsoup

object WuxiaWorldParser : Parser {

  override fun parse(data: String): String {
    val elements = Jsoup.parse(data).getElementsByAttributeValue("itemprop", "articleBody")

    if (elements.isEmpty()) {
      throw IllegalArgumentException("Response is different from what is expected.")
    }

    /* remove navigation */
    elements.select("a[href]").remove()
    elements.select("span").remove()
    elements.select("hr").remove()

    /* remove empty paragraph */
    elements.select("p").filter { !it.hasText() }.forEach { it.remove() }

    /* remove trailing whitespace */
    return elements.html().replace("> ", ">")
  }
}