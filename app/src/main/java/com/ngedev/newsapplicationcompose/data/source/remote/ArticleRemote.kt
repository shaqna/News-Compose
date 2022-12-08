package com.ngedev.newsapplicationcompose.data.source.remote


import com.ngedev.newsapplicationcompose.data.source.remote.service.ApiService
import org.koin.dsl.module

class ArticleRemote(private val apiService: ApiService) {
    suspend fun getArticleRelateWith(query: String, page: Int? = null, pageSize: Int? = null) =
        apiService.getArticleRelateWith(query, page, pageSize)

    companion object {
        fun inject() = module {
            single {
                ArticleRemote(get())
            }
        }
    }
}