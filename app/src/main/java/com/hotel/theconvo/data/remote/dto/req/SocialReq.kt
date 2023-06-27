package com.hotel.theconvo.data.remote.dto.req

data class SocialReq(
    val _token: Token,
    val buisnessCategory: String,
    val custodianModel: String,
    val custodudianModel: String,
    val emailAddress: String?,
    val firstname: String?,
    val generateNewWallet: String,
    val lastname: String?,
    val mode: String,
    val orgType: String,
    val passwordHash: String,
    val registrationDate: String,
    val registrationMechanism: String,
    val userId: String?,
    val isMobile: Boolean?
)

data class Token(
    val idToken: String
)