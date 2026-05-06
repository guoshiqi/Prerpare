package com.example.prerpare.ui.mainlist

import android.os.Parcelable

sealed class EventHint {
    data class Hint(val message:String) : EventHint()

    data class NavigateToDetail(val data: Parcelable) : EventHint()
}