package com.hotel.theconvo.data.remote.dto.req

data class HappeningNowReq(
    val searchCriteria: HappeningNowSearchCriteria
)

data class HappeningNowSearchCriteria(
    val city: String,
    val country: String,
    val hotel: String,
    val name: String,
    val pageNo: Int,
    val pageSize: Int
)