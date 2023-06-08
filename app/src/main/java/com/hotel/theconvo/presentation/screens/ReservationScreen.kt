package com.hotel.theconvo.presentation.screens

import android.graphics.Paint.Style
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.BookingApiReq
import com.hotel.theconvo.data.remote.dto.response.BookingApiResponse
import com.hotel.theconvo.data.remote.dto.response.SearchResult
import com.hotel.theconvo.destinations.LocationsListScreenDestination
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.destinations.ReservationScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.hotel.theconvo.util.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@Destination
@Composable
fun ReservationScreen(
    navigator: DestinationsNavigator?,
    propertyImageUrl: String,
    roomImageUrl: String,
    amount: String,
    roomName: String,
    currencySymbol: String,
    totalTaxes: String,
    propertyID: String,
    roomID: String,
    grandTotal: String
) {

    val textFieldShape = RoundedCornerShape(8.dp)

    val coupon = remember { mutableStateOf(TextFieldValue()) }

    var showDialog = remember{ mutableStateOf(false) }
    var uiState by remember { mutableStateOf<UiState<BookingApiResponse>>(UiState.Loading) }

    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)

    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }

    var start_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("start_date", "") ?: "") }
    var end_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("end_date", "") ?: "") }

    var email by rememberSaveable{
        mutableStateOf(sharedPreferences.getString(AllKeys.email,"") ?: "")
    }


    var adults by rememberSaveable{
        mutableStateOf(sharedPreferences.getString("adults","0") ?: "0")
    }

    var kids by rememberSaveable{
        mutableStateOf(sharedPreferences.getString("kids","0") ?: "0")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Log.i("Reservation Start Date", start_date)
        Log.i("Reservation End Date",end_date)

        Box(
            modifier = Modifier
                .fillMaxWidth()


        ) {






            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.FillBounds,
              //  painter = painterResource(id = R.drawable.ic_stays),
                painter = rememberAsyncImagePainter(model = propertyImageUrl.replace("\r\n","")),
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

                        Row(
                            modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Image(
                                modifier = Modifier.size(25.dp),
                                // .padding(top = 30.dp, start = 20.dp),
                                painter = painterResource(id = R.drawable.ic_calendar),
                                contentDescription = "calendar icon"
                            )

                            /** Image(
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier
                            .height(1.dp)
                            .width(70.dp)
                            .padding(start = 12.dp, top = 2.dp, bottom = 2.dp),
                            painter = painterResource(id = R.drawable.ic_straight_line),
                            contentDescription = "straight line"
                            ) */
                            Divider(
                                modifier = Modifier
                                    .height(2.dp)
                                    .width(100.dp)
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
                    }
                        Row(modifier = Modifier.padding(top = 20.dp)) {

                            Column(
                                modifier = Modifier.padding(start = 20.dp)
                            ) {
                                Text(text = "From")

                                Text(
                                    fontWeight = FontWeight.Black ,
                                    text = "${formatDate(start_date).split(",")[0]}\n${formatDate(start_date).split(",")[1]}"
                                )
                            }


                            //Spacer(modifier = Modifier.height(20.dp))

                            Column(
                                modifier = Modifier.padding(start= 35.dp)
                            ) {


                                Text(text = "Till")

                                Text(
                                    fontWeight = FontWeight.Black,
                                    text = "${formatDate(end_date).split(",")[0]}\n${formatDate(end_date).split(",")[1]}"
                                )
                            }

                        }


                    //Row ends here

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

                        Text(text = "${adults} Adults", modifier = Modifier.padding(start = 10.dp))

                        Spacer(modifier = Modifier.weight(1f))

                        Image(painter = painterResource(id = R.drawable.ic_kids),
                            contentDescription = "Kids Image",
                            modifier = Modifier.size(25.dp)
                        )
                        Text(text = "${kids} Kids", modifier = Modifier.padding(start = 10.dp, end = 60.dp))
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
                        //painter = painterResource(id = R.drawable.ic_stays)
                        painter = rememberAsyncImagePainter(model = roomImageUrl),

                         contentDescription = "Stays Image")

                    Text(text = roomName, modifier = Modifier.weight(5f))

                }


            }





        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            Text(text = "Total", modifier = Modifier.padding(start = 35.dp))

            Spacer(modifier = Modifier.weight(1f))

            Text(text = "${amount}${currencySymbol}", modifier = Modifier.padding(end = 35.dp))


        }

        Spacer(modifier = Modifier.height(20.dp))

        Divider(modifier = Modifier.padding(start = 25.dp,end = 25.dp))

        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier.fillMaxWidth()) {

            Text(text = "Tax", modifier = Modifier.padding(start = 35.dp))

            Spacer(modifier = Modifier.weight(1f))

            Text(text = "${totalTaxes} ${currencySymbol}", modifier = Modifier.padding(end = 35.dp))


        }



        Spacer(modifier = Modifier.height(30.dp))


        Box(
            modifier = Modifier.fillMaxWidth()
        ) {


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

            Text(text = "Apply",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 50.dp),
                color = Color(0XFFfdad02))

        }

        Spacer(
            modifier =  Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
        ) {

            Column(
                modifier = Modifier.weight(1f)

            ) {


                Text(text = "TOTAL", fontSize = 10.sp)
                Row {


                    Text(text = grandTotal, fontSize = 20.sp)
                    Text(
                        text = currencySymbol,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(start = 5.dp, top = 8.dp)
                    )
                }
            }


            Card(modifier = Modifier.weight(2f),shape = RoundedCornerShape(4.dp)) {


                Button(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .fillMaxWidth(),
                       // .weight(2f),
                    onClick = {

                        showDialog.value = true
                        uiState = UiState.Loading

                        GlobalScope.launch {

                            withContext(Dispatchers.IO) {
                                var bookingApiReq = BookingApiReq(
                                    adults = adults,
                                    amount_paid = "1425.00",
                                    booking_end_date = end_date,
                                    booking_policy = "Request",
                                    booking_start_date = start_date,
                                    children = kids.toInt(),
                                    country = "AF",
                                    email = email,
                                    firstName = "test",
                                    inventory_count = "1",
                                    lastName = "test",
                                    payment_mode = "cash",
                                    phone_no = "+923101127419",
                                    //property_id = "201",
                                    //property_room_type_id = "249_standard",
                                    property_id = propertyID,
                                    property_room_type_id = roomID,
                                    quantity = "1",
                                    roomTypeName = "",
                                    room_variation_id = "1"
                                )

                                uiState =
                                    UiState.Success(loginUseCase.bookingApiCall(bookingApiReq))

                                Log.i(
                                    "Booking Api Response:",
                                    loginUseCase.bookingApiCall(bookingApiReq).toString()
                                )

                            }

                        }

                        // navigator?.navigate(ReservationScreenDestination())
                    }) {

                    Text(text = "CHECKOUT")


                }// Button ends here
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




            if(data.responseCode == 200) {
                navigator?.navigate(TabScreenDestination(isStay = true, true))
            }
            else {
                navigator?.navigate(TabScreenDestination(isStay = true, false))
            }
            Toast.makeText(LocalContext.current,data.responseDescription, Toast.LENGTH_LONG).show()

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

fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("EEEE dd, MMMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}