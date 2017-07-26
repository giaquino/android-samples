package com.giaquino.sample.feature.novel.downloader

import com.giaquino.sample.feature.novel.Chapter
import com.giaquino.sample.feature.novel.client.Client
import com.giaquino.sample.feature.novel.parser.ParserFactory
import com.giaquino.sample.feature.novel.resolver.url.UrlResolverFactory

class ChapterDownloader(
    private val client: Client,
    private val parserFactory: ParserFactory,
    private val urlResolverFactory: UrlResolverFactory) {

  fun load(chapter: Chapter): String {
    val url = urlResolverFactory.createUrlResolver(chapter).resolveUrl(chapter)
    val response = client.request(url)
    return parserFactory.createParser(chapter).parse(response)
  }
}