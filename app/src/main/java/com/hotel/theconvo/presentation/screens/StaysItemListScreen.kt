package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.CheckoutScreenDestination
import com.hotel.theconvo.util.AllKeys
import com.hotel.theconvo.util.SharedPrefsHelper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun StaysItemListScreen(
    navigator: DestinationsNavigator?
) {
    /**val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(MainActivity.propList.get(0).property.latitude, MainActivity.propList.get(0).property.longitude), 8f)
    }*/



    Column(
        modifier = Modifier.fillMaxSize()
    ) {



        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(9f)
                .verticalScroll(rememberScrollState())
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //.weight(2f)
                    .height(300.dp)

            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                   // painter = rememberAsyncImagePainter(model = propertyImageUrl.replace("\r\n","")),

                    painter = painterResource(id = R.drawable.ic_stays),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = "Slider Image"
                )

                Image(

                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_convo_logo),
                    contentDescription = "Convo Logo"
                )

                Image(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 50.dp, end = 50.dp)
                        .align(Alignment.BottomCenter)
                        .padding(top = 100.dp),
                    //painter = rememberAsyncImagePainter(model = roomImageUrl),
                    painter = painterResource(id = R.drawable.ic_stays),
                    contentDescription = "Stay Image"

                )

            }


            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
                // horizontalArrangement = Arrangement.spacedBy(10.dp)

            ) {

                Column(Modifier.weight(1f)) {


                    Text(

                        //text = name,
                        text = "sample name",
                        maxLines = 2,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(start = 20.dp, top = 5.dp)
                        //.width(180.dp)
                        //.weight(1f)
                        //.padding(start = 20.dp).weight(1f).fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        Image(
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .padding(start = 10.dp, end = 10.dp),
                            contentScale = ContentScale.Fit,
                            painter = painterResource(id = R.drawable.ic_bed),
                            contentDescription = "Bed Icon"
                        )

                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                           // text = roomType,
                            text = "Room Name",
                            fontSize = 17.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                }


                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .weight(1f)
                    // .weight(1f).fillMaxWidth()

                )

                {

                    Image(

                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .fillMaxSize(),

                        painter = painterResource(id = R.drawable.room_background),
                        contentDescription = "Image"
                    )

                }

            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Amenities", fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))

            Spacer(modifier = Modifier.height(10.dp))
            LazyRow(
                modifier = Modifier.padding(start = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp), content = {

                   // items(MainActivity.amenitiesList) {
                     items(10) {

                        // Text(text = "Silent Rooms")
                        AmentiesCardStays("sample name")


                    }

                })

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "About",fontSize = 20.sp,modifier = Modifier.padding(start = 10.dp))

            Spacer(modifier = Modifier.height(15.dp))

           /** Text(text = description.replace("<span>","")

                .replace("<br>","")
                .replace("\r","")
                .replace("\n","")
                .replace("<u>","")
                .replace("\nA","")
                .replace("\b","")
                .replace("</u>","")
                .replace("</span>","")
                .replace("<b>","")
                .replace("</b>",""),*/
            Text(text = "Lorem ipsum dfkdfjfdk dlfkdflkdfk dfklfdlkdklf fsklfdkldfkl",
                modifier = Modifier.padding(start = 10.dp,end = 10.dp))


            Spacer(modifier = Modifier.height(20.dp))



           /** if(MainActivity.reviews.size == 0) {

            }*/

            //else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(bottom = 20.dp)
                ) {

                    GoogleMap(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                      //  cameraPositionState = cameraPositionState
                    ) {


                    }



                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .align(Alignment.BottomCenter)
                            .padding(start = 30.dp, end = 30.dp, top = 50.dp)
                            .shadow(elevation = 5.dp)


                    ) {


                        Column {


                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()

                                    .padding(start = 20.dp, top = 20.dp),
                                verticalAlignment = Alignment.CenterVertically


                            ) {

                                Image(
                                    modifier = Modifier
                                        .size(25.dp)
                                        .clip(RoundedCornerShape(16.dp)),
                                  /**  painter = rememberAsyncImagePainter(
                                        model = "http://23.97.138.116:8004/${
                                            MainActivity.reviews.get(
                                                0
                                            ).image
                                        }"
                                    ),*/
                                    painter = painterResource(id = R.drawable.ic_stays),
                                    contentDescription = " Prof image"
                                )

                                Spacer(modifier = Modifier.width(10.dp))

                                Text(
                                   /** text = MainActivity.reviews.get(0).reviewer,*/
                                    text = "Reviewer Name",
                                    fontSize = 13.sp
                                )


                            }

                            Spacer(modifier = Modifier.height(10.dp))


                            Text(
                                /**text = MainActivity.reviews.get(0).review,*/
                                text = "Lorem ipsum dfkdfj dflfdklfdklk dfkldfklkdfl sfjdkkfjdkjfd",
                                modifier = Modifier.padding(end = 20.dp, start = 40.dp),
                                maxLines = 4
                            )


                        }


                    }

                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(100.dp)
                            .padding(end = 50.dp, top = 30.dp) ,
                        painter = painterResource(id = R.drawable.ic_convo_inverted_coma),
                        contentDescription = "inverted commas")


                } //Box ends here
          //  } // else ends here









        }


        Spacer(modifier = Modifier.height(10.dp))

        /** Make this Row unscrollable */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                //.align(Alignment.BottomCenter)
                .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp)
        ) {


            Column(
                modifier = Modifier.weight(1f)
            ) {



                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp,end= 10.dp)
                        .background(color = Color(0xFF04113B).copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp))
                ) {
                    Text(

                        textAlign = TextAlign.Center ,
                        modifier = Modifier


                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 5.dp)
                        ,
                            //.background(color = Color(0xFF04113B).copy(alpha = 0.1f), shape = RoundedCornerShape(16.dp)),
                        text = "246785435458",
                        fontSize = 10.sp

                    )
                }





                Text(
                    // text = "${amount}\n${currencySymbol}/Night",
                    text = "12 - 15 MAY",
                    fontSize = 13.sp,
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(bottom = 2.dp))

                Text(
                    // text = "${amount}\n${currencySymbol}/Night",
                    text = "2023",
                    fontSize = 10.sp,
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(bottom = 2.dp))



            }




            Spacer(modifier = Modifier.width(20.dp))


            Card(
                modifier = Modifier
                    .weight(2f)
                    .height(70.dp),
                shape = RoundedCornerShape(4.dp)

                    //.clip(RoundedCornerShape(8.dp))
            ) {


                Button(

                    modifier = Modifier
                        .fillMaxWidth()
                        //.weight(2f)
                        //.height(60.dp)
                        //.padding(16.dp)
                        .background(color = MaterialTheme.colors.primary),
                    //.clip(RoundedCornerShape(8.dp)),
                    //shape = RoundedCornerShape(8.dp),


                    onClick = {

                        /** navigator?.navigate(
                        CheckoutScreenDestination(
                        amount,
                        propertyImageUrl.toString(),
                        roomImageUrl.toString(),
                        roomType,
                        netAmount,
                        currencySymbol,
                        totalTaxes
                        )

                        )*/


                    }) {

                    Text(text = "Check In")

                }

            }

        }

    } // Column ends here


}


@Composable
fun AmentiesCardStays(title : String) {

    Card(modifier = Modifier
        .shadow(elevation = 5.dp)
        .width(120.dp)
        .height(140.dp)) {

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_silent_rooms),
                contentDescription = "Silent Room Icon",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 10.dp)
            )

            Text(
                text = title,
                maxLines = 2 ,
                textAlign = TextAlign.Center,
                fontSize = 13.sp ,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 20.dp),


                )

        }


    }



}//Amenties card ends here