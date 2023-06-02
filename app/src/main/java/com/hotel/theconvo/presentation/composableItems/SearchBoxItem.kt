package com.hotel.theconvo.presentation.composableItems

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.BrowseScreenDestination
import com.hotel.theconvo.destinations.HotelsListScreenDestination
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SearchBoxItem(

    modifier: Modifier,
    textFieldShape: RoundedCornerShape,
    navigator: DestinationsNavigator?,
    onClick: () -> Unit



) {





        TextField(

            leadingIcon = {
                IconButton(
                    onClick = {

                        // navigator?.navigate(HotelsListScreenDestination())
                        navigator?.navigate(TabScreenDestination(false,false))
                    }) {
                    Icon(

                        painter = painterResource(R.drawable.ic_location),
                        contentDescription = "location icon",
                        tint = Color(0XFFfdad02)
                    )
                }
            },
            label = {
                Text(text = "Where to next?")
            },

            trailingIcon = {
                IconButton(onClick = { }) {
                    Icon(

                        painter = painterResource(R.drawable.ic_search),
                        contentDescription = "search icon",
                        tint = Color(0XFFfdad02)
                    )
                }
            },

            enabled = false,

                    modifier = modifier
                .clickable { onClick() }
            ,
            /**  modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 170.dp)
            .align(Alignment.BottomCenter)
            .shadow(elevation = 5.dp, shape = textFieldShape)
            .clip(textFieldShape),*/
            /**  modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 170.dp)
            .align(Alignment.BottomCenter)
            .shadow(elevation = 5.dp, shape = textFieldShape)
            .clip(textFieldShape),*/
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color(0xFFFFFFFF),
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = "",
            onValueChange = {}


        )


}