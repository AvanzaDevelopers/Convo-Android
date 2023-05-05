package com.hotel.theconvo.presentation.vm

import androidx.lifecycle.ViewModel
import com.hotel.theconvo.usecase.LoginUseCase
import javax.inject.Inject


//@HiltViewModel
class ConvoViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase


    ): ViewModel() {


   /** private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse


    fun fetchLoginResponse() {
        viewModelScope.launch {
            val result = loginUseCase()
            _loginResponse.value = result
        }
    }*/


}

