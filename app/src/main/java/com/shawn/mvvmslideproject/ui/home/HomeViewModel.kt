package com.shawn.mvvmslideproject.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shawn.mvvmslideproject.model.source.repository.home.HomeRepository
import com.shawn.mvvmslideproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) : ViewModel() {

    private val _stateFlow = MutableStateFlow("")
    val stateFlow : StateFlow<String> = _stateFlow

    fun getAttractions(page: Int, lang: String="zh-tw") =
        viewModelScope.launch {
            homeRepository.getAttractions(page, lang).collect{
                when(it){
                    is Resource.Success ->{
                        _stateFlow.value = it.data.infos.declaration.orgname.toString()
                    }
                    is Resource.Error->{

                    }
                    is Resource.Loading->{

                    }
                }
                Log.d("shawnTest","it:$it")
//                stateFlow.value =
            }
        }

}