package com.hotel.theconvo.domain.repository

import com.hotel.theconvo.data.remote.dto.req.ForgetReq
import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.req.SocialReq
import com.hotel.theconvo.data.remote.dto.response.ForgetResponse
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.data.remote.dto.response.SocialResponse

interface ConvoRepository {

    //suspend fun loginCall(loginReq: LoginReq, dialogCallBack: DialogCallback): LoginResponse

    suspend fun loginCall(loginReq: LoginReq): LoginResponse


    suspend fun signupCall(signupReq: SignupReq): SignupResponse

    suspend fun resetPassword(forgetReq: ForgetReq): ForgetResponse

    suspend fun socialLogin(socialReq: SocialReq): SocialResponse

    suspend fun socialReLogin(socialReq: SocialReq): LoginResponse

}




