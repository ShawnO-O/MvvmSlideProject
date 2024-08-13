package com.shawn.mvvmslideproject.model.retrofitManager

import com.shawn.mvvmslideproject.model.data.home.AttractionsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    /**
     * 參考 https://travel.tycg.gov.tw/open-api/swagger/ui/index#/Travel/Travel_Attraction
     * **/
    @Headers("Content-Type:application/json; charset=UTF-8", "Accept:application/json")
    @GET("{lang}/Travel/Attraction")
    suspend fun getAttractions(
        @Path("lang") lang: String = "zh-tw",
        @Query("page") page: String = "1"
    ): Response<AttractionsResponse>
}