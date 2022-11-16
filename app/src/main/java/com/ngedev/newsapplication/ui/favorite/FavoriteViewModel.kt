package com.ngedev.newsapplication.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngedev.newsapplication.data.source.local.entity.FavoriteEntity
import com.ngedev.newsapplication.domain.usecase.ArticleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf

class FavoriteViewModel(private val useCase: ArticleUseCase) : ViewModel() {

    private val _favorites = MutableStateFlow<List<FavoriteEntity>>(emptyList())
    val favorites: StateFlow<List<FavoriteEntity>> = _favorites

    fun getAllFavorites() {
        viewModelScope.launch {
            useCase.loadFavoriteArticles().collect { listFavorite ->
                _favorites.value = listFavorite
            }
        }
    }

    fun deleteArticleFavorite(favorite: FavoriteEntity) {
        viewModelScope.launch {
            useCase.deleteFavoriteArticle(favorite)
        }
    }

    companion object {
        fun inject() = module {
            viewModelOf(::FavoriteViewModel)
        }
    }
}