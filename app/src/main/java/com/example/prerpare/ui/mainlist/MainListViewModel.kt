package com.example.prerpare.ui.mainlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainListViewModel(private val repository: MainListRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState<List<Article>>>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState<List<Article>>> = _uiState

    fun loadArticles(network: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ListUiState.Loading
            val result = repository.getArticles(network)
            result.fold(
                onSuccess = { data ->
                    if (data.isEmpty()) {
                        _uiState.value = ListUiState.Empty
                    } else {
                        _uiState.value = ListUiState.Success(data)
                    }
                },
                onFailure = { exception ->
                    _uiState.value = ListUiState.Error(exception.message ?: "error")
                }
            )
        }
    }
}