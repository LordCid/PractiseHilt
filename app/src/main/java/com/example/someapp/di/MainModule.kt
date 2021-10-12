package com.example.someapp.di

import com.example.someapp.data.ApiService
import com.example.someapp.data.NetworkDataSource
import com.example.someapp.data.NetworkDataSourceImpl
import com.example.someapp.domain.GetDataListUseCase
import com.example.someapp.domain.GetDataListUseCaseImpl
import com.example.someapp.domain.GetDataUseCase
import com.example.someapp.domain.GetDataUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@InstallIn(ActivityComponent::class)
@InstallIn(SingletonComponent::class)
@Module(includes = [ApplicationProviderModule::class])
class MainModule {
    @Provides
    @Singleton
    fun providesNetworkDataSource(apiService: ApiService): NetworkDataSource {
        return NetworkDataSourceImpl(apiService)
    }
    @Provides
    @Singleton
    fun providesGetDataListUseCase(networkDataSource: NetworkDataSource): GetDataListUseCase {
        return GetDataListUseCaseImpl(networkDataSource)
    }

    @Provides
    @Singleton
    fun providesGetDataUseCase(networkDataSource: NetworkDataSource): GetDataUseCase {
        return GetDataUseCaseImpl(networkDataSource)
    }
}