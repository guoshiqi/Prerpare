package com.example.prerpare.ui.mainView

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel: ViewModel() {

    private val _keyWordState= MutableStateFlow("")
    val keyWordState: StateFlow<String> = _keyWordState

    fun onKeyWordChanged(keyWord: String){
        _keyWordState.value=keyWord

    }
}