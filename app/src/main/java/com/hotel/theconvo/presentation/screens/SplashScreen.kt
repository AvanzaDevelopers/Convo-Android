package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule


@Destination
@Composable
 fun SplashScreen(
    navigator: DestinationsNavigator?
) {


    LaunchedEffect(key1 = Unit){
        delay(3000)
        //navigator?.navigate(LoginScreenDestination())
        navigator?.navigate(TabScreenDestination())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.primary)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

        Image(

            painter = painterResource(id = R.drawable.ic_convo_logo_white),
            contentDescription = "Convo Logo",

            )



    }






}