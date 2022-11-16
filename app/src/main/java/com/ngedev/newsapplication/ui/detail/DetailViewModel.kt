package com.ngedev.newsapplication.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ngedev.newsapplication.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplication.domain.model.Article
import com.ngedev.newsapplication.domain.usecase.ArticleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class DetailViewModel(private val useCase: ArticleUseCase) : ViewModel() {

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

    fun getArticleByTitle(title: String) {
        viewModelScope.launch {
            useCase.getArticleByTitle(title).collect {
                when(it) {
                    is BookmarkState.HasData -> {
                        _isFavorite.value = true
                    }
                    is BookmarkState.Empty -> {
                        _isFavorite.value = false
                    }
                    else -> {}
                }
            }
        }
    }

    fun addArticleFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            useCase.insertFavoriteArticle(favorite)
        }
    }

    fun deleteArticleFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            useCase.deleteFavoriteArticle(favorite)
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::DetailViewModel)
        }
    }

}

sealed class BookmarkState<out T>(val data: T? = null, val message: String? = null) {
    class HasData<T>(data: T?): BookmarkState<T>(data)
    class Empty<T>(message: String?, data: T? = null): BookmarkState<T>(data, message)
}