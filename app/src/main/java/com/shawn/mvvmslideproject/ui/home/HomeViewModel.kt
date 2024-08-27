package com.shawn.mvvmslideproject.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.model.data.home.Info
import com.shawn.mvvmslideproject.model.source.repository.home.HomeRepositoryImpl
import com.shawn.mvvmslideproject.ui.base.BaseViewModel
import com.shawn.mvvmslideproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepositoryImpl: HomeRepositoryImpl) :
    BaseViewModel() {

    private val _stateFlow = MutableStateFlow("")
    val stateFlow: StateFlow<String> = _stateFlow
    private val _attractions = MutableStateFlow(mutableListOf<Info>())
    val attractions: StateFlow<MutableList<Info>> = _attractions
    private var page = 1
    fun getAttractionsFirst(lang: String = "zh-tw") =
        viewModelScope.launch {
            //if you call this api ，it means you want to refresh the data. so the page should be 1.
            page = 1
            homeRepositoryImpl.getAttractionsFirst(page, lang).collect {
                when (it) {
                    is Resource.Success -> {
                        _stateFlow.value = it.data.infos.declaration.orgname.toString()
                        _attractions.value = it.data.infos.info
                    }

                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }
                }
                Log.d("shawnTest", "it:$it")
//                stateFlow.value =
            }
        }

    fun getAttractionsMore( lang: String = "zh-tw") =
        viewModelScope.launch {
            homeRepositoryImpl.getAttractionsMore(++page, lang).collect {
                when (it) {
                    is Resource.Success -> {
                        _attractions.value.addAll(_attractions.value.size-1,it.data.infos.info)
                    }

                    is Resource.Error -> {
                        _toastShardFlow.emit(it.message)
                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }

}