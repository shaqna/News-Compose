package com.ngedev.newsapplication.data.source.remote.service



import com.ngedev.newsapplication.data.source.remote.response.Response
import com.ngedev.newsapplication.domain.model.Article
import com.ngedev.newsapplication.utils.Endpoints
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(Endpoints.EVERYTHING)
    suspend fun getArticleRelateWith(
        @Query("q") query: String = "tesla",
        @Query("page") page: Int?,
        @Query("pageSize") pageSize: Int?,
    ): Response<Article>

}