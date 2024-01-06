package com.example.androidproject.ui.screens.breweriesoverview

import com.example.androidproject.model.breweries.Brewery

data class BreweriesOverviewState(
    val isRefreshing: Boolean = false,
    val breweries: List<Brewery>?,
    val isError: Boolean = false,
)
