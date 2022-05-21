package com.example.medicaleducation.di

import com.example.medicaleducation.common.Constants
import com.example.medicaleducation.feature_programs.data.data_source.ProgramsService
import com.example.medicaleducation.feature_programs.data.repository.ProgramsRepositoryImpl
import com.example.medicaleducation.feature_programs.domain.repository.ProgramsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): ProgramsService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProgramsService::class.java)
    }

    @Singleton
    @Provides
    fun provideProgramsRepository(api : ProgramsService) : ProgramsRepository {
        return ProgramsRepositoryImpl(api)
    }


}