package com.hotel.theconvo.presentation.screens

import android.graphics.text.LineBreaker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun NavDrawerScreen(
    navigator: DestinationsNavigator?
) {


    Column(
        modifier = Modifier.fillMaxSize()
    ) {




           Row(
               horizontalArrangement = Arrangement.SpaceBetween,
               modifier = Modifier.fillMaxWidth()
           ) {




               Box(
                   modifier = Modifier.size(160.dp)
               ) {
                   Image(
                       painter = painterResource(R.drawable.ic_convo_logo),
                       contentDescription = "Your Image",
                       contentScale = ContentScale.Fit,
                       modifier = Modifier
                           .fillMaxSize()
                           .padding(
                               start =
                               10.dp
                           )
                   )
               }



               Text(
                   text = "X", fontSize = 20.sp,
                   modifier = Modifier
                       .padding(end = 15.dp, top = 40.dp)
                       .clickable {
                           navigator?.navigate(TabScreenDestination(true,false))
                       }

               )

           }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "Stay", modifier = Modifier.padding(start = 15.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Own",modifier = Modifier.padding(start = 15.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Convos",modifier = Modifier.padding(start = 15.dp))

        Spacer(modifier = Modifier.height(5.dp))
        Divider(modifier = Modifier.padding(start = 15.dp, end = 15.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Login",
            modifier = Modifier
                .padding(start = 15.dp)
                .clickable {
                  navigator?.navigate(LoginScreenDestination())
                }

        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Notifications",modifier = Modifier.padding(start = 15.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "History",modifier = Modifier.padding(start = 15.dp))












        //Divider(color = Color.Blue, thickness = 1.dp)

        //Spacer(modifier = Modifier.height(10.dp))





    }


}