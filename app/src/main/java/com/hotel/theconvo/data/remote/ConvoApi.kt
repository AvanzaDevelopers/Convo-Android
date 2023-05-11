package com.hotel.theconvo.data.remote

import com.hotel.theconvo.data.remote.dto.req.ForgetReq
import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.req.SocialReq
import com.hotel.theconvo.data.remote.dto.response.ForgetResponse
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.data.remote.dto.response.SocialResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ConvoApi {


    @POST("api/Login")
    suspend fun getLoginResponse(@Body loginReq: LoginReq) : LoginResponse

    @POST("api/PUBLIC/core/registerUser")
    suspend fun getSignupResponse(@Body signupReq: SignupReq) : SignupResponse

    @POST("api/API/core/registerUser")
    suspend fun socialLogin(@Body socialReq: SocialReq): SocialResponse

    @POST("api/Login")
    suspend fun socialReLogin(@Body socialReq: SocialReq) : LoginResponse


    @POST("api/API/UI/passwordReset")
    suspend fun passwordReset(@Body forgetReq: ForgetReq): ForgetResponse

    companion object{
        const val BASE_URL = "http://23.97.138.116:8004/"
    }


}