package com.shawn.mvvmslideproject.model.source.remote.home

import com.shawn.mvvmslideproject.model.data.home.AttractionsResponse
import com.shawn.mvvmslideproject.model.retrofitManager.APIService
import retrofit2.Response
import javax.inject.Inject


class HomeRomeDataSource @Inject constructor (private val apiService: APIService) {
    suspend fun getAttractions(page: Int, lang: String): Response<AttractionsResponse>{
        return apiService.getAttractions(lang,page.toString())
    }
}