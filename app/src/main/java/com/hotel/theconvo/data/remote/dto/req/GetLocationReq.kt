package com.hotel.theconvo.data.remote.dto.req

data class GetLocationReq(
    val action: String,
    val actionType: String,
    val isui: Boolean,
    val typeData: List<String>
)