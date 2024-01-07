package com.example.androidproject.ui.screens.breweriesmetadata

import com.example.androidproject.model.metadata.Metadata

data class BreweriesMetadataState(
    val isError: Boolean = false,
    var metadata: Metadata? = null,
)
