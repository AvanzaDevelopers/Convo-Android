package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.hotel.theconvo.MainActivity.Companion.amenitiesList
import com.hotel.theconvo.MainActivity.Companion.propList
import com.hotel.theconvo.MainActivity.Companion.reviews
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.response.Room
import com.hotel.theconvo.destinations.CheckoutScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HotelDetailScreen(

    navigator: DestinationsNavigator?,
    name: String,
    propertyImageUrl: String,
    roomImageUrl: String,
    roomType: String,
    amount: String,
    netAmount: String,
    currencySymbol: String,
    description: String,
    totalTaxes: String,
    propertyID: String,
    roomID: String,
    grandTotal: String


) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(propList.get(0).property.latitude, propList.get(0).property.longitude), 8f)
    }

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
                    painter = rememberAsyncImagePainter(model = propertyImageUrl.replace("\r\n","")),
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
                    painter = rememberAsyncImagePainter(model = roomImageUrl),
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

                        text = name,
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
                            text = roomType,
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

                    items(amenitiesList) {


                        // Text(text = "Silent Rooms")
                        AmentiesCard(it.name)


                    }

                })

            Spacer(modifier = Modifier.height(15.dp))
            
            Text(text = "About",fontSize = 20.sp,modifier = Modifier.padding(start = 10.dp))

            Spacer(modifier = Modifier.height(15.dp))

            ExpandableText(text = description.replace("<span>","")

                .replace("<br>","")
                .replace("\r","")
                .replace("\n","")
                .replace("<u>","")
                .replace("\nA","")
                .replace("\b","")
                .replace("</u>","")
                .replace("</span>","")
                .replace("<b>","")
                .replace("</b>",""),
                modifier = Modifier.padding(start = 10.dp,end = 10.dp))


            Spacer(modifier = Modifier.height(20.dp))



             if(reviews.size == 0) {

             }

              else {
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
                         cameraPositionState = cameraPositionState
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
                                     // painter = painterResource(id = R.drawable.ic_prof),
                                     painter = rememberAsyncImagePainter(
                                         model = "http://23.97.138.116:8004/${
                                             reviews.get(
                                                 0
                                             ).image
                                         }"
                                     ),
                                     contentDescription = " Prof image"
                                 )

                                 Spacer(modifier = Modifier.width(10.dp))

                                 Text(
                                     text = reviews.get(0).reviewer,
                                     fontSize = 13.sp
                                 )


                             }

                             Spacer(modifier = Modifier.height(10.dp))


                             Text(
                                 text = reviews.get(0).review,
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
             }









        }


         Spacer(modifier = Modifier.height(10.dp))

        /** Make this Row unscrollable */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                //.align(Alignment.BottomCenter)
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {



                Text(
                    text = "${amount}\n${currencySymbol}/Night",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 5.dp))


            Spacer(modifier = Modifier.width(20.dp))


            Card(modifier = Modifier.weight(2f),
                shape = RoundedCornerShape(4.dp)) {


                Button(

                    modifier = Modifier
                        //.weight(2f)
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(color = MaterialTheme.colors.primary), onClick = {

                        navigator?.navigate(
                            CheckoutScreenDestination(
                                amount,
                                propertyImageUrl.toString(),
                                roomImageUrl.toString(),
                                roomType,
                                netAmount,
                                currencySymbol,
                                totalTaxes,
                                propertyID,
                                roomID,
                                grandTotal
                            )

                        )


                    }) {

                    Text(text = "Book My Stay")

                } // Button ends here

            }


        }
        
    } // Column ends here
}


@Composable
fun AmentiesCard(title : String) {

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



} // Amenties card ends here




@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    minimizedMaxLines: Int = 5,
) {
    var cutText by remember(text) { mutableStateOf<String?>(null) }
    var expanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    val seeMoreSizeState = remember { mutableStateOf<IntSize?>(null) }
    val seeMoreOffsetState = remember { mutableStateOf<Offset?>(null) }

    // getting raw values for smart cast
    val textLayoutResult = textLayoutResultState.value
    val seeMoreSize = seeMoreSizeState.value
    val seeMoreOffset = seeMoreOffsetState.value

    LaunchedEffect(text, expanded, textLayoutResult, seeMoreSize) {
        val lastLineIndex = minimizedMaxLines - 1
        if (!expanded && textLayoutResult != null && seeMoreSize != null
            && lastLineIndex + 1 == textLayoutResult.lineCount
            && textLayoutResult.isLineEllipsized(lastLineIndex)
        ) {
            var lastCharIndex = textLayoutResult.getLineEnd(lastLineIndex, visibleEnd = true) + 1
            var charRect: Rect
            do {
                lastCharIndex -= 1
                charRect = textLayoutResult.getCursorRect(lastCharIndex)
            } while (
                charRect.left > textLayoutResult.size.width - seeMoreSize.width
            )
            seeMoreOffsetState.value = Offset(charRect.left, charRect.bottom - seeMoreSize.height)
            cutText = text.substring(startIndex = 0, endIndex = lastCharIndex)
        }
    }

    Box(modifier) {
        Text(
            text = cutText ?: text,
            maxLines = if (expanded) Int.MAX_VALUE else minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResultState.value = it },
        )
        if (!expanded) {
            val density = LocalDensity.current
            Text(
                "... See more",
                onTextLayout = { seeMoreSizeState.value = it.size },
                modifier = Modifier
                    .then(
                        if (seeMoreOffset != null)
                            Modifier.offset(
                                x = with(density) { seeMoreOffset.x.toDp() },
                                y = with(density) { seeMoreOffset.y.toDp() },
                            )
                        else
                            Modifier
                    )
                    .clickable {
                        expanded = true
                        cutText = null
                    }
                    .alpha(if (seeMoreOffset != null) 1f else 0f)
            )
        }
    }
}






/**@Preview(showBackground = true)
@Composable

fun HotelDetailPreview() {
    HotelDetailScreen(navigator = null)
}*/