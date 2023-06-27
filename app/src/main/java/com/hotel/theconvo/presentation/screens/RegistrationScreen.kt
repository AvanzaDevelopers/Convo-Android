package com.hotel.theconvo.presentation.screens

import android.util.Base64
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.edit
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.destinations.TermsConditionDestination
import com.hotel.theconvo.util.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec


@Destination
@Composable
fun RegistrationScreen(

    navigator: DestinationsNavigator?

) {
    val SECRET_KEY = "b75524255a7f54d2726a951bb39204df"
    val SECRET_IV = "1583288699248111"

    var showEmailDialog by remember { mutableStateOf(false) }
    val image: Painter = painterResource(id = R.drawable.ic_convo_logo)


    val textFieldShape = RoundedCornerShape(8.dp)

    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }

    val rePassword = remember { mutableStateOf(TextFieldValue()) }

    var fName = remember { mutableStateOf(TextFieldValue()) }
    var lName = remember { mutableStateOf(TextFieldValue()) }

    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    var rePasswordVisibility: Boolean by remember {mutableStateOf(false)}

    var showDialog = remember{ mutableStateOf(false) }

    var uiState by remember { mutableStateOf<UiState<SignupResponse>>(UiState.Loading) }

    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)

    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }


    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    var showEmailError by remember { mutableStateOf(false) }


    var showFirstNameError by remember { mutableStateOf(false) }

    var showLastNameError by remember {mutableStateOf(false)}

    var isEmailValid by remember { mutableStateOf(false) }

    var isPasswordValid by remember { mutableStateOf(false) }
    var showPasswordError by remember { mutableStateOf(false) }

    var showRePasswordError by remember { mutableStateOf(false) }

    val showTermsCondition = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()



    Column(


        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center

    ) {

        if (showTermsCondition.value) {
            TermsAndConditionsDialog(openDialog = showTermsCondition.value, onCloseDialog = { showTermsCondition.value = false })

        }


        val emailKey = "furqanrathor182@gmail.com".encryptCBC()

         Log.i("Email Key:",emailKey)
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = textFieldShape,
            value = fName.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
            onValueChange = {
               fName.value = it
                showFirstNameError = false
            },
            label = {
                Text(text = "First Name")
            },
            isError = fName.value.text.length<3 && showFirstNameError  ,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent


            )
        )


        if (showFirstNameError && fName.value.text.length<3) {
            Text(
                text = "First Name must be 3 characters long",
                color = Color.Red,
                modifier = Modifier
                    // .align(Alignment.BottomStart)
                    .padding(start = 30.dp, top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = textFieldShape,
            value = lName.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
            onValueChange = {

                            lName.value = it
                         showLastNameError = false
            },

            label = {
                Text(text = "Last Name")
            },
            isError = lName.value.text.length<3 && showLastNameError  ,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )


        )

        if (showLastNameError && lName.value.text.length<3) {
            Text(
                text = "Last Name must be 3 characters long",
                color = Color.Red,
                modifier = Modifier
                    // .align(Alignment.BottomStart)
                    .padding(start = 30.dp, top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = textFieldShape,
            value = email.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
            onValueChange = {

                            email.value = it
                showEmailError = false
                isEmailValid = emailRegex.matches(email.value.text)

            },

            isError = showEmailError && !isEmailValid,

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

        if (showEmailError && !emailRegex.matches(email.value.text)) {
            Text(
                text = "Invaid Email",
                color = Color.Red,
                modifier = Modifier
                    // .align(Alignment.BottomStart)
                    .padding(start = 30.dp, top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            shape = textFieldShape,
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

                           showPasswordError = false
                            password.value = it
            },

            isError = password.value.text.length<8 && showPasswordError  ,
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


        if (showPasswordError && password.value.text.length<8) {
            Text(
                text = "Password Name must be 8 characters long",
                color = Color.Red,
                modifier = Modifier
                    // .align(Alignment.BottomStart)
                    .padding(start = 30.dp, top = 5.dp)
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        TextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            isError = rePassword.value.text.length<8 && showRePasswordError,
            shape = textFieldShape,
            value = rePassword.value,
            visualTransformation = if (rePasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { rePasswordVisibility = !rePasswordVisibility }) {
                    Icon(

                        painter = painterResource(if (rePasswordVisibility) R.drawable.ic_password_visibility else R.drawable.ic_password_visibility),
                        contentDescription = if (rePasswordVisibility) "Hide password" else "Show password",
                        tint = if (rePasswordVisibility) Color(0XFFfdad02) else Color.Gray
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
            onValueChange = {

                showRePasswordError = false
                rePassword.value = it
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

        if (showRePasswordError && rePassword.value.text.length<8) {
            Text(
                text = "Re-Password must be 8 characters long",
                color = Color.Red,
                modifier = Modifier
                    // .align(Alignment.BottomStart)
                    .padding(start = 30.dp, top = 5.dp)
            )
        }




       /** Spacer(modifier = Modifier.size(20.dp))

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


        )*/

        Spacer(modifier = Modifier.size(10.dp))

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp)
        ) {

            Checkbox(checked = true, onCheckedChange = {

            })

            Spacer(modifier = Modifier.width(10.dp))

            Text(text = "Agree to all ", modifier = Modifier.padding(top = 12.dp), fontSize = 15.sp)

            Text(
                text = " Terms & Conditions ",
                textDecoration = TextDecoration.Underline,
                color= Color(0XFFfdad02),

                modifier = Modifier

                    .padding(top = 12.dp)
                    .clickable {
                        //navigator?.navigate(LoginScreenDestination())

                        //  showTermsCondition.value = true
                        //  navigator?.navigate(TermsConditionDe)
                        navigator?.navigate(TermsConditionDestination())

                    },
                fontSize = 15.sp

            )

        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(

            onClick = {


                if(!password.value.text.equals(rePassword.value.text)){
                    Toast.makeText(context,"Password & Re-Password must be same",Toast.LENGTH_LONG).show()
                    return@Button
                }

                showEmailError = true
                showPasswordError = true
                showFirstNameError = true
                showLastNameError = true
                showPasswordError = true
                showRePasswordError = true




                if(isEmailValid && fName.value.text.length>=3 && lName.value.text.length >=3 && password.value.text.length>=8 && rePassword.value.text.length>=8) {
                    showDialog.value = true


                    uiState = UiState.Loading

                    val aesKey = password.value.text.encryptCBC()
                    Log.i("AES Key is:", aesKey)

                    scope.launch {


                        // var signupReq = SignupReq("Dealer","Y","Y","xavien.carmello@fullangle.org","test","Y",false,"test","NEW","INDIVISUAL","U7JhFpqm+JIkdR0XzdTwvQ==","02/06/2022","PLATFORM","xavien.carmello@fullangle.org")

                        var signupReq = SignupReq(
                            "Dealer",
                            "Y",
                            "Y",
                            email.value.text,
                            fName.value.text,
                            "Y",
                            false,
                            lName.value.text,
                            "NEW",
                            "INDIVIDUAL",
                            password.value.text.encryptCBC(),
                            "11/05/2023",
                            "PLATFORM",
                            email.value.text
                        )

                        Log.i("Email is:", email.value.text)
                        try {
                            Log.i("Data  is:", loginUseCase.signupInvoke(signupReq).toString())
                            uiState = UiState.Success(loginUseCase.signupInvoke(signupReq))
                        } catch (ex: Exception) {

                            uiState = UiState.Error(ex.message.toString())
                            Log.i("Exception:", "Exception Occured!")

                        }


                    } // Global Scope ends here.
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
            Text(text = "CREATE ACCOUNT", color = Color.White)
        }
        
        Spacer(modifier = Modifier.size(20.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)

        ) {

            Text(
                text = "Already have an account "
            )

            Text(
                text = "Sign in Now ",
                textDecoration = TextDecoration.Underline,
                color= Color(0XFFfdad02),
                modifier = Modifier
                    .clickable {
                        navigator?.navigate(LoginScreenDestination())
                    }

            )

        }





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
                val data = (uiState as UiState.Success<SignupResponse>).data
                // ...
                showDialog.value = false

                showEmailDialog = true



                sharedPreferences.edit {
                    putString(AllKeys.email,email.value.text)
                    putString(AllKeys.firstName,fName.value.text)
                    putString(AllKeys.lastName,lName.value.text)
                    putString(AllKeys.token,data.data.JWTToken)
                }


                if (showEmailDialog) {
                    AlertDialog(
                        onDismissRequest = { showEmailDialog = false },
                        title = { Text(data.messageStatus) },

                        text = {
                            if (data.data.responseMessage == null)
                            Text(text = data.responseDescription)

                            else
                                Text(text = data.data.responseMessage)

                            /**   Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Spacer(modifier = Modifier.height(16.dp))

                                Image(
                                    painter = image,
                                    contentDescription = "Image",
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                Text(data.responseDescription)


                            }*/
                        },
                        confirmButton = {
                            Button(onClick = {
                                showEmailDialog = false
                                navigator?.navigate(LoginScreenDestination())
                            },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp)

                                ) {
                                Text("Continue")
                            }
                        },
                        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                    )
                }



            } // Success block ends here
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

private fun String.encryptCBC(): String {
    val iv = IvParameterSpec(AESUtils.SECRET_IV.toByteArray())
    val keySpec = SecretKeySpec(AESUtils.SECRET_KEY.toByteArray(), "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
    val crypted = cipher.doFinal(this.toByteArray())
    val encodedByte = Base64.encode(crypted, Base64.DEFAULT)
    return String(encodedByte)
}



@Composable
fun TermsAndConditionsDialog(
    openDialog: Boolean,
    onCloseDialog: () -> Unit
) {
    if (openDialog) {
        Dialog(onDismissRequest = { onCloseDialog() }) {
            AlertDialog(
                onDismissRequest = { onCloseDialog() },
                title = { Text(text = "Terms and Conditions") },
              /**  text = {
                    val resourceName = stringResource(R.string.terms_condition)

                    Text(text = resourceName)
                    // Add your terms and conditions content here
                },*/

                text = {
                    LazyColumn(


                            //.weight(weight =1f, fill = false)

                    ) {
                        items(1) {
                            Text(
                                text = stringResource(R.string.terms_condition),
                                modifier = Modifier.padding(bottom = 16.dp).fillMaxHeight()
                            )
                        }


                    }
                },
                confirmButton = {
                    Button(
                        onClick = { onCloseDialog() },
                       // modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "OK")
                    }
                },
              /**  dismissButton = {
                    Button(
                        onClick = { onCloseDialog() },
                        //modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Decline")
                    }
                }*/
            )
        }
    }
}
