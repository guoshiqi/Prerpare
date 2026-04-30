package com.example.prerpare.ui.mainlist

// 定义 UI State
sealed class ListUiState<out T> {
    object Loading : ListUiState<Nothing>() // 加载中
    data class Success<out T>(val data: T) : ListUiState<T>() // 成功，并返回数据
    data class Error(val message: String) : ListUiState<Nothing>() // 错误，带错误消息
    object Empty: ListUiState<Nothing>()//空页面
}