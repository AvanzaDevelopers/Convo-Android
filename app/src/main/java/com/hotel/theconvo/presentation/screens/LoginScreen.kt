package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.destinations.RegistrationScreenDestination
import com.hotel.theconvo.presentation.vm.ConvoViewModel
import com.hotel.theconvo.ui.theme.Shapes
import com.hotel.theconvo.usecase.LoginUseCase
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.math.round


@Destination
@Composable
fun LoginScreen(

    navigator: DestinationsNavigator?
    ) {
    //Text(text = "Login Screen")

    //val viewModel: ConvoViewModel = hiltViewModel()
    //val post by viewModel.loginResponse.observeAsState()


    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }


    LaunchedEffect(true) {
       // viewModel.fetchLoginResponse()

        Log.i("Sha512:", sha512("Test@123"))

        var loginReq = LoginReq("62e556598c9a47074325d98b4aa621eeaff30ef1d01300b5a06aa6eede1cfdfdaf636d4e657cdf62185b0df07d500b95670869ac096305d4126b99a275c9cee5","shourya.juden@fullangle.org")

        Log.i("Data  is:", loginUseCase.invoke(loginReq).toString())
    }




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

       /** Text(

            text = "Login",
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                fontSize = 25.sp,
            color = Color.Black
        )*/

        Image(

            painter = painterResource(id = R.drawable.login_heading_icon),
            contentDescription = "Login Screen Heading",
            modifier = Modifier.padding(30.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))




        TextField(
            value = email.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = RectangleShape),
            onValueChange = {
             email.value = it
        },
            label = {
            Text(text = "Email Address")
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

        TextField(
            value = password.value,
            modifier = Modifier
            .fillMaxWidth()
            .padding(start = 30.dp, end = 30.dp)
            .shadow(elevation = 5.dp, shape = RectangleShape),
            onValueChange = {

                 password.value = it
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

        Text(

            text = "Forgot your password? Reset Now",
            fontSize = 10.sp,
            color = Color.Black,
            textAlign = TextAlign.Left,
            modifier = Modifier.padding(start = 50.dp, top = 10.dp)
            )

        Spacer(modifier = Modifier.height(40.dp))

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
            Text(text = "LOGIN", color = Color.White)
        }

        Text(
            text = "Don't have an account? Signup",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .clickable {
                    Log.i("Account Clicked", "Account Text Clicked")
                    navigator?.navigate(RegistrationScreenDestination())
                }
            ,
            textAlign = TextAlign.Center,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(50.dp))



        OutlinedButton(
            onClick = {  },
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            ),
           modifier = Modifier
               .fillMaxWidth()
               .padding(start = 20.dp, end = 20.dp)
               .height(50.dp)

        ) {

            Text(text = "Log in with Facebook")


        }


        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {  },

            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            ),

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .height(50.dp)



            ) {

            Text(text = "Log in with Google")


        }







    }


}

fun sha512(input: String): String {
    return MessageDigest.getInstance("SHA-512")
        .digest(input.toByteArray())
        .joinToString(separator = "") {
            ((it.toInt() and 0xff) + 0x100)
                .toString(16)
                .substring(1)
        }
}

@Preview(showBackground = true)
@Composable

  fun loginPreview() {
    LoginScreen(null)
}