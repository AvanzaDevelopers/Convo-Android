package com.hotel.theconvo.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.hotel.theconvo.MainActivity.Companion.amenitiesList
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.response.GetPropertyResponse
import com.hotel.theconvo.data.remote.dto.response.Room
import com.hotel.theconvo.data.remote.dto.response.SearchResult
import com.hotel.theconvo.presentation.composableItems.OurStaysItem
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
    propertyId: String
) {

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

    Log.i("Property Id", propertyId)

    Column {


       /** GoogleMap(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            cameraPositionState = cameraPositionState
        ) {


            Marker(
                state = rememberMarkerState(position = singapore),
                title = "Marker1",
                snippet = "Marker in Singapore",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE),

                )


        }*/

        Image(
            painter = rememberAsyncImagePainter(model = hotelImageUrl),
            contentDescription = "Property Image" )


        Spacer(modifier = Modifier.height(10.dp))


        LaunchedEffect( Unit){

          /**  var getPropertyReq = GetPropertyReq(PageData(currentPageNo = 0, pageSize = "5"),
                SearchCriteria(Coordinates(latitude = "25.1819", longitude = "55.2772", radiusinKm = 5),"",true,"Mayfair Residency, Mayfair Residency")
            )*/

            var propDetailsReq = PropertyDetailsReq(
                searchCriteria = PropertyDetailsSearchCriteria(
                    end_date = "2023-06-17",
                    property_id = propertyId,
                    required_room = RequiredRoom(
                        adults = adults,
                        childrens = childrens,
                        rooms = "1"
                    ),
                    start_date = "2023-06-14"
                )
            )

            withContext(Dispatchers.IO) {
                try {
                    roomList = loginUseCase.getPropertyDetails(propDetailsReq).propertyDetails.rooms

                    amenitiesList = loginUseCase.getPropertyDetails(propDetailsReq).propertyDetails.amenities
                }
                catch (Ex: Exception) {
                    withContext(Dispatchers.Main) {
                       // Toast.makeText(currentCoroutineContext(),"No Rooms Found",Toast.LENGTH_LONG)
                        Log.i("No Rooms Found",Ex.message.toString())
                    }
                }
            }

          /**  val users = withContext(Dispatchers.IO) {
                //userListUseCase.execute()
                loginUseCase.getProperties(getPropertyReq).toString()

                propertyList = loginUseCase.getProperties(getPropertyReq).searchProperties.searchResult
            }*/





        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            content = {

            /**item {

                OurStaysItem(title = "Greece")
            }*/

            items(roomList) { rooms ->
                //UserListItem(user)
                OurStaysItem(title = rooms.roomId, imageUrl = rooms.image.toString(),hotelImageUrl,rooms.roomType,rooms.totalRoomRate,navigator)
            }



        })
        


    }


}