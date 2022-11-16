package com.ngedev.newsapplication.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.BuildCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ngedev.newsapplication.R
import com.ngedev.newsapplication.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplication.databinding.ActivityDetailBinding
import com.ngedev.newsapplication.domain.model.Article
import com.ngedev.newsapplication.ui.web.WebActivity
import com.ngedev.newsapplication.utils.DataMapper.toFavoriteEntity
import com.ngedev.newsapplication.utils.ImageBinding
import com.ngedev.newsapplication.utils.TimestampConverter.formatTo
import com.ngedev.newsapplication.utils.TimestampConverter.toDate
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@Suppress("DEPRECATION")
@BuildCompat.PrereleaseSdkCheck
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        loadKoinModules(DetailViewModel.inject())

        val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TAG, Article::class.java)
        } else {
            intent.getParcelableExtra(TAG)
        }
        article?.let {
            setArticleDetail(it)
        }

        onButtonListener(article)


    }


    private fun onButtonListener(article: Article?) {
        binding.apply {
            buttonBack.setOnClickListener {
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
            buttonShare.setOnClickListener {

            }
            buttonReadMore.setOnClickListener {
                Intent(this@DetailActivity, WebActivity::class.java).also { intent ->
                    article?.url?.let {
                        intent.putExtra(WebActivity.TAG, it )
                    }
                    startActivity(intent)
                }
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

    private fun setArticleDetail(article: Article) {
        Log.d("My Article", article.toString())
        viewModel.getArticleByTitle(article.title ?: "")
        with(binding) {
            detailArticleTitle.text = article.title
            ImageBinding.bind(
                this@DetailActivity,
                article.urlToImage ?: "",
                R.drawable.loading_image,
                detailArticleImage
            )
            detailArticleDescription.text = article.description
            detailArticleAuthor.text = article.author
            detailArticleDatePublished.text = article.publishedAt?.toDate()?.formatTo("MMM dd, yyyy")
            detailArticleContent.text = article.content


            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isFavorite.collectLatest {
                        Log.d("Status Favorite",it.toString())
                        var statusFavorite = it
                        setBookmarkButtonState(statusFavorite)
                        binding.buttonBookmark.setOnClickListener {
                            statusFavorite = !statusFavorite
                            if (statusFavorite) {
                                val favoriteArticle = article.toFavoriteEntity(true)
                                viewModel.addArticleFavorite(favoriteArticle)
                            } else {
                                val favoriteArticle = article.toFavoriteEntity(false)
                                viewModel.deleteArticleFavorite(favoriteArticle)
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