package com.hotel.theconvo.presentation.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.MainActivity.Companion.mGoogleSignInClient
import com.hotel.theconvo.MainActivity.Companion.signInLauncher
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.LoginReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.destinations.ForgetPasswordScreenDestination
import com.hotel.theconvo.destinations.RegistrationScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.hotel.theconvo.presentation.vm.ConvoViewModel
import com.hotel.theconvo.ui.theme.Shapes
import com.hotel.theconvo.ui.theme.yellowColor
import com.hotel.theconvo.usecase.LoginUseCase
import com.hotel.theconvo.util.AllKeys
import com.hotel.theconvo.util.LoadingDialog
import com.hotel.theconvo.util.SharedPrefsHelper
import com.hotel.theconvo.util.UiState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.*
import java.security.MessageDigest
import javax.inject.Inject
import kotlin.math.round


@SuppressLint("UnrememberedMutableState")
@Destination
@Composable
fun LoginScreen(

    navigator: DestinationsNavigator?
    ) {
    //Text(text = "Login Screen")

    //val viewModel: ConvoViewModel = hiltViewModel()
    //val post by viewModel.loginResponse.observeAsState()


    val textFieldShape = RoundedCornerShape(8.dp)
    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

   var showDialog = remember{ mutableStateOf(false) }

    var uiState by remember { mutableStateOf<UiState<LoginResponse>>(UiState.Loading) }


        var googleState by remember { mutableStateOf<UiState<GoogleSignInAccount>>(UiState.Loading) }



    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)

    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }

    var token by rememberSaveable { mutableStateOf(sharedPreferences.getString("token", "") ?: "") }




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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
            shape = textFieldShape,
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
            shape = textFieldShape,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = password.value,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        
                        painter = painterResource(if (passwordVisibility) R.drawable.ic_password_visibility else R.drawable.ic_password_visibility),
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                        tint = if (passwordVisibility) Color(0XFFfdad02) else Color.Gray
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 30.dp)
                .height(20.dp)

        ) {

            Text(

                text = "Forgot your password? ",
                fontSize = 13.sp,
                color = Color.Black
            )

            Text(

                text = "Reset Now",
                fontSize = 13.sp,
                textDecoration = TextDecoration.Underline,
                color = Color(0XFFfdad02),
                modifier = Modifier.clickable {


                    navigator?.navigate(ForgetPasswordScreenDestination())
                }

                )

        }




        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {



                Log.i("Login Clicked:","Login Clicked")

                showDialog.value = true

                //loginUseCase.dialogCallback.showLoadingDialog()

              //  LaunchedEffect(true) {
                    // viewModel.fetchLoginResponse()


                GlobalScope.launch {




                  uiState = UiState.Loading

                    Log.i("Sha512:", sha512(password.value.text.toString()))

                   // var loginReq = LoginReq("62e556598c9a47074325d98b4aa621eeaff30ef1d01300b5a06aa6eede1cfdfdaf636d4e657cdf62185b0df07d500b95670869ac096305d4126b99a275c9cee5","shourya.juden@fullangle.org")


                    var loginReq = LoginReq(sha512(password.value.text.toString()),email.value.text.toString())


                    try {
                       // Log.i("Data  is:", loginUseCase.invoke(loginReq).toString())

                        var loginResponse = loginUseCase.invoke(loginReq)

                       // uiState = UiState.Success(loginUseCase.invoke(loginReq))

                        uiState = UiState.Success(loginResponse)

                        var userResponse = loginUseCase.getConvoUser(loginResponse.loginResponse.data.token)

                        Log.i("User Data is:", userResponse.toString())

                        //Log.i("Token is:",loginResponse.loginResponse.data.token)
                        sharedPreferences.edit {
                            //putString()
                            putString(AllKeys.firstName,userResponse.user.data.searchResult.firstName)
                            putString(AllKeys.lastName,userResponse.user.data.searchResult.lastName)
                            putString(AllKeys.mobileNumber,userResponse.user.data.searchResult.mobileNumber)
                            putString(AllKeys.countryResidence,userResponse.user.data.searchResult.countryOfResidence)

                        }

                    }
                    catch (e: Exception) {
                        uiState = UiState.Error(e.message.toString())
                       // uiState = UiState.Error("logged in successfully !!!")

                    }



                }

                //}


            },
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


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(40.dp)

        ) {

            Text(
                text = "Don't have an account? ",


                fontSize = 15.sp
            )

            Text(text = "SignUp",
                color = Color(0XFFfdad02),
                textDecoration = TextDecoration.Underline,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier
                        .clickable {
                    Log.i("Account Clicked", "Account Text Clicked")
                    navigator?.navigate(RegistrationScreenDestination())
                })


        }



        Spacer(modifier = Modifier.height(50.dp))



        /**OutlinedButton(
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

            Icon(painter = painterResource(id = R.drawable.ic_facebook), contentDescription = "Fb Icon", modifier = Modifier.padding(start = 5.dp))
            Text(text = "Log in with Facebook", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)


        }*/


        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {


                      signIn(mGoogleSignInClient, signInLauncher)



            },

            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colors.primary
            ),

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .height(50.dp)



            ) {

            Icon(painter = painterResource(id = R.drawable.ic_google), contentDescription = "Google Icon", modifier = Modifier.padding(start = 5.dp))
            Text(text = "Log in with Google", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)

        }







    }


    /**For google states */
    when(googleState) {
        is UiState.Error -> {
            Log.i("In Error State","In Error State")
        }
        UiState.Loading -> {
            Log.i("In Loading State","In Loading State")
        }
        is UiState.Success -> {
            navigator?.navigate(TabScreenDestination(true,false))
        }
    }

    //val openDialog by viewModel.open.observeAsState(false)


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
            val data = (uiState as UiState.Success<LoginResponse>).data

            sharedPreferences.edit {
                putString(AllKeys.token,data.loginResponse.data.token)

                putString(AllKeys.email,email.value.text.toString())




            }

            // ...
            showDialog.value = false

            navigator?.navigate(TabScreenDestination(true,false))
        }
        is UiState.Error -> {
            // Display an error message
            val message = (uiState as UiState.Error).message

            Toast.makeText(LocalContext.current,message,Toast.LENGTH_LONG).show()
            showDialog.value = false

            uiState = UiState.Loading

            /**remove below line after fixation */
            navigator?.navigate(TabScreenDestination(true,false))
            // ...
        }

    }


    if (showDialog.value) {
        LoadingDialog(isShowingDialog = showDialog.value)

        uiState = UiState.Loading








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

fun signIn(mGoogleSignInClient: GoogleSignInClient, signInLauncher: ActivityResultLauncher<Intent>) {
    println("void sign in")
    val signInIntent = mGoogleSignInClient.signInIntent
    signInLauncher.launch(signInIntent)


    println("got sign in intent")



}




@Preview(showBackground = true)
@Composable

  fun loginPreview() {
    LoginScreen(null)
}