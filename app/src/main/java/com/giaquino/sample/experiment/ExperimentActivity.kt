package com.giaquino.sample.experiment

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.giaquino.sample.R
import com.giaquino.sample.databinding.ExActivityBinding
import com.giaquino.sample.feature.novel.ChapterActivity

class ExperimentActivity : AppCompatActivity() {

  private lateinit var binding: ExActivityBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.ex_activity)
    binding.activity = this
    setSupportActionBar(binding.includeToolbar.toolbar)
  }

  fun onStateExperimentClick() {
    startActivity(Intent(this, ChapterActivity::class.java))
  }
}