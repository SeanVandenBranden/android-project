package com.example.androidproject.model.breweries

data class Brewery(
    val id: String,
    val name: String,
    val breweryType: String,
    val address1: String,
    val address2: String?,
    val address3: String?,
    val city: String,
    val stateProvince: String,
    val postalCode: String,
    val country: String,
    val longitude: String,
    val latitude: String,
    val phone: String,
    val websiteUrl: String,
    val state: String,
    val street: String,
)
