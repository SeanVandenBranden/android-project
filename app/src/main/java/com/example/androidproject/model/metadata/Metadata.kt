package com.example.androidproject.model.metadata

import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("total") val total: String,
    @SerializedName("page") val page: String,
    @SerializedName("per_page") val perPage: String?,
)