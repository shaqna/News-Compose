package com.ngedev.newsapplication.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ngedev.newsapplication.data.source.remote.response.Response

abstract class PagingRemoteSource<ResultType:Any, RequestType>: PagingSource<Int, ResultType>(){

    override fun getRefreshKey(state: PagingState<Int, ResultType>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultType> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = createCall(nextPageNumber)
            LoadResult.Page(
                data = mapToResult(response.articles),
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    abstract suspend fun createCall(page: Int): Response<RequestType>

    abstract fun mapToResult(response: List<RequestType>): List<ResultType>

}