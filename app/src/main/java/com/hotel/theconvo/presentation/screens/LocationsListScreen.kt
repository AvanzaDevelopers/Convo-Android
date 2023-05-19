package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.ImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.MainActivity.Companion.propList
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.Coordinates
import com.hotel.theconvo.data.remote.dto.req.GetPropertyReq
import com.hotel.theconvo.data.remote.dto.req.PageData
import com.hotel.theconvo.data.remote.dto.req.SearchCriteria
import com.hotel.theconvo.data.remote.dto.response.SearchResult
import com.hotel.theconvo.presentation.composableItems.OurStaysItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Destination
@Composable
fun LocationsListScreen(
    navigator: DestinationsNavigator?,
    noOfRooms: Int
   // propList: List<SearchResult>
) {

    val singapore = LatLng(1.3554117053046808, 103.86454252780209)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 30f)
    }

   /** var propertyList by remember {
        mutableStateOf<List<SearchResult>>(emptyList())
    }*/


    Column {

        GoogleMap(
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


        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "5",
            color = Color(0XFFfdad02)

        )

        Text(text = "Locations Found")

       Spacer(modifier = Modifier.height(10.dp))

       /** LaunchedEffect( Unit){

            var getPropertyReq = GetPropertyReq(
                PageData(currentPageNo = 0, pageSize = "5"),
                SearchCriteria(Coordinates(latitude = "25.1819", longitude = "55.2772", radiusinKm = 5),"",true,"Mayfair Residency, Mayfair Residency")
            )

            val users = withContext(Dispatchers.IO) {
                //userListUseCase.execute()
                MainActivity.loginUseCase.getProperties(getPropertyReq).toString()

                propertyList = MainActivity.loginUseCase.getProperties(getPropertyReq).searchProperties.searchResult
            }






        }*/

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            content = {

                /**item {

                OurStaysItem(title = "Greece")
                }*/

                /**item {

                OurStaysItem(title = "Greece")
                }*/

                items(propList) { properties ->
                    //UserListItem(user)
                    //OurStaysItem(title = properties.property.name)

                    Box(

                       modifier = Modifier
                           .fillMaxWidth()
                           .padding(start = 10.dp, end = 10.dp)
                    ) {


                      Row(
                          modifier = Modifier.fillMaxWidth()
                      ) {

                         Image(

                             modifier = Modifier
                                 .weight(4f)
                                 .size(200.dp),
                            // painter = painterResource(id = R.drawable.ic_stays),

                            // painter = rememberAsyncImagePainter("https://www.oneperfectstay.com/storage/uploads/aAyZTUtruRbfvz5jdLQJHUwqwy8kNCt5JVxKSeza.jpg"),



                        painter =  rememberAsyncImagePainter(model = properties.property.property_images.get(0).image_path),


                             contentDescription = "Stay Image"

                         )

                          Column(
                              modifier = Modifier.weight(1f)
                          ) {

                              Text(

                                  modifier = Modifier.padding(top = 10.dp),
                                  text = properties.property.number_of_rooms_left,
                                  fontSize = 18.sp
                              )
                              Text(text = "ROOMS", fontSize = 10.sp)

                              Spacer(modifier = Modifier.height(100.dp))

                              Text(text = properties.property.amount.toString(), fontSize = 18.sp)

                              Text(text = "USD/NIGHT", fontSize = 10.sp)


                          }





                      }



                    }



                }



            })



    }




}