package com.hotel.theconvo.data.repository

import com.hotel.theconvo.data.remote.ConvoApi
import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.response.*
import com.hotel.theconvo.domain.repository.ConvoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConvoRepositoryImpl @Inject constructor(
    private val api: ConvoApi
) :ConvoRepository {


    override suspend fun loginCall(loginReq: LoginReq): LoginResponse {

       // var loginReq = LoginReq("62e556598c9a47074325d98b4aa621eeaff30ef1d01300b5a06aa6eede1cfdfdaf636d4e657cdf62185b0df07d500b95670869ac096305d4126b99a275c9cee5","shourya.juden@fullangle.org")
        val response = api.getLoginResponse(loginReq)
        return  response




    }

    override suspend fun signupCall(signupReq: SignupReq): SignupResponse {





//        var signupReq = SignupReq("Dealer","Y","Y","xavien.carmello@fullangle.org","test","Y",false,"test","NEW","INDIVISUAL","U7JhFpqm+JIkdR0XzdTwvQ==","02/06/2022","PLATFORM","xavien.carmello@fullangle.org")


        val response = api.getSignupResponse(signupReq)
        return response


    }

    override suspend fun resetPassword(forgetReq: ForgetReq): ForgetResponse {
        val response = api.passwordReset(forgetReq)
        return response
    }

    override suspend fun socialLogin(socialReq: SocialReq): SocialResponse {

        val response = api.socialLogin(socialReq)
        return response

    }

    override suspend fun socialReLogin(socialReq: SocialReq): LoginResponse {

     val response = api.socialReLogin(socialReq)
        return response

    }

    override suspend fun getProperties(getPropertyReq: GetPropertyReq): GetPropertyResponse {

        val response = api.getProperties(getPropertyReq)
        return response

    }

    override suspend fun getLocations(getLocationReq: GetLocationReq): GetLocationResponse {

        val response = api.getLocations(getLocationReq)
        return response

    }

    override suspend fun getAutoCompleteLocations(getAutoCompleteReq: AutoCompleteReq): AutoCompleteResponse {
        val response = api.getAutoCompleteLocations(getAutoCompleteReq)
        return response

    }

    override suspend fun getPropertyDetails(propertyDetailsReq: PropertyDetailsReq): PropertyDetailsResponse {
        val response = api.getPropertyDetails(propertyDetailsReq)
        return response
    }

    override suspend fun bookingApiCall(bookingApiReq: BookingApiReq): BookingApiResponse {
        val response = api.bookingApi(bookingApiReq)
        return response
    }

    override suspend fun happeningNowApiCall(happeningNowReq: HappeningNowReq): HappeningNowResponse {
        val response = api.happeningNowApi(happeningNowReq)
        return response
    }


}




