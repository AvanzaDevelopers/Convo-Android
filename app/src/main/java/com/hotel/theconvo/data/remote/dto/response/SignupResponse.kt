package com.hotel.theconvo.data.remote.dto.response

data class SignupResponse(
    val data: SignupData,
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: String,
    val timestamp: String
)

data class SignupData(
    val JWTToken: String,
    val responseMessage: String? = "Something went wrong"
)