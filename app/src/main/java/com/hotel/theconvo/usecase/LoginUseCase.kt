package com.hotel.theconvo.usecase

import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.domain.DialogCallback
import com.hotel.theconvo.domain.repository.ConvoRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(

    private val convoRepository: ConvoRepository,
     val dialogCallback: DialogCallback

    ) {

    suspend operator fun invoke(loginReq: LoginReq): LoginResponse {

      //  var loginReq = LoginReq("62e556598c9a47074325d98b4aa621eeaff30ef1d01300b5a06aa6eede1cfdfdaf636d4e657cdf62185b0df07d500b95670869ac096305d4126b99a275c9cee5","shourya.juden@fullangle.org")

        return  convoRepository.loginCall(loginReq,dialogCallback)
    }
}