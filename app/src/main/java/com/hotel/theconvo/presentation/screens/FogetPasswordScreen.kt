package com.hotel.theconvo.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.ForgetReq
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.response.ForgetResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.util.AESUtils
import com.hotel.theconvo.util.LoadingDialog
import com.hotel.theconvo.util.UiState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Destination
@Composable
fun ForgetPasswordScreen(

    navigator: DestinationsNavigator?


) {


    val textFieldShape = RoundedCornerShape(8.dp)


    val email = remember { mutableStateOf(TextFieldValue()) }

    var showDialog = remember{ mutableStateOf(false) }

    /** Remember to change Signup Response to Forget Response */
    var uiState by remember { mutableStateOf<UiState<ForgetResponse>>(UiState.Loading) }


    Column(modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center) {


        Image(painter = painterResource(
            id = R.drawable.ic_convo_logo),
            contentDescription = "Convo Logo",
            modifier = Modifier
                .fillMaxWidth()
                .clickable { Log.i("On Click", "On Click") },
            alignment = Alignment.Center)


        Spacer(modifier = Modifier.height(40.dp))

        Text(text = "Reset Password",
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            modifier = Modifier.padding(start = 30.dp)
        )

        Spacer(modifier = Modifier.size(16.dp))

        TextField(
           shape = textFieldShape,
            value = email.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape).clip(textFieldShape),
            onValueChange = {
                email.value = it
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

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = {

                showDialog.value = true


                uiState = UiState.Loading

                val aesKey = AESUtils.generateAESKey("Test@123")

                Log.i("AES Key is:",aesKey)




                GlobalScope.launch{


                    uiState = UiState.Loading

                    var forgetReq = ForgetReq(email.value.text,email.value.text)
                    Log.i("Email is:",email.value.text)
                    try {
                        Log.i("Data  is:", MainActivity.loginUseCase.resetInvoke(forgetReq).toString())
                        uiState = UiState.Success(MainActivity.loginUseCase.resetInvoke(forgetReq))
                    }
                    catch (ex: Exception) {

                        uiState = UiState.Error(ex.message.toString())
                        Log.i("Exception:","Exception Occured!")

                    }


                }


            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 30.dp,
                    end = 30.dp
                )
                .height(50.dp)
        ) {
            Text(text = "Reset", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Back to Login Screen",
            textDecoration = TextDecoration.Underline,
            textAlign = TextAlign.Center,
            color = Color(0XFFfdad02),
            fontSize = 15.sp,

            modifier = Modifier
                .clickable {
                    navigator?.navigate(LoginScreenDestination())
                }
                .fillMaxWidth()
        )



        when (uiState) {



            is UiState.Loading -> {

                Log.i("In Loading State","In Loading State")
                // Display a loading dialog
                //CircularProgressIndicator()
                if (showDialog.value == true) {
                    showDialog.value = true
                }
                else {
                    showDialog.value = false
                }
            }
            is UiState.Success -> {
                // Display the data
                val data = (uiState as UiState.Success<ForgetResponse>).data
                // ...
                showDialog.value = false

                Toast.makeText(LocalContext.current,data.responseMessage.data.message.responseDescription,Toast.LENGTH_LONG).show()

            }
            is UiState.Error -> {
                // Display an error message
                val message = (uiState as UiState.Error).message

                Toast.makeText(LocalContext.current,message, Toast.LENGTH_LONG).show()
                showDialog.value = false

                uiState = UiState.Loading
                // ...
            }

        }


        if (showDialog.value) {
            LoadingDialog(isShowingDialog = showDialog.value)

            uiState = UiState.Loading








        }




    }

}