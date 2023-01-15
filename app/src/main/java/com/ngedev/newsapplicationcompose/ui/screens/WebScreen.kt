package com.ngedev.newsapplicationcompose.ui.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen(url: String?) {
    AndroidView(factory = {
        WebView(it).apply {
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
    }, update = {
        it.loadUrl(url ?: "")
    })
}