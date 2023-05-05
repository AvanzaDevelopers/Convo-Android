package com.hotel.theconvo.usecase

import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
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
}