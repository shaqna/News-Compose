package com.ngedev.newsapplicationcompose.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngedev.newsapplicationcompose.ui.navigation.Screen
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

// create splash screen viewmodel
class SplashViewModel : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Discover.route)
    val startDestination: State<String> = _startDestination

    companion object{
        fun inject() = module {
            viewModelOf(::SplashViewModel)
        }
    }
}