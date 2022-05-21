package com.example.medicaleducation.feature_programs.domain.use_case

import android.util.Log
import com.example.medicaleducation.common.Constants.TAG
import com.example.medicaleducation.common.Resource
import com.example.medicaleducation.feature_programs.domain.model.Program
import com.example.medicaleducation.feature_programs.domain.repository.ProgramsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class GetAllProgramsUseCase @Inject constructor(
    private val repository: ProgramsRepository
){
    operator fun invoke() : Flow<Resource<List<Program>>> = flow {
        try {
            emit(Resource.Loading())
            val programs = repository.getAllPrograms()
            emit(Resource.Success(programs))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error has occurred"))
        } catch (e : IOException){
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            Log.e(TAG,e.toString())
        }
    }
}