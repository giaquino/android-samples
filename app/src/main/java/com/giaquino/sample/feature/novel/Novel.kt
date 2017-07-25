package com.giaquino.sample.feature.novel

data class Novel(val source: Source, val title: String, val volume: Int, val chapter: Int) {

  enum class Source {
    WUXIA_WORLD, LNMTL
  }

  companion object {
    const val NO_VOLUME = -1
  }

  fun isVolumeAvailable() = volume != NO_VOLUME

  fun arguments() = arrayOf(title, volume, chapter)
}