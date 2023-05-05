package com.hotel.theconvo.domain

import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import javax.inject.Singleton


interface DialogCallback {

    fun showLoadingDialog()
    fun dismissLoadingDialog()


}