package com.hotel.theconvo.usecase

import com.hotel.theconvo.data.remote.dto.req.ForgetReq
import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.req.SocialReq
import com.hotel.theconvo.data.remote.dto.response.ForgetResponse
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.data.remote.dto.response.SocialResponse
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


}