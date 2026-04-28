package com.example.prerpare.ui

// 定义 UI State
sealed class UiState<out T> {
    object Loading : UiState<Nothing>() // 加载中
    data class Success<out T>(val data: T) : UiState<T>() // 成功，并返回数据
    data class Error(val message: String) : UiState<Nothing>() // 错误，带错误消息
    object Empty: UiState<Nothing>()//空页面
}