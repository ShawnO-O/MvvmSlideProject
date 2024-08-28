package com.shawn.mvvmslideproject.model.source.repository.home

import com.shawn.mvvmslideproject.MvvmSlideProjectApplication
import com.shawn.mvvmslideproject.R
import com.shawn.mvvmslideproject.model.data.home.AttractionsResponse
import com.shawn.mvvmslideproject.model.source.remote.home.HomeRomeDataSource
import com.shawn.mvvmslideproject.util.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ActivityRetainedScoped
class HomeRepositoryImpl @Inject constructor(private var homeRomeDataSource: HomeRomeDataSource) :
    HomeRepository {

    override fun getAttractionsFirst(page: Int, lang: String): Flow<Resource<AttractionsResponse>> {
        return flow {
            emit(Resource.Loading)
            val result = homeRomeDataSource.getAttractions(page, lang)
            result.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error(result.message(), result.code()))
        }
    }

    override fun getAttractionsMore(page: Int, lang: String): Flow<Resource<AttractionsResponse>> {
        return flow {
            emit(Resource.Loading)
            val result = homeRomeDataSource.getAttractions(page, lang)
            result.body()?.let {
                emit(Resource.Success(it))
            } ?: emit(Resource.Error(MvvmSlideProjectApplication.getStringResource(R.string.list_data_empty), result.code()))
        }
    }
}