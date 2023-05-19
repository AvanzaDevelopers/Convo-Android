package com.hotel.theconvo.data.remote.dto.response

data class BookingApiResponse(
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: String,
    val timestamp: String
)