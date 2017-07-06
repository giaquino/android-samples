package com.giaquino.sample.experiment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.giaquino.sample.R
import com.giaquino.sample.databinding.ExActivityBinding
import com.giaquino.sample.experiment.state.ExStateActivity

class ExperimentActivity : AppCompatActivity() {

  private lateinit var binding: ExActivityBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.ex_activity)
    binding.activity = this
    setSupportActionBar(binding.includeToolbar.toolbar)
  }

  fun onStateExperimentClick() {
    startActivity(ExStateActivity.createIntent(this, "foobar"))
  }
}