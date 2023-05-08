package com.hotel.theconvo.data.remote.dto.response

data class ForgetResponse(
    val responseMessage: ResponseMessage
)

data class ResponseMessage(
    val action: String,
    val data: DataX
)

data class DataX(
    val message: ForgetMessage
)

data class ForgetMessage(
    val displayToUser: Boolean,
    val responseDescription: String,
    val status: String
)