package com.hotel.theconvo.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.HappeningNowReq
import com.hotel.theconvo.data.remote.dto.req.HappeningNowSearchCriteria
import com.hotel.theconvo.data.remote.dto.response.HappeningNowData
import com.hotel.theconvo.data.remote.dto.response.Room
import com.hotel.theconvo.destinations.*
import com.hotel.theconvo.presentation.composableItems.OurStaysItem
import com.hotel.theconvo.presentation.composableItems.SearchBoxItem
import com.hotel.theconvo.reels.Reel
import com.hotel.theconvo.reels.ReelInfo
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

   // var happeningNowData = emptyList<HappeningNowData>()

    var happeningNowData by remember {
        mutableStateOf<List<HappeningNowData>>(emptyList())
    }


   var happeningNowImages = remember {
       mutableStateListOf<String>()
   }

    var reels = remember{
        mutableStateListOf<Reel>()
    }


    /**Commenting happening now api for now */
    LaunchedEffect( Unit) {

       withContext(Dispatchers.IO) {

           var happeningNowReq = HappeningNowReq(searchCriteria = HappeningNowSearchCriteria(
               pageNo = 1,
               pageSize = 10,
               country = "",
               city = "",
               hotel = "",
               name = ""
           ))

         happeningNowData = loginUseCase.happeningApiCall(happeningNowReq).responseDescription.data



         /** happeningNowData.forEach {

             it.images.forEach {
                 reels.add( Reel(

                     reelUrl = "http://23.97.138.116:7001/${it.path}",isFollowed = true, ReelInfo(
                         "basicswithbails",
                         "https://scontent-mct1-1.cdninstagram.com/v/t51.2885-19/54513550_542278632927468_3913108728739528704_n.jpg?stp=dst-jpg_s150x150&_nc_ht=scontent-mct1-1.cdninstagram.com&_nc_cat=1&_nc_ohc=cz9_T-UKvFMAX8iRnf5&edm=ALbqBD0BAAAA&ccb=7-5&oh=00_AT8Xtd3LXmYVOEbbtGA2W8dQGlbJHyv-gN5kJgH4IW5LAg&oe=6299BF34&_nc_sid=9a90d6",
                         "Ran with the trend, but made it cookies & cream version \uD83E\uDD5B\uD83C\uDF6A\n" +
                                 "\n" +
                                 "INGREDIENTS -\n" +
                                 "Plain rice cake\n" +
                                 "Peanut butter (reminded me of the Olsen twins dipping Oreos into peanut butter, please tell me I’m not the only one who remembers this)\n" +
                                 "Oreo pieces (gluten free if desired)\n" +
                                 "White chocolate chips (approx 2 tbsp)\n" +
                                 "Coconut oil (approx 1/2 tsp)\n" +
                                 "\n" +
                                 "DIRECTIONS -\n" +
                                 "1) Layer a generous amount of your favourite peanut butter or nut butter of choice on a rice cake. Then top with crumbled up Oreo pieces.\n" +
                                 "2) In a small bowl, microwave white chocolate chips and coconut oil in 30 second increments. Once completely melted, pour overtop Oreos and disperse evenly. Then top with a few more Oreo pieces.\n" +
                                 "3) Freeze for 20 minutes, then break into it and inhale!\n" +
                                 "\n" +
                                 "#ricecakes #ricecakedessert #easytreat #glutenfreetreat #glutenfreedessert #peanutbutterandchocolate #frozenbananas #glutenfreeblogger #glutenfreerecipes #easyrecipesathome #foodblogfeed #thebakefeed #feedfeedglutenfree #peanutbutterbanana #kelownafoodie",
                         false,
                         4193,
                         128,
                         "altego_music • Original Audio",
                         "https://scontent-mct1-1.cdninstagram.com/v/t51.2885-19/274969059_117165994229563_8118811520835177688_n.jpg?stp=dst-jpg_s150x150&_nc_ht=scontent-mct1-1.cdninstagram.com&_nc_cat=1&_nc_ohc=rr7KUgMYI54AX_cD8R0&edm=ACaJ6XgBAAAA&ccb=7-5&oh=00_AT8tV71goYhWRV4anlVv4NJYqVSJbQ9KqoHfNHKYYRx_vw&oe=629A762D&_nc_sid=0cc1b1",
                         location = "Kelowna, British Columbia"
                     )
                 )
                 )
             }
         }  for each ends here */


       }


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
                                navigator?.navigate(TabScreenDestination(false))

                            }

                    ) {



                         Box(
                             modifier = Modifier.fillMaxWidth()
                         ) {


                             Text(

                                 text = "Book Your Stay",
                                 fontWeight = FontWeight.Medium,
                                 fontSize = 20.sp ,
                                 color = Color(0XFF000000) ,
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

            Text(
                text = "My Stays",
                modifier = Modifier.padding(start = 10.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))


            LazyRow(){

                items(10) {

                    Card(
                        modifier = Modifier

                            .height(180.dp)
                            .width(280.dp)
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                            .shadow(elevation = 5.dp)
                            .clickable {
                                //navigator?.navigate(HotelDetailScreenDestination(title,"",""))
                                // navigator?.navigate(HotelDetailScreenDestination(title,hotelImageUrl,imageUrl,roomType,roomRate,netAmount,currencySymbol))
                            }
                    ) {

                        Row(modifier = Modifier.fillMaxWidth()) {

                            Image(
                                contentScale = ContentScale.FillBounds ,
                                modifier = Modifier.weight(3f),
                                painter = painterResource(id = R.drawable.ic_stays) ,
                                contentDescription = "Stays Image"
                            )

                            Column(
                                modifier = Modifier.weight(5f)
                            ) {

                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 10.dp)) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_location),
                                        modifier = Modifier
                                            .size(15.dp)
                                            .padding(top = 1.dp),
                                        contentDescription = "Location Icon")

                                    Text(text = "Greece", color = Color(0XFFfdad02), fontSize = 10.sp,modifier = Modifier.padding( start = 10.dp))

                                }


                                Text(text = "title", fontSize = 18.sp, modifier = Modifier.padding(start = 10.dp, top = 10.dp), maxLines = 2)


                                Row(modifier = Modifier
                                    .padding(top = 15.dp, start = 10.dp)
                                    .fillMaxWidth()) {

                                    Image(
                                        painter = painterResource(id = R.drawable.ic_bed),
                                        modifier = Modifier
                                            .size(15.dp)
                                            .padding(top = 2.dp),
                                        contentDescription = "Bed Icon")

                                    Text(text = "2 GUESTS",
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .padding(start = 10.dp)

                                    )
                                }

                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, start = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    Column {
                                        Text(text = "12 - 15 MAY")
                                        //Text(text = "${currencySymbol}/NIGHT", fontSize = 10.sp)
                                        Text(text = "2023")

                                    }

                                    /**Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                                        modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                                        contentDescription = "Arrow")*/


                                }

                            } //colmn ends here


                        }

                    }




                }


            }


            Spacer(modifier = Modifier.height(10.dp))



            // } // item 1 ends here




          //  item {

               // Column {


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
                        items(happeningNowData) {   data ->

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

                            ) {



                                Image(

                                    painter = rememberAsyncImagePainter(

                                        model = "http://23.97.138.116:7001/${data.images.get(0).path}"
                                    ),
                                    //painter = painterResource(R.drawable.ic_happening2),

                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillParentMaxSize()
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(8.dp))


                                )


                            }


                        }
                    }
               // }

           // } // item 2 ends here


           // item {
               // Column(modifier = Modifier.padding(bottom = 10.dp)) {


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

                    OurStaysItem("Greece","https://www.oneperfectstay.com/storage/uploads/P6hhCXJnuxEVUUtW2ngAO63l8jSZd2qREczXW5ce.jpg","","","945","","USD",navigator,"")



                //}
           // } // item 3 ends here
        }
    }
}





