package com.ngedev.newsapplication.data.repository

import androidx.paging.*
import com.ngedev.newsapplication.data.source.local.LocalDataSource
import com.ngedev.newsapplication.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplication.data.source.remote.ArticleRemote
import com.ngedev.newsapplication.data.source.remote.RemoteMediator
import com.ngedev.newsapplication.data.source.remote.response.Response
import com.ngedev.newsapplication.domain.model.Article
import com.ngedev.newsapplication.domain.model.RemoteKeys
import com.ngedev.newsapplication.domain.repository.IArticleRepository
import com.ngedev.newsapplication.ui.detail.BookmarkState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

class ArticleRepository(
    private val remote: ArticleRemote,
    private val local: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : IArticleRepository {


    @OptIn(ExperimentalPagingApi::class)
    override fun getArticleRelateWith(query: String): Flow<PagingData<Article>> =
        Pager(
            config = PagingConfig(10, enablePlaceholders = true, initialLoadSize = 10),
            remoteMediator = object :
                RemoteMediator<Article, Response<Article>, LocalDataSource>() {
                override suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Article>): RemoteKeys? =
                    state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                        ?.let { data ->
                            database.getRemoteKeysId(data.id)
                        }

                override suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Article>): RemoteKeys? =
                    state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
                        ?.let { data ->
                            database.getRemoteKeysId(data.id)
                        }

                override suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Article>): RemoteKeys? =
                    state.anchorPosition?.let { position ->
                        state.closestItemToPosition(position)?.id?.let { id ->
                            database.getRemoteKeysId(id)
                        }
                    }

                override suspend fun createCall(page: Int, size: Int): Response<Article> =
                    remote.getArticleRelateWith(query)

                override val database: LocalDataSource
                    get() = local

                override suspend fun createDatabaseWithTransaction(
                    database: LocalDataSource,
                    loadType: LoadType,
                    response: Response<Article>,
                    page: Int
                ): Boolean {
                    val endOfPaginationReached = response.articles.isEmpty()
                    database.runAsTransaction {
                        if (loadType == LoadType.REFRESH) {
                            database.deleteRemoteKeys()
                            database.deleteAllArticle()
                        }
                        val prevKey = if (page == 1) null else page - 1
                        val nextKey = if (endOfPaginationReached) null else page + 1
                        val keys = response.articles.map {
                            RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
                        }

                        database.insertAll(keys)

                        response.articles.forEach {
                            database.insertArticle(it)
                        }
                    }

                    return endOfPaginationReached
                }

            },
            pagingSourceFactory = {
                local.getAllArticles()
            }
        ).flow


    override fun loadFavoriteArticles(): Flow<List<FavoriteEntity>> =
        local.getAllFavoriteArticles()

    override fun getArticleByTitle(title: String): Flow<BookmarkState<Article>> = flow {
        try {
            val fetchingResponse = local.getArticleByTitle(title).firstOrNull()
            if (fetchingResponse == null) {
                emit(BookmarkState.Empty("no data"))
            } else {
                emit(BookmarkState.HasData(fetchingResponse))
            }
        } catch (e: Exception) {
            emit(BookmarkState.Empty("no data"))
        }
    }.flowOn(ioDispatcher)


    override suspend fun insertFavoriteArticle(favoriteEntity: FavoriteEntity) {
        withContext(ioDispatcher) {
            local.insertFavoriteArticle(favoriteEntity)
        }
    }

    override suspend fun deleteFavoriteArticle(favoriteEntity: FavoriteEntity) {
        withContext(ioDispatcher) {
            local.deleteFavoriteArticle(favoriteEntity.title)
        }
    }

    companion object {
        private const val ioDispatcherName = "IODispatchers"
        fun inject() = module {
            single(named(ioDispatcherName)) {
                Dispatchers.IO
            }
            single<IArticleRepository> {
                ArticleRepository(get(), get(), get(named(ioDispatcherName)))
            }
        }
    }


}