package com.hotel.theconvo.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.*
import com.hotel.theconvo.presentation.composableItems.OurStaysItem
import com.hotel.theconvo.presentation.composableItems.SearchBoxItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlin.coroutines.coroutineContext

@Composable
fun StayScreen(

    isStay: Boolean,
    navigator: DestinationsNavigator?
) {





    Spacer(modifier = Modifier.height(10.dp))



        MainStayScreen(
            isStay,
            navigator
        )







}



@Composable
fun MainStayScreen(
    isVisible: Boolean,
    navigator: DestinationsNavigator?) {


    if (isVisible) {

        LazyColumn(

            modifier = Modifier.fillMaxWidth(),


            ) {


            item {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.roof_image),
                        contentDescription = "Roof Image",
                        modifier = Modifier.padding(top = 25.dp)
                    )


                    Box(
                        modifier = Modifier
                            .height(50.dp)
                            .width(50.dp)
                            .align(Alignment.TopEnd)

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_convo_inverted_coma),
                            contentDescription = "Inverted comma at top",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )


                    }

                    val textFieldShape = RoundedCornerShape(8.dp)




                    SearchBoxItem(
                        modifier = Modifier


                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 170.dp)
                            .align(Alignment.BottomCenter)
                            .shadow(elevation = 5.dp, shape = textFieldShape)
                            .clip(textFieldShape),
                        textFieldShape,
                        navigator,
                        onClick = {

                            //navigator?.navigate(HotelsListScreenDestination())
                            navigator?.navigate(TabScreenDestination(false))
                            //TabScreenDestination(false)
                        }

                    )

                }


            }




            item {

                Column {


                    Text(
                        text = "Happening Now",
                        fontSize = 17.sp,
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    LazyRow(

                        //contentPadding = PaddingValues(0.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)

                    ) {
                        items(10) {

                            //  Spacer(modifier = Modifier.width(10.dp))


                            Box(
                                modifier = Modifier
                                    .clickable {
                                       // navigator?.navigate(VideoPlayerScreenDestination())
                                        navigator?.navigate(ReelsScreenDestination())
                                    }
                                    .height(250.dp)
                                    .width(165.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                // .padding(start = if (it == 0) 20.dp else (-8).dp)
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.ic_happening2),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    //alignment = Alignment.TopStart,
                                    modifier = Modifier
                                        .fillParentMaxSize()
                                        .aspectRatio(1f)

                                )
                            }


                        }
                    }
                }

            }


            item {
                Column(modifier = Modifier.padding(bottom = 10.dp)) {


                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                    ) {

                        Text(
                            text = "Our Stays",
                            fontSize = 17.sp,
                            modifier = Modifier.weight(3f),
                            textAlign = TextAlign.Left
                        )

                        Text(
                            text = "VIEW ALL",
                            fontSize = 13.sp,
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    Log.i("Item Clicked:", "Item Clicked")
                                    //navigator?.navigate(LoginScreenDestination())
                                    //navigator?.navigate(HotelDetailScreenDestination())

                                },
                            textAlign = TextAlign.Right,
                            color = Color(0XFFfdad02)
                        )


                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    OurStaysItem("Greece","https://www.oneperfectstay.com/storage/uploads/P6hhCXJnuxEVUUtW2ngAO63l8jSZd2qREczXW5ce.jpg","","","945",navigator)



                }
            }
        }
    }
}





