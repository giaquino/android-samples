package com.giaquino.sample.feature.novel.downloader

import com.giaquino.sample.feature.novel.Novel
import com.giaquino.sample.feature.novel.client.Client
import com.giaquino.sample.feature.novel.parser.ParserFactory
import com.giaquino.sample.feature.novel.resolver.UrlResolverFactory

class NovelDownloader(
    private val client: Client,
    private val parserFactory: ParserFactory,
    private val urlResolverFactory: UrlResolverFactory) {

  fun load(novel: Novel): String {
    val url = urlResolverFactory.createUrlResolver(novel).resolveUrl(novel)
    val response = client.request(url)
    return parserFactory.createParser(novel).parse(response)
  }
}