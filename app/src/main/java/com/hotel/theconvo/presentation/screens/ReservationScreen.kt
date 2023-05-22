package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.hotel.theconvo.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ReservationScreen(
    navigator: DestinationsNavigator?
) {

    val textFieldShape = RoundedCornerShape(8.dp)

    val coupon = remember { mutableStateOf(TextFieldValue()) }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()


        ) {






            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_stays),
                contentDescription = "Stays Image"
            )

            Text(

                text = "Reservation", color = Color(0XFFffffff),
                modifier = Modifier
                    .padding(top = 30.dp, start = 20.dp)
                    .fillMaxWidth()
            )



            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(200.dp)
                    .padding(top = 120.dp, start = 10.dp, end = 10.dp)
                    .shadow(elevation = 5.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)

                ) {

                }


            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(200.dp)
                    .padding(top = 300.dp, start = 30.dp, end = 30.dp)
                    .shadow(elevation = 5.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)

                ) {

                }


            }





        }

        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = coupon.value,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp)
                .shadow(elevation = 5.dp, shape = textFieldShape).clip(textFieldShape),
            shape = textFieldShape,
            onValueChange = {
                coupon.value = it
            },
            label = {
                Text(text = "Coupon Code")
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )




    }
}