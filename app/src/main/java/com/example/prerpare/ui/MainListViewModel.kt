package com.example.prerpare.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.data.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainListViewModel(private val repository: MainListRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun loadArticles(network: Boolean) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.getArticles(network)
            result.fold(
                onSuccess = { data ->
                    if (data.isEmpty()) {
                        _uiState.value = UiState.Empty
                    } else {
                        _uiState.value = UiState.Success(data)
                    }
                },
                onFailure = { exception ->
                    _uiState.value = UiState.Error(exception.message ?: "error")
                }
            )
        }
    }
}