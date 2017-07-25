package com.giaquino.sample.feature.novel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.giaquino.sample.R
import com.giaquino.sample.common.utils.BackgroundExecutor
import com.giaquino.sample.databinding.NovelActivityBinding
import com.giaquino.sample.feature.novel.Novel.Source.LNMTL
import com.giaquino.sample.feature.novel.client.OkHttpClient
import com.giaquino.sample.feature.novel.downloader.NovelDownloader
import com.giaquino.sample.feature.novel.parser.ParserFactory
import com.giaquino.sample.feature.novel.resolver.UrlResolverFactory

class NovelActivity : AppCompatActivity() {

  var volume = 5
  var chapter = 401
  lateinit var binding: NovelActivityBinding
  lateinit var downloader: NovelDownloader

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView<NovelActivityBinding>(this, R.layout.novel_activity)
    downloader = NovelDownloader(OkHttpClient(), ParserFactory, UrlResolverFactory)
    binding.content.setOnClickListener {
      download()
    }
    download()
  }

  fun download() {
    BackgroundExecutor().execute {
      downloader.load(Novel(LNMTL, "against-the-gods", volume, chapter)).apply {
        runOnUiThread {
          binding.content.text = Html.fromHtml(this)
        }
      }
      chapter++
    }
  }
}