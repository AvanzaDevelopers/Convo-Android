package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun RegistrationScreen(

    navigator: DestinationsNavigator?

) {

    Column(


        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center

    ) {

        Image(painter = painterResource(
            id = R.drawable.ic_convo_logo),
            contentDescription = "Convo Logo",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { Log.i("On Click", "On Click") },
            alignment = Alignment.Center)


        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Registration",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(start = 30.dp)
            )

        Spacer(modifier = Modifier.size(16.dp))

        TextField(
            value = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = RectangleShape),
            onValueChange = {

            },
            label = {
                Text(text = "First Name")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent


            )
        )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(value = "",modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .shadow(elevation = 5.dp, shape = RectangleShape),
            onValueChange = {

            },

            label = {
                Text(text = "Last Name")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )


        )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(value = "",modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .shadow(elevation = 5.dp, shape = RectangleShape),
            onValueChange = {

            },

            label = {
                Text(text = "Email")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )


        )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(value = "",modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .shadow(elevation = 5.dp, shape = RectangleShape),
            onValueChange = {

            },

            label = {
                Text(text = "Password")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )


        )

        Spacer(modifier = Modifier.size(20.dp))

        TextField(value = "",modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .shadow(elevation = 5.dp, shape = RectangleShape),
            onValueChange = {

            },

            label = {
                Text(text = "Re-Password")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )


        )

        Spacer(modifier = Modifier.size(10.dp))

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        ) {

            Checkbox(checked = true, onCheckedChange = {

            })

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = "Agree to all Terms & Conditions", modifier = Modifier.padding(top = 12.dp), fontSize = 15.sp)


        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 30.dp,
                    end = 30.dp
                )
                .height(50.dp)
        ) {
            Text(text = "CREATE ACCOUNT", color = Color.White)
        }
        
        Spacer(modifier = Modifier.size(20.dp))
        
        Text(
            text = "Already have an account? Signup Now ",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigator?.navigate(LoginScreenDestination())
                },
            textAlign = TextAlign.Center
            )
        







    }

}