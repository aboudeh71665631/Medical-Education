package com.example.medicaleducation.feature_programs.data.data_source

import com.example.medicaleducation.common.Constants
import com.example.medicaleducation.feature_programs.domain.model.Program
import retrofit2.http.GET

interface ProgramsService {
    @GET(Constants.URL_QUERY)
    suspend fun getAllPrograms() : List<Program>
}