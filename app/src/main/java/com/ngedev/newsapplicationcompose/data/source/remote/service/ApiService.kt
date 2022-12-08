package com.ngedev.newsapplicationcompose.data.source.remote.service



import com.ngedev.newsapplicationcompose.data.source.remote.response.Response
import com.ngedev.newsapplicationcompose.domain.model.Article
import com.ngedev.newsapplicationcompose.utils.Endpoints
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