package com.hotel.theconvo.presentation.vm

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.domain.repository.ConvoRepository
import com.hotel.theconvo.usecase.LoginUseCase
import com.hotel.theconvo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//@HiltViewModel
class ConvoViewModel @Inject constructor( private val loginUseCase: LoginUseCase): ViewModel() {


   /** private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse: LiveData<LoginResponse> = _loginResponse


    fun fetchLoginResponse() {
        viewModelScope.launch {
            val result = loginUseCase()
            _loginResponse.value = result
        }
    }*/


}

