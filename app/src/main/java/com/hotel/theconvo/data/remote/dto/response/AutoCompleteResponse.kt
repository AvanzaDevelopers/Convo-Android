package com.hotel.theconvo.data.remote.dto.response

data class AutoCompleteResponse(
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: String,
    val suggestedLocations: SuggestedLocations,
    val timestamp: String
)

data class SuggestedLocations(
    val action: String,
    val cache: Boolean,
    val pageData: AutoCompletePageData,
    val searchKeyword: String,
    val searchResult: List<AutoCompleteSearchResult>
)

data class AutoCompleteSearchResult(
    val address: String,
    val city: String,
    val label: String,
    val latitude: Double,
    val location: String,
    val longitude: Double
)

data class AutoCompletePageData(
    val pageSize: Int,
    val recordsCount: Int
)