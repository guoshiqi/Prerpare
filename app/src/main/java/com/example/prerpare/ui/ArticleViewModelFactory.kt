package com.example.prerpare.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prerpare.data.MainListRepository

class ArticleViewModelFactory (private val repository: MainListRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainListViewModel::class.java)) {
            return MainListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}