package com.giaquino.sample.feature.novel

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html
import com.giaquino.sample.R
import com.giaquino.sample.common.utils.BackgroundExecutor
import com.giaquino.sample.databinding.NovelActivityBinding
import com.giaquino.sample.feature.novel.Chapter.Source.LNMTL
import com.giaquino.sample.feature.novel.client.OkHttpClient
import com.giaquino.sample.feature.novel.downloader.ChapterDownloader
import com.giaquino.sample.feature.novel.parser.ParserFactory
import com.giaquino.sample.feature.novel.resolver.url.UrlResolverFactory

class ChapterActivity : AppCompatActivity() {

  var volume = 5
  var chapter = 401
  lateinit var binding: NovelActivityBinding
  lateinit var downloader: ChapterDownloader

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView<NovelActivityBinding>(this, R.layout.novel_activity)
    downloader = ChapterDownloader(OkHttpClient(), ParserFactory,
        UrlResolverFactory)
    binding.content.setOnClickListener {
      download()
    }
    download()
  }

  fun download() {
    BackgroundExecutor().execute {
      downloader.load(Chapter(LNMTL, "against-the-gods", volume, chapter)).apply {
        runOnUiThread {
          binding.content.text = Html.fromHtml(this)
        }
      }
      chapter++
    }
  }
}