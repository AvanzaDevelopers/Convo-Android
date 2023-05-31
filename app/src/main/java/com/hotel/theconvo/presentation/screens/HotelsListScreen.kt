package com.hotel.theconvo.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.hotel.theconvo.MainActivity.Companion.amenitiesList
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.MainActivity.Companion.propExtras
import com.hotel.theconvo.MainActivity.Companion.propList
import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.response.GetPropertyResponse
import com.hotel.theconvo.data.remote.dto.response.PropertyImage
import com.hotel.theconvo.data.remote.dto.response.Room
import com.hotel.theconvo.data.remote.dto.response.SearchResult
import com.hotel.theconvo.presentation.composableItems.OurStaysItem
import com.hotel.theconvo.util.SharedPrefsHelper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.*

@Destination
@Composable
fun HotelsListScreen(
    navigator: DestinationsNavigator?,
    hotelImageUrl: String,
    adults: String,
    childrens: String,
    propertyId: String,
    hotelRate: Double,
    currencySymbol: String,
    index: Int
) {

    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }

    var start_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("start_date", "") ?: "") }
    var end_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("end_date", "") ?: "") }


    val singapore = LatLng(1.3554117053046808, 103.86454252780209)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 30f)
    }

    var propertyList by remember {
        mutableStateOf<List<SearchResult>>(emptyList())
    }

    var roomList by remember {
        mutableStateOf<List<Room>>(emptyList())
    }

   var roomDescription by remember {
       mutableStateOf("")
   }

    Log.i("Property Id", propertyId)

    Column {




       /** Image(
            painter = rememberAsyncImagePainter(model = hotelImageUrl),
            contentDescription = "Property Image" )*/

        HorizontalPagerWithIndicators(images = propList.get(index).property_images,index)



        Spacer(modifier = Modifier.height(10.dp))


        LaunchedEffect( Unit){



            var propDetailsReq = PropertyDetailsReq(
                searchCriteria = PropertyDetailsSearchCriteria(
                    end_date = end_date,
                    property_id = propertyId,
                    required_room = RequiredRoom(
                        adults = adults,
                        childrens = childrens,
                        rooms = "1"
                    ),
                    start_date = start_date
                )
            )

            withContext(Dispatchers.IO) {
                try {

                    var response = loginUseCase.getPropertyDetails(propDetailsReq).propertyDetails



                    roomList = response.rooms


                   roomDescription = response.description
                    amenitiesList = response.amenities

                    propExtras = response.extras

                }
                catch (Ex: Exception) {
                    withContext(Dispatchers.Main) {
                       // Toast.makeText(currentCoroutineContext(),"No Rooms Found",Toast.LENGTH_LONG)
                        Log.i("No Rooms Found",Ex.message.toString())
                    }
                }
            }







        }

        LazyColumn(

            verticalArrangement = Arrangement.spacedBy(15.dp),
            content = {



            items(roomList) { rooms ->
                //UserListItem(user)

                OurStaysItem(title = rooms.roomType, imageUrl = rooms.image.toString(),hotelImageUrl,rooms.roomType,hotelRate.toString(),netAmount = rooms.netAmount,currencySymbol,navigator,roomDescription)
            }



        })
        


    }






}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithIndicators(images: List<String>, index: Int) {
    val pagerState = rememberPagerState()
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
    ) {
        HorizontalPager(pageCount = propList.get(index).property_images.size, state = pagerState) {
            Image(
                // painter = painterResource(id = images[it]),
                modifier = Modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(

                    //model = images[it].image_path.replace("\r\n","")
                     model = images[it].replace("\r\n","")
                ),
                contentScale = ContentScale.FillBounds,
                contentDescription = "" )
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp),
            pageCount =  propList.get(index).property_images.size,
            pagerState = pagerState,
        )
    }
}