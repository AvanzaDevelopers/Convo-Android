package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.ReservationScreenDestination
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
                    .height(220.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.ic_stays),
                contentDescription = "Stays Image"
            )

            
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "back arrow",
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
            )
            
            Text(

                text = "Reservation", color = Color(0XFFffffff),
                modifier = Modifier
                    .padding(top = 40.dp, start = 20.dp)
                    .fillMaxWidth(),
                fontSize = 25.sp
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


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(top = 20.dp, start = 20.dp)
                        ) {

                            Image(
                                modifier = Modifier.size(25.dp),
                                // .padding(top = 30.dp, start = 20.dp),
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "calendar icon"
                            )

                            Image(
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(35.dp)
                                    .padding(start = 12.dp, top = 2.dp, bottom = 2.dp),
                                painter = painterResource(id = R.drawable.ic_straight_line),
                                contentDescription = "straight line"
                            )

                            Image(
                                modifier = Modifier.size(25.dp)
                                // .padding(top = 30.dp, start = 20.dp)
                                ,
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "calendar icon"
                            )

                        }

                        Spacer(modifier = Modifier.width(20.dp))

                        Column(modifier = Modifier.padding(top = 20.dp)) {

                            Text(text = "From")

                            Text(text = "Tuesday 23, May 2023")

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(text = "Till")

                            Text(text = "Wednesday 24, May 2023")

                        }


                    }//Row ends here

                    Spacer(modifier = Modifier.height(40.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.ic_adults),
                            contentDescription = "Adults Image",
                            modifier = Modifier.size(25.dp)
                        )

                        Text(text = "2 Adults", modifier = Modifier.padding(start = 10.dp))

                        Spacer(modifier = Modifier.weight(1f))

                        Image(painter = painterResource(id = R.drawable.ic_kids),
                            contentDescription = "Kids Image",
                            modifier = Modifier.size(25.dp)
                        )
                        Text(text = "0 Kids", modifier = Modifier.padding(start = 10.dp, end = 60.dp))
                    }


                }



            }


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    //.height(200.dp)
                    .padding(top = 350.dp, start = 30.dp, end = 30.dp)
                    .shadow(elevation = 5.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp),
                    verticalAlignment = Alignment.CenterVertically,
                     horizontalArrangement = Arrangement.spacedBy(10.dp)

                ) {

                    Image(
                        modifier = Modifier.weight(3f) ,
                        contentScale = ContentScale.FillBounds ,
                        painter = painterResource(id = R.drawable.ic_stays)
                        , contentDescription = "Stays Image")

                    Text(text = "Executive Suite", modifier = Modifier.weight(5f))

                }


            }





        }

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = coupon.value,
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

        Spacer(
            modifier =  Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
        ) {

            Row(
                modifier = Modifier.weight(1f)

            ) {

                Text(text = "345", fontSize = 20.sp)
                Text(text = "USD", fontSize = 13.sp, modifier = Modifier.padding(start = 5.dp, top = 5.dp))

            }


            Button(
                modifier = Modifier
                    .background(MaterialTheme.colors.primary)
                    .weight(2f),
                onClick = {

                    navigator?.navigate(ReservationScreenDestination())
                }) {

                Text(text = "CHECKOUT")


            }

        }



    }
}