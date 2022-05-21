package com.example.medicaleducation.feature_programs.domain.model


import com.google.gson.annotations.SerializedName

data class Media(
    val icon: String,
    val title: String,
    val type: String,
    val url: String
)