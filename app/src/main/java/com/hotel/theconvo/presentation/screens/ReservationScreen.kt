package com.hotel.theconvo.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.BookingApiReq
import com.hotel.theconvo.data.remote.dto.response.BookingApiResponse
import com.hotel.theconvo.data.remote.dto.response.SearchResult
import com.hotel.theconvo.destinations.LocationsListScreenDestination
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.destinations.ReservationScreenDestination
import com.hotel.theconvo.util.LoadingDialog
import com.hotel.theconvo.util.UiState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Destination
@Composable
fun ReservationScreen(
    navigator: DestinationsNavigator?
) {

    val textFieldShape = RoundedCornerShape(8.dp)

    val coupon = remember { mutableStateOf(TextFieldValue()) }

    var showDialog = remember{ mutableStateOf(false) }
    var uiState by remember { mutableStateOf<UiState<BookingApiResponse>>(UiState.Loading) }


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

                    showDialog.value = true
                    uiState = UiState.Loading

                    GlobalScope.launch {

                        withContext(Dispatchers.IO) {
                            var bookingApiReq = BookingApiReq(
                                adults = "2",
                                amount_paid = "1425.00",
                                booking_end_date = "2023-06-10",
                                booking_policy = "Request",
                                booking_start_date = "2023-06-08",
                                children = 0,
                                country = "AF",
                                email = "test@gmail.com",
                                firstName = "test",
                                inventory_count = "1",
                                lastName = "test",
                                payment_mode = "cash",
                                phone_no = "+923101127419",
                                property_id = "201",
                                property_room_type_id = "249_standard",
                                quantity = "1",
                                roomTypeName = "",
                                room_variation_id = "1"
                            )

                            uiState = UiState.Success(loginUseCase.bookingApiCall(bookingApiReq))

                            Log.i("Booking Api Response:", loginUseCase.bookingApiCall(bookingApiReq).toString())

                        }

                    }

                   // navigator?.navigate(ReservationScreenDestination())
                }) {

                Text(text = "CHECKOUT")


            }

        }



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
            val data = (uiState as UiState.Success<BookingApiResponse>).data
            // ...
            showDialog.value = false

     //       navigator?.navigate(LocationsListScreenDestination(noOfRooms = 2, adults = adults, childrens = childrens))


            //  navigator?.navigate(TabScreenDestination(true))
        }
        is UiState.Error -> {
            // Display an error message
            val message = (uiState as UiState.Error).message

            Toast.makeText(LocalContext.current,message, Toast.LENGTH_LONG).show()
            showDialog.value = false

            uiState = UiState.Loading

            /**remove below line after fixation */
            //navigator?.navigate(TabScreenDestination(true))
            // ...
        }

    }



    if (showDialog.value) {
        LoadingDialog(isShowingDialog = showDialog.value)

        uiState = UiState.Loading


    }



}