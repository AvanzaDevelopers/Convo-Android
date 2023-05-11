package com.hotel.theconvo.presentation.screens

import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.SignupReq
import com.hotel.theconvo.data.remote.dto.response.LoginResponse
import com.hotel.theconvo.data.remote.dto.response.SignupResponse
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.util.AESUtils
import com.hotel.theconvo.util.LoadingDialog
import com.hotel.theconvo.util.UiState
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

    var fName = remember { mutableStateOf(TextFieldValue()) }
    var lName = remember { mutableStateOf(TextFieldValue()) }

    var passwordVisibility: Boolean by remember { mutableStateOf(false) }

    var showDialog = remember{ mutableStateOf(false) }

    var uiState by remember { mutableStateOf<UiState<SignupResponse>>(UiState.Loading) }



    Column(


        modifier = Modifier.fillMaxSize(),

        verticalArrangement = Arrangement.Center

    ) {


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

            Text(text = "Agree to all Terms & Conditions", modifier = Modifier.padding(top = 12.dp), fontSize = 15.sp)


        }

        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = {

                showDialog.value = true


                uiState = UiState.Loading

                val aesKey = password.value.text.encryptCBC()
                Log.i("AES Key is:",aesKey)

                GlobalScope.launch{



                  // var signupReq = SignupReq("Dealer","Y","Y","xavien.carmello@fullangle.org","test","Y",false,"test","NEW","INDIVISUAL","U7JhFpqm+JIkdR0XzdTwvQ==","02/06/2022","PLATFORM","xavien.carmello@fullangle.org")

                    var signupReq = SignupReq("Dealer","Y","Y",email.value.text,fName.value.text,"Y",false,lName.value.text,"NEW","INDIVIDUAL",password.value.text.encryptCBC(),"11/05/2023","PLATFORM",email.value.text)

                    Log.i("Email is:",email.value.text)
                    try {
                       Log.i("Data  is:", loginUseCase.signupInvoke(signupReq).toString())
                        uiState = UiState.Success(loginUseCase.signupInvoke(signupReq))
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