package com.hotel.theconvo.data.remote

import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.response.*
import retrofit2.http.Body
import retrofit2.http.POST

interface ConvoApi {

    /**Login Api */
    @POST("api/Login")
    suspend fun getLoginResponse(@Body loginReq: LoginReq) : LoginResponse

    /**Registration Api */
    @POST("api/PUBLIC/core/registerUser")
    suspend fun getSignupResponse(@Body signupReq: SignupReq) : SignupResponse

    /**Social Login APi */
    @POST("api/API/core/registerUser")
    suspend fun socialLogin(@Body socialReq: SocialReq): SocialResponse

    /**Login Api */
    @POST("api/Login")
    suspend fun socialReLogin(@Body socialReq: SocialReq) : LoginResponse

    /**Password Reset Api */
    // Password Reset Api
    @POST("api/API/UI/passwordReset")
    suspend fun passwordReset(@Body forgetReq: ForgetReq): ForgetResponse

    //Get Properties Api
    @POST("api/PUBLIC/CONVO_PROJECT/getProperties")
    suspend fun getProperties(@Body getPropertyReq: GetPropertyReq): GetPropertyResponse

    companion object{
        const val BASE_URL = "http://23.97.138.116:8004/"
    }


}