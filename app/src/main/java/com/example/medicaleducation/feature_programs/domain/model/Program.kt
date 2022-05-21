package com.example.medicaleducation.feature_programs.domain.model


import com.google.gson.annotations.SerializedName

data class Program(
    val media: List<Media>,
    val title: String
)