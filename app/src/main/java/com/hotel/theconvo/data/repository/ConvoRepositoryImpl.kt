package com.hotel.theconvo.data.repository

import com.hotel.theconvo.data.remote.ConvoApi
import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.domain.repository.ConvoRepository
import com.hotel.theconvo.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
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





        var signupReq = SignupReq("Dealer","Y","Y","xavien.carmello@fullangle.org","test","Y",false,"test","NEW","INDIVISUAL","U7JhFpqm+JIkdR0XzdTwvQ==","02/06/2022","PLATFORM","xavien.carmello@fullangle.org")
        val response = api.getSignupResponse(signupReq)
        return response


    }


}




