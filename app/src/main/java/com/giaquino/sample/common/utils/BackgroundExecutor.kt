package com.giaquino.sample.common.utils

import android.os.Process
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class BackgroundExecutor : Executor {

  private val executor = Executors.newCachedThreadPool { r ->
    Thread(r).apply {
      priority = Process.THREAD_PRIORITY_BACKGROUND
    }
  }

  override fun execute(command: Runnable) {
    executor.execute(command)
  }
}