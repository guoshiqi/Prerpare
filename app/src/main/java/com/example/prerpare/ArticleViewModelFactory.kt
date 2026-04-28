package com.example.prerpare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.prerpare.data.MainListRepository
import com.example.prerpare.ui.mainlist.MainListViewModel

class ArticleViewModelFactory (private val repository: MainListRepository): ViewModelProvider.Factory {
    //建立带repository的viewmodel
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainListViewModel::class.java)) {
            return MainListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}