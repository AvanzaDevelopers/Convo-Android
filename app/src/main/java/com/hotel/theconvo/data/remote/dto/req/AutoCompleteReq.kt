package com.hotel.theconvo.data.remote.dto.req

data class AutoCompleteReq(
    val pageData: AutoCompletePageData,
    val searchCriteria: AutoCompleteSearchCriteria,
    val sortby: String
)

data class AutoCompletePageData(
    val currentPageNo: String,
    val pageSize: String
)

data class AutoCompleteSearchCriteria(
    val coordinates: AutoCompleteCoordinates,
    val query: String
)

data class AutoCompleteCoordinates(
    val latitude: String,
    val longitude: String,
    val radiusinKm: Int
)