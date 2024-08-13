package com.shawn.mvvmslideproject.model.source.repository.home

import com.shawn.mvvmslideproject.model.data.home.AttractionsResponse
import com.shawn.mvvmslideproject.model.source.remote.home.HomeRomeDataSource
import com.example.ig_slide_project.util.Resource
import com.shawn.mvvmslideproject.model.source.repository.home.HomeDomain
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepository @Inject constructor(private var homeRomeDataSource: HomeRomeDataSource) :
    HomeDomain {

    override fun getAttractions(page: Int, lang: String): Flow<Resource<AttractionsResponse>> {
        return flow {
            emit(Resource.Loading)
            val result = homeRomeDataSource.getAttractions(page, lang)
            result.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error(result.message(), result.code()))
        }
    }
}