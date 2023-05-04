package com.hotel.theconvo.domain.repository

import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.util.Resource
import kotlinx.coroutines.flow.Flow

interface ConvoRepository {

    suspend fun loginCall(loginReq: LoginReq): LoginResponse

    suspend fun signupCall(signupReq: SignupReq): SignupResponse

}




