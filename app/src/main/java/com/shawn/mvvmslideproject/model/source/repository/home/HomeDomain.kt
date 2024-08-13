package com.shawn.mvvmslideproject.model.source.repository.home

import com.shawn.mvvmslideproject.model.data.home.AttractionsResponse
import com.example.ig_slide_project.util.Resource
import kotlinx.coroutines.flow.Flow

interface HomeDomain {
    fun getAttractions(page: Int, lang: String): Flow<Resource<AttractionsResponse>>
}