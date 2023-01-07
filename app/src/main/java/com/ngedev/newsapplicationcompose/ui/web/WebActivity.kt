package com.ngedev.newsapplicationcompose.ui.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ngedev.newsapplicationcompose.databinding.ActivityWebBinding

@SuppressLint("SetJavaScriptEnabled")
class WebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(TAG)
        binding.webView.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.apply {
                javaScriptEnabled = true
                userAgentString = System.getProperty("http.agent")
            }
            loadUrl(url ?: "")
        }
    }

    companion object {
        const val TAG = "WEB_VIEW"
    }
}