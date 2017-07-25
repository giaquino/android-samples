package com.giaquino.sample.feature.novel.parser

import com.giaquino.sample.loadResourceAsString
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test

class WuxiaWorldParserTest {

  val parser = WuxiaWorldParser

  @Test fun validWuxiaWorldResponse() {
    val data = loadResourceAsString("response/wuxia_world_novel_response.txt")
    val content = loadResourceAsString("response/wuxia_world_novel_response_content.txt")
    assertThat(data).isNotBlank()
    assertThat(content).isNotBlank()
    assertThat(parser.parse(data)).isEqualTo(content)
  }

  @Test(expected = IllegalArgumentException::class) fun validGoogleResponse() {
    parser.parse(loadResourceAsString("response/google_response.txt"))
  }

  @Test(expected = IllegalArgumentException::class) fun invalidHtmlResponse() {
    parser.parse(loadResourceAsString("response/abc.txt"))
  }
}