package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun ManageAccountsTab(
    navigator: DestinationsNavigator?
) {

    val tabItems = listOf("Personal Info", "Change Password")
    var selectedTabIndex by remember { mutableStateOf(0) }



    Column {
        TabRow(
            modifier = Modifier.padding(start = 20.dp,end = 20.dp,top = 10.dp),
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.White,
            divider = {},
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier

                        .tabIndicatorOffset(it[selectedTabIndex]),
                    color = Color(0XFFfdad02),
                    //  height = TabRowDefaults.IndicatorHeight * 1.5F
                    height = 1.dp
                )
            }
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        } //Tab Row ends here



        // content for the currently selected tab
        when (selectedTabIndex) {
            0 -> {
                //Text("Content for Tab 1")
               PersonalInfoTab(navigator = navigator)
            }
            1 -> {
                //Text("Content for Tab 2")
                ChangePasswordTab(navigator = navigator)
            }

        }
    }// column ends here




}


@Composable
fun PersonalInfoTab(
    navigator: DestinationsNavigator?
) {
    //Text(text = "Personal Info Tab")

    val email = remember { mutableStateOf(TextFieldValue()) }
    val fullName = remember { mutableStateOf(TextFieldValue()) }
    val address = remember { mutableStateOf(TextFieldValue()) }
    val phoneNumber = remember { mutableStateOf(TextFieldValue()) }
    val description = remember { mutableStateOf(TextFieldValue()) }
    val fbProfileLink =  remember { mutableStateOf(TextFieldValue()) }
    val linkedInProfLink =  remember { mutableStateOf(TextFieldValue()) }
    val textFieldShape = RoundedCornerShape(8.dp)
    Column(
        modifier = Modifier.fillMaxSize()

    ) {

        Spacer(modifier = Modifier.height(30.dp))
        /**Text(
            text = "Personal Information",
            fontSize = 17.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
            )*/
        Image(
            painter = painterResource(id = R.drawable.ic_convo_logo),
            contentDescription = "Convo Logo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))

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

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = fullName.value,
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
                fullName.value = it
            },
            label = {
                Text(text = "Full Name")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = address.value,
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
                address.value = it
            },
            label = {
                Text(text = "Address")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = phoneNumber.value,
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
                phoneNumber.value = it
            },
            label = {
                Text(text = "Phone Number")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )


        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = description.value,
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
                description.value = it
            },
            label = {
                Text(text = "Description")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = fbProfileLink.value,
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
                fbProfileLink.value = it
            },
            label = {
                Text(text = "Facebook profile link")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(10.dp))


        TextField(
            value = linkedInProfLink.value,
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
                linkedInProfLink.value = it
            },
            label = {
                Text(text = "Linkedin profile link")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )


        Spacer(modifier = Modifier.weight(1f))


        Button(onClick = { }, modifier = Modifier
            .fillMaxWidth()

            .padding(
                start = 30.dp,
                end = 30.dp,
                bottom = 20.dp
            )
            .height(50.dp)
        ) {
            Text(text = "Save")
        }



    }






}

@Composable
fun ChangePasswordTab(
    navigator: DestinationsNavigator?
) {

    val email = remember { mutableStateOf(TextFieldValue()) }
    val password = remember { mutableStateOf(TextFieldValue()) }
    val confirmPassword = remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    var confirmPasswordVisibility: Boolean by remember { mutableStateOf(false) }

    val textFieldShape = RoundedCornerShape(8.dp)



    Column(modifier = Modifier.fillMaxWidth()) {

        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_convo_logo),
            contentDescription = "Convo Logo",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))

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

        Spacer(modifier = Modifier.height(20.dp))

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

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            shape = textFieldShape,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = confirmPassword.value,
            visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                    Icon(

                        painter = painterResource(if (confirmPasswordVisibility) R.drawable.ic_password_visibility else R.drawable.ic_password_visibility),
                        contentDescription = if (confirmPasswordVisibility) "Hide password" else "Show password",
                        tint = if (confirmPasswordVisibility) Color(0XFFfdad02) else Color.Gray
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
            onValueChange = {

                confirmPassword.value = it
            },

            label = {
                Text(text = "Confirm Password")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )


        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { }, modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 30.dp,
                end = 30.dp
            )
            .height(50.dp)
        ) {
            Text(text = "Save")
        }







    }
    
    
    
    
}