package com.hotel.theconvo.data.remote.dto.req

data class GetPropertyReq(
    val pageData: PageData,
    val searchCriteria: SearchCriteria
)

data class SearchCriteria(
    val coordinates: Coordinates,
    val country: String,
    val is_active: Boolean,
    val query: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String,
    val radiusinKm: Int
)

data class PageData(
    val currentPageNo: Int,
    val pageSize: String
)
