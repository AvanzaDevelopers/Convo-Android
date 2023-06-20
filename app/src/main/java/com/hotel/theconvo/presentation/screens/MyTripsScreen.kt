package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.R
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.data.remote.dto.req.BookingListReq
import com.hotel.theconvo.data.remote.dto.req.Page
import com.hotel.theconvo.data.remote.dto.response.Upcomming
import com.hotel.theconvo.destinations.StaysItemListScreenDestination
import com.hotel.theconvo.util.AllKeys
import com.hotel.theconvo.util.SharedPrefsHelper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Destination
@Composable
fun MyTripsScreen(navigator: DestinationsNavigator?) {

    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)
    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }
    var token by rememberSaveable { mutableStateOf(sharedPreferences.getString(AllKeys.token, "") ?: "") }

    var getBookingList by remember{
        mutableStateOf<List<Upcomming>>(emptyList())
    }

    LaunchedEffect(Unit ) {

        withContext(Dispatchers.IO) {

            try {
                var getBookingListReq = BookingListReq(

                    isui = true,
                    page = Page(
                        pageSize = 10,
                        currentPageNo = 1
                    )
                )

                getBookingList = MainActivity.loginUseCase.getBookingList(
                    token, getBookingListReq
                ).responseDescription.upcomming





            }
            catch (ex: Exception) {

                Log.i("Booking Exception",ex.message.toString())

            }

        } //Coroutine ends here
    }//Launched Effect ends here

    Column {

    Spacer(modifier = Modifier.height(10.dp))

    Text(text = "My Trips", fontSize = 25.sp,modifier = Modifier.padding(start = 10.dp, top = 10.dp))

    Spacer(modifier = Modifier.height(10.dp))

    LazyColumn() {
        items(getBookingList) { data ->

            Card(
                modifier = Modifier

                    .height(200.dp)
                    //.width(300.dp)
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    .shadow(elevation = 5.dp)
                    .clickable {
                        //navigator?.navigate(HotelDetailScreenDestination(title,"",""))
                        // navigator?.navigate(HotelDetailScreenDestination(title,hotelImageUrl,imageUrl,roomType,roomRate,netAmount,currencySymbol))

                        navigator?.navigate(
                            StaysItemListScreenDestination(
                                data.property_id,
                                "${(data.bookingDetail.children.toInt() + data.bookingDetail.adults.toInt()).toString()} Guests"
                            )
                        )

                    }
            ) {

                Row(modifier = Modifier.fillMaxWidth()) {

                    Image(
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .weight(3f)
                            .fillMaxHeight(),
                        //painter = painterResource(id = R.drawable.ic_stays) ,
                        painter = rememberAsyncImagePainter(
                            model = data.images.get(0).image_path.replace(
                                "\r\n",
                                ""
                            )
                        ),
                        contentDescription = "Stays Image"
                    )

                    Column(
                        modifier = Modifier.weight(5f)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 10.dp, top = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = com.hotel.theconvo.R.drawable.ic_location),
                                modifier = Modifier
                                    .size(15.dp)
                                    .padding(top = 1.dp),
                                contentDescription = "Location Icon"
                            )



                            Text(
                                text = data.propertyDetail.address_line_2,
                                color = Color(0XFFfdad02),
                                fontSize = 10.sp,
                                modifier = Modifier.padding(start = 10.dp)
                            )

                        }


                        Text(
                            text = data.name,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                            maxLines = 2
                        )


                        Row(
                            modifier = Modifier
                                .padding(top = 15.dp, start = 10.dp)
                                .fillMaxWidth()
                        ) {

                            Image(

                                painter = painterResource(id = com.hotel.theconvo.R.drawable.ic_bed),
                                modifier = Modifier
                                    .size(15.dp)
                                    .padding(top = 2.dp),
                                contentDescription = "Bed Icon"
                            )

                            Text(

                                text = "${(data.bookingDetail.children.toInt() + data.bookingDetail.adults.toInt()).toString()} Guests",
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .padding(start = 10.dp)

                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp, start = 10.dp, bottom = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Column()

                            {


                                Text(
                                    text = convertDatesToFormattedString(
                                        data.booking_start_date,
                                        data.booking_end_date
                                    )
                                )
                                // Text(text = "12-15 May")
                                Text(text = data.booking_start_date.split("-")[0])

                            }

                            Spacer(modifier = Modifier.weight(1f))

                            OutlinedButton(onClick = {  }) {
                                Text(text = "Review")
                            }

                            Spacer(modifier = Modifier.width(5.dp))

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/

                            /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                            modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                            contentDescription = "Arrow")*/


                        }

                    } //colmn ends here
                }

                }

            }


        }
    }




}