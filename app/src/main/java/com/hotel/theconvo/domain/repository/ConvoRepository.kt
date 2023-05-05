package com.hotel.theconvo.domain.repository

import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse

interface ConvoRepository {

    //suspend fun loginCall(loginReq: LoginReq, dialogCallBack: DialogCallback): LoginResponse

    suspend fun loginCall(loginReq: LoginReq): LoginResponse


    suspend fun signupCall(signupReq: SignupReq): SignupResponse

}




