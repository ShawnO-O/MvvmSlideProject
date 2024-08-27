package com.shawn.mvvmslideproject.model.source.repository.home

import com.shawn.mvvmslideproject.model.data.home.AttractionsResponse
import com.shawn.mvvmslideproject.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAttractionsFirst(page: Int, lang: String): Flow<Resource<AttractionsResponse>>

    fun getAttractionsMore(page:Int,lang:String):Flow<Resource<AttractionsResponse>>

}