package com.hotel.theconvo.data.remote.dto.req

data class SignupReq(
    val buisnessCategory: String,
    val custodianModel: String,
    val custodudianModel: String,
    val emailAddress: String,
    val firstname: String,
    val generateNewWallet: String,
    val isNewUser: Boolean,
    val lastname: String,
    val mode: String,
    val orgType: String,
    val passwordHash: String,
    val registrationDate: String,
    val registrationMechanism: String,
    val userId: String
)