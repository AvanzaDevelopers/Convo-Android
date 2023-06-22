package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.edit
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.BookingListReq
import com.hotel.theconvo.data.remote.dto.req.HappeningNowReq
import com.hotel.theconvo.data.remote.dto.req.HappeningNowSearchCriteria
import com.hotel.theconvo.data.remote.dto.req.Page
import com.hotel.theconvo.data.remote.dto.response.*
import com.hotel.theconvo.destinations.*
import com.hotel.theconvo.presentation.composableItems.OurStaysItem
import com.hotel.theconvo.reels.Reel
import com.hotel.theconvo.ui.theme.darkCardColor
import com.hotel.theconvo.util.AllKeys
import com.hotel.theconvo.util.PreviewCard
import com.hotel.theconvo.util.SharedPrefsHelper
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


@UnstableApi
@Composable
fun StayScreen(

    isStay: Boolean,
    navigator: DestinationsNavigator?
) {



    val isDarkMode = MaterialTheme.colors.isLight

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

   // var happeningNowData = emptyList<HappeningNowData>()

    var happeningNowData by remember {
        mutableStateOf<List<HappeningNowData>>(emptyList())
    }

    var getBookingList by remember{
        mutableStateOf<List<Upcomming>>(emptyList())
    }


   var happeningNowImages = remember {
       mutableStateListOf<String>()
   }

    var reels = remember{
        mutableStateListOf<Reel>()
    }

    var isStaySuccess by remember {
        mutableStateOf(false)
    }



    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)
    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }
    var token by rememberSaveable { mutableStateOf(sharedPreferences.getString(AllKeys.token, "") ?: "") }



    /**Commenting happening now api for now */
    LaunchedEffect( Unit) {

       withContext(Dispatchers.IO) {


           try {
               var happeningNowReq = HappeningNowReq(
                   searchCriteria = HappeningNowSearchCriteria(
                       pageNo = 1,
                       pageSize = 3,
                       country = "",
                       city = "",
                       hotel = "",
                       name = ""
                   )
               )

               happeningNowData =
                   loginUseCase.happeningApiCall(happeningNowReq).responseDescription.data


           }
           catch (ex: Exception) {




           }

           try {
               var getBookingListReq = BookingListReq(

                   isui = true,
                   page = Page(
                       pageSize = 10,
                       currentPageNo = 1
                   )
               )

               Log.i("Token:",token)



               getBookingList = loginUseCase.getBookingList(
                   token, getBookingListReq
               ).responseDescription.upcomming

               isStaySuccess = true
           }
           catch (ex: Exception) {

               sharedPreferences.edit {
                   putString(AllKeys.token,"")
               }
               Log.i("Booking Exception",ex.message.toString())

               isStaySuccess = false

           }

          // Log.i("Happening Now Data",happeningNowData.toString())




       } // Dispatchers ends here


    }


    if (isVisible) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),


            ) {




           // item {


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



                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, top = 150.dp)
                            .align(Alignment.BottomCenter)
                            .shadow(elevation = 5.dp, shape = textFieldShape)
                            .clip(textFieldShape)

                            .clickable {
                                navigator?.navigate(TabScreenDestination(false, false))

                            },
                        backgroundColor = if (isSystemInDarkTheme()) darkCardColor else Color.White

                    ) {



                         Box(
                             modifier = Modifier.fillMaxWidth()
                         ) {


                             Text(

                                 text = "Book Your Stay",
                                 fontWeight = FontWeight.Medium,
                                 fontSize = 20.sp ,
                                color =  if (isSystemInDarkTheme()) Color.White else Color.Black ,
                                 textAlign = TextAlign.Center,
                                 modifier = Modifier
                                     .padding(
                                         top = 20.dp, bottom = 20.dp
                                     )
                                     .fillMaxWidth()


                             )
                         }




                    }

                   /** SearchBoxItem(
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

                    )*/

                }


            Spacer(modifier = Modifier.height(10.dp))

            if (isStaySuccess) {
                Text(
                    text = "My Stays",
                    modifier = Modifier.padding(start = 10.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))


                LazyRow() {

                    items(getBookingList) { data ->

                        Card(
                            modifier = Modifier

                                .height(200.dp)
                                .width(300.dp)
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
                                            painter = painterResource(id = R.drawable.ic_location),
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

                                            painter = painterResource(id = R.drawable.ic_bed),
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

                                        /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                                        modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                                        contentDescription = "Arrow")*/


                                    }

                                } //colmn ends here


                            }

                        }


                    } // items ends here


                }// Lazy Row Ends here


                Spacer(modifier = Modifier.height(10.dp))
            }





                    Text(
                        text = "Happening Now",
                        fontSize = 17.sp,
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black ,
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(15.dp))



            val listState = rememberLazyListState()
            val focusedItemIndex = remember { mutableStateOf(-1) }

                    LazyRow(

                        state = listState,
                        //contentPadding = PaddingValues(0.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)

                    ) {

                        itemsIndexed(happeningNowData) {  index, data ->




                            var hasFocus = index == focusedItemIndex.value
                            val focusRequester = remember { FocusRequester() }

                            PreviewCard(


                            modifier= Modifier.clickable {
                                    navigator?.navigate(ReelsScreenDestination())
                                },
                                thumbnailUrl = "http://23.97.138.116:7001/${data.images.get(0).path}" ,
                                cardWidth = 165.dp,
                                cardHeight = 250.dp,
                                videoUrl = "http://23.97.138.116:7001/${data.videos.get(0).path}",
                               hasFocus =  true
                                )




                           /** Box(
                                modifier = Modifier
                                    .clickable {

                                        navigator?.navigate(ReelsScreenDestination())
                                    }
                                    .height(250.dp)
                                    .width(165.dp)
                                    .clip(RoundedCornerShape(8.dp))

                            ) {



                                Image(

                                    painter = rememberAsyncImagePainter(

                                        model = "http://23.97.138.116:7001/${data.images.get(0).path}"
                                    ),


                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillParentMaxSize()
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(8.dp))


                                )


                            }*/ // Box Ends Here


                        }
                    }





                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                    ) {

                        Text(
                            text = "Explore",
                            fontSize = 17.sp,
                            modifier = Modifier.weight(3f),
                            color = if (isSystemInDarkTheme()) Color.White else Color.Black ,
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

                                    navigator?.navigate(RoomScreenDestination())
                                },
                            textAlign = TextAlign.Right,
                            color = Color(0XFFfdad02)
                        )


                    }

                    Spacer(modifier = Modifier.height(15.dp))

                    OurStaysItem("Greece","https://www.oneperfectstay.com/storage/uploads/P6hhCXJnuxEVUUtW2ngAO63l8jSZd2qREczXW5ce.jpg","","","945","","USD",navigator,"","","","","","","")



                //}
           // } // item 3 ends here
        }
    }
}


fun convertDateFormat(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM MMM", Locale.getDefault())

    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)
}

fun convertDatesToFormattedString(date1: String, date2: String): String {
    val convertedDate1 = convertDateFormat(date1)
    val convertedDate2 = convertDateFormat(date2)

    val startDate = convertedDate1.split(" ")[0].split("-")[0]
    val endDate = convertedDate2.split(" ")[0].split("-")[0]
    val month = convertedDate1.split(" ")[1]




    return "$startDate-$endDate $month"
}




