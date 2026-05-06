package com.example.prerpare.ui.mainlist

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.data.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException


class MainListViewModel(private val repository: MainListRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ListUiState<List<Article>>>(ListUiState.Loading)
    val uiState: StateFlow<ListUiState<List<Article>>> = _uiState

    private val _eventFlow = MutableSharedFlow<EventHint>()

    val eventFlow: SharedFlow<EventHint> = _eventFlow

    fun loadArticles(network: Boolean) {
        viewModelScope.launch {
            _uiState.value = ListUiState.Loading
            try {
                val result = withContext(Dispatchers.IO) {
                    repository.getArticles(network)
                }
                result.fold(
                    onSuccess = { data ->
                        if (data.isEmpty()) {
                            _uiState.value = ListUiState.Empty
                        } else {
                            _uiState.value = ListUiState.Success(data)
                            _eventFlow.emit(EventHint.Hint("success"))
                        }
                    },
                    onFailure = { exception ->
                        _uiState.value = ListUiState.Error(exception.message ?: "error")
                        _eventFlow.emit(EventHint.Hint(exception.message ?: "error"))
                    }
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = ListUiState.Error(e.message ?: "加载失败")
                _eventFlow.emit(EventHint.Hint(e.message ?: "加载失败"))
            }

        }
    }

    fun jumToNext(data: Parcelable){
        viewModelScope.launch {
            _eventFlow.emit(EventHint.NavigateToDetail(data))
        }
    }
}