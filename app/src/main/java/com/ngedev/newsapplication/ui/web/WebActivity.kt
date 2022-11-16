package com.ngedev.newsapplication.ui.web

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ngedev.newsapplication.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(TAG)
        binding.webView.loadUrl(url ?: "")
    }

    companion object {
        const val TAG = "WEB_VIEW"
    }
}