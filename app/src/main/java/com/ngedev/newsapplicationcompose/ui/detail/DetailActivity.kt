package com.ngedev.newsapplicationcompose.ui.detail

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.core.os.BuildCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ngedev.newsapplicationcompose.R
import com.ngedev.newsapplicationcompose.databinding.ActivityDetailBinding
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.ui.detail.components.DetailContent
import com.ngedev.newsapplicationcompose.utils.DataMapper.toFavoriteEntity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
@BuildCompat.PrereleaseSdkCheck
class DetailActivity : ComponentActivity() {

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        loadKoinModules(DetailViewModel.inject())

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TAG, Article::class.java)
        } else {
            intent.getParcelableExtra(TAG)
        }

        onButtonListener()
        setArticleContent(article)
    }

    private fun onButtonListener() {
        binding.apply {
            with(buttonBack) {
                setImageResource(R.drawable.ic_baseline_arrow_back_24)
                setOnClickListener {
                    if (BuildCompat.isAtLeastT()) {
                        onBackPressedDispatcher.addCallback(
                            this@DetailActivity,
                            object : OnBackPressedCallback(true) {
                                override fun handleOnBackPressed() {
                                    finish()
                                }
                            })
                    } else {
                        onBackPressed()
                    }

                }
            }
            with(buttonShare) {
                setImageResource(R.drawable.ic_baseline_share_24)
                setOnClickListener {  }
            }


        }
    }

    private fun setBookmarkButtonState(it: Boolean) {
        if (it) {
            binding.buttonBookmark.setImageResource(R.drawable.ic_baseline_bookmark_added_24)
        } else {
            binding.buttonBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24)
        }
    }

    private fun setArticleContent(article: Article?) {
        viewModel.getArticleByTitle(article?.title ?: "")
        with(binding) {
            detailArticle.setContent {
                MaterialTheme {
                    article?.let {
                        Column() {
                            DetailContent(article = it)
                        }
                    }
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isFavorite.collectLatest {
                        Log.d("Status Favorite",it.toString())
                        var statusFavorite = it
                        setBookmarkButtonState(statusFavorite)
                        binding.buttonBookmark.setOnClickListener {
                            statusFavorite = !statusFavorite
                            if (statusFavorite) {
                                val favoriteArticle = article?.toFavoriteEntity(true)
                                viewModel.addArticleFavorite(favoriteArticle!!)
                            } else {
                                val favoriteArticle = article?.toFavoriteEntity(false)
                                viewModel.deleteArticleFavorite(favoriteArticle!!)
                            }
                            setBookmarkButtonState(statusFavorite)
                        }
                    }
                }
            }

        }
    }

    companion object {
        const val TAG = "DETAIL_ACTIVITY"
    }
}
