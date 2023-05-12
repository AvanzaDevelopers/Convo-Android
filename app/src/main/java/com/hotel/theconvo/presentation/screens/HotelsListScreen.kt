package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.data.remote.dto.req.Coordinates
import com.hotel.theconvo.data.remote.dto.req.GetPropertyReq
import com.hotel.theconvo.data.remote.dto.req.PageData
import com.hotel.theconvo.data.remote.dto.req.SearchCriteria
import com.hotel.theconvo.presentation.composableItems.OurStaysItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Destination
@Composable
fun HotelsListScreen(
    navigator: DestinationsNavigator?
) {

    val singapore = LatLng(1.3554117053046808, 103.86454252780209)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 30f)
    }

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


        LaunchedEffect( Unit){

            var getPropertyReq = GetPropertyReq(PageData(currentPageNo = 0, pageSize = "1"),
                SearchCriteria(Coordinates(latitude = "25.1819", longitude = "55.2772", radiusinKm = 5),"",true,"Mayfair Residency, Mayfair Residency")
            )
            Log.i("Properties Are:",loginUseCase.getProperties(getPropertyReq).toString())

        }
        
        LazyColumn(content = {
            item {
                OurStaysItem("Greece")

            }
        })

    }


}