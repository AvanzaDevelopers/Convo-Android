package com.hotel.theconvo.data.remote.dto.response

data class GetLocationResponse(
    val typeData: TypeData
)


data class TypeData(
    val action: String,
    val data: LocationData
)

data class LocationData(
    val countries_and_flags: List<CountriesAndFlag>
)

data class CountriesAndFlag(
    val _alpha2Code: String,
    val _alpha3Code: String,
    val _value: String,
    val alpha2Code: String,
    val alpha3Code: String,
    val countryid: Int,
    val flag: String,
    val name: String,
    val namear: String,
    val value: String
)

