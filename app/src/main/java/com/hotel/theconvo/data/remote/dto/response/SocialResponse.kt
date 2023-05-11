package com.hotel.theconvo.data.remote.dto.response

data class SocialResponse(
    val data: SocialData,
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: String,
    val timestamp: String
)

data class SocialData(
    val address: String
)