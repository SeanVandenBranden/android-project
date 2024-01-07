package com.example.androidproject.model.breweries

import com.google.gson.annotations.SerializedName

data class Brewery(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("brewery_type") val breweryType: String?,
    @SerializedName("address_1") val address1: String?,
    @SerializedName("address_2") val address2: String?,
    @SerializedName("address_3") val address3: String?,
    @SerializedName("city") val city: String?,
    @SerializedName("state_povince") val stateProvince: String?,
    @SerializedName("postal_code") val postalCode: String?,
    @SerializedName("country") val country: String?,
    @SerializedName("longitude") val longitude: String?,
    @SerializedName("latitude") val latitude: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("website_url") val websiteUrl: String?,
    @SerializedName("state") val state: String?,
    @SerializedName("street") val street: String?,
)
