package com.example.medicaleducation.feature_programs.data.repository

import com.example.medicaleducation.feature_programs.data.data_source.ProgramsService
import com.example.medicaleducation.feature_programs.domain.model.Program
import com.example.medicaleducation.feature_programs.domain.repository.ProgramsRepository
import retrofit2.Response
import javax.inject.Inject

class ProgramsRepositoryImpl @Inject constructor(
    private val api: ProgramsService
) : ProgramsRepository {
    override suspend fun getAllPrograms(): List<Program> {
        return api.getAllPrograms()
    }
}