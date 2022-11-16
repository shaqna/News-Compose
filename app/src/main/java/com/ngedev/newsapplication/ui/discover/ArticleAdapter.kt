package com.ngedev.newsapplication.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ngedev.newsapplication.R
import com.ngedev.newsapplication.databinding.ItemArticleBinding
import com.ngedev.newsapplication.domain.model.Article
import com.ngedev.newsapplication.utils.ImageBinding
import com.ngedev.newsapplication.utils.TimestampConverter.formatTo
import com.ngedev.newsapplication.utils.TimestampConverter.toDate

class ArticleAdapter : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(DiffCallBack) {

    var onItemClickListener: ((Article) -> Unit)? = null

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            with(binding) {
                ImageBinding.bind(
                    itemView.context,
                    item.urlToImage ?: "",
                    R.drawable.loading_image,
                    articleImage
                )
                articleTitle.text = item.title
                item.author.let {
                    articleAuthor.text = it
                }
                articleDatePublished.text = item.publishedAt?.toDate()?.formatTo("MMM dd, yyyy")
                root.setOnClickListener {
                    onItemClickListener?.invoke(item)
                }
            }
        }

    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        getItem(position)?.let { holder.bind((it)) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val itemBinding =
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(itemBinding)
    }

    companion object {
        val DiffCallBack = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

        }
    }
}