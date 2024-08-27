package com.shawn.mvvmslideproject.ui.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
open class BaseViewModel@Inject constructor():ViewModel() {
    val _toastShardFlow = MutableSharedFlow<String>()
    val toastSharedFlow : SharedFlow<String> = _toastShardFlow
    val _finishSharedFlow = MutableSharedFlow<Boolean>()
    val finishSharedFlow : SharedFlow<Boolean> = _finishSharedFlow
    val _snackBarShardFlow = MutableSharedFlow<String>()
    val snackBarSharedFlow : SharedFlow<String> = _snackBarShardFlow

    override fun onCleared() {
        super.onCleared()

    }


    fun clearToastMessage(){
        _toastShardFlow.resetReplayCache()
    }
}