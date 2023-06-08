package com.hotel.theconvo.domain.repository

import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.response.*

interface ConvoRepository {

    //suspend fun loginCall(loginReq: LoginReq, dialogCallBack: DialogCallback): LoginResponse

    suspend fun loginCall(loginReq: LoginReq): LoginResponse


    suspend fun signupCall(signupReq: SignupReq): SignupResponse

    suspend fun resetPassword(forgetReq: ForgetReq): ForgetResponse

    suspend fun socialLogin(socialReq: SocialReq): SocialResponse

    suspend fun socialReLogin(socialReq: SocialReq): LoginResponse

    suspend fun getProperties(getPropertyReq: GetPropertyReq): GetPropertyResponse

    suspend fun getLocations(getLocationReq: GetLocationReq) : GetLocationResponse

    suspend fun getAutoCompleteLocations(getAutoCompleteReq: AutoCompleteReq): AutoCompleteResponse

   suspend fun getPropertyDetails(propertyDetailsReq: PropertyDetailsReq): PropertyDetailsResponse

   suspend fun bookingApiCall(bookingApiReq: BookingApiReq): BookingApiResponse

   suspend fun happeningNowApiCall(happeningNowReq: HappeningNowReq) : HappeningNowResponse

   suspend fun bookingListApiCall(token: String, bookingApiReq: BookingListReq): BookingListResponse

   suspend fun convoUserApiCall(token: String): ConvoUserResponse



}




