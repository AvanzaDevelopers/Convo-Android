package com.hotel.theconvo.usecase

import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.response.*
import com.hotel.theconvo.domain.repository.ConvoRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(

    private val convoRepository: ConvoRepository
     //val dialogCallback: DialogCallback

    ) {

    suspend operator fun invoke(loginReq: LoginReq): LoginResponse {


      //  return  convoRepository.loginCall(loginReq,dialogCallback)

        return  convoRepository.loginCall(loginReq)
    }

    suspend fun signupInvoke(signupReq: SignupReq) : SignupResponse {
        return convoRepository.signupCall(signupReq)
    }

    suspend fun resetInvoke(forgetReq: ForgetReq): ForgetResponse {
        return convoRepository.resetPassword(forgetReq)
    }

    suspend fun socialInvoke(socialReq: SocialReq): SocialResponse {
        return convoRepository.socialLogin(socialReq)
    }

    suspend fun socialReLoginInvoke(socialReq: SocialReq): LoginResponse {
        return convoRepository.socialReLogin(socialReq)
    }

    suspend fun getProperties(getPropertReq: GetPropertyReq): GetPropertyResponse {
        return convoRepository.getProperties(getPropertReq)
    }

    suspend fun getLocations(getLocationReq: GetLocationReq): GetLocationResponse {

        return convoRepository.getLocations(getLocationReq)

    }
    suspend fun getAutoCompleteLocations(autoCompleteReq: AutoCompleteReq): AutoCompleteResponse {
        return convoRepository.getAutoCompleteLocations(autoCompleteReq)
    }

    suspend fun getPropertyDetails(propertyDetailsReq: PropertyDetailsReq) : PropertyDetailsResponse {
        return convoRepository.getPropertyDetails(propertyDetailsReq)
    }

    suspend fun bookingApiCall(bookingApiReq: BookingApiReq): BookingApiResponse {
        return convoRepository.bookingApiCall(bookingApiReq)
    }

}