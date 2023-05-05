package com.hotel.theconvo.di

import com.hotel.theconvo.data.remote.ConvoApi
import com.hotel.theconvo.data.repository.ConvoRepositoryImpl
import com.hotel.theconvo.domain.repository.ConvoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideConvoApi():ConvoApi{
        return Retrofit.Builder().baseUrl(ConvoApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ConvoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideConvoRepository(api:ConvoApi) :ConvoRepository{
        return ConvoRepositoryImpl(api = api)
    }

   /** @Provides
    @Singleton
    fun provideDialog(): DialogCallback {
        return DialogCallImpl()
    }*/





}