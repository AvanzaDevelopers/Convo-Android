package com.hotel.theconvo.data.remote.dto.response

data class ConvoUserResponse(
    val messageStatus: String,
    val responseCode: Int,
    val responseDescription: String,
    val timestamp: String,
    val user: User
)

data class User(
    val action: String,
    val data: UserData
)

data class UserData(
    val searchResult: UserSearchResult
)

data class UserSearchResult(
    val authType: String,
    val countryOfResidence: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val mobileNumber: String
)