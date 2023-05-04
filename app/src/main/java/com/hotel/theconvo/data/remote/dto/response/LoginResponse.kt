package com.hotel.theconvo.data.remote.dto.response

data class LoginResponse(
    val loginResponse: LoginResponseX
)



data class LoginResponseX(
    val action: String,
    val data: Data
)

data class Data(
    val firstScreen: String,
    val message: Message,
    val success: Boolean,
    val token: String
)

data class Message(
    val displayToUser: Boolean,
    val responseCode: Int,
    val responseDescription: String,
    val routeTo: String,
    val status: String
)