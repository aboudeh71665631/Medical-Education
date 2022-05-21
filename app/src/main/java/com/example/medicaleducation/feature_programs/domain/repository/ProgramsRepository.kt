package com.example.medicaleducation.feature_programs.domain.repository

import com.example.medicaleducation.feature_programs.domain.model.Program
import retrofit2.Response

interface ProgramsRepository {
    suspend fun getAllPrograms(): List<Program>
}