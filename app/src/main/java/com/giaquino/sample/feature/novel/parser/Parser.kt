package com.giaquino.sample.feature.novel.parser

/**
 * Parses data to return its content.
 */
interface Parser {
  fun parse(data: String): String
}