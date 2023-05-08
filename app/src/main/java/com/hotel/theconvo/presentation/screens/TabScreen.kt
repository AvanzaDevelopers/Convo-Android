package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun TabScreen(

    navigator: DestinationsNavigator?

) {

    Column {

      Row(
          modifier = Modifier
              .fillMaxWidth()
              .padding(top = 20.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween

      ) {

          Image(
              painter = painterResource(id = R.drawable.ic_burger_menu),
              contentDescription = "Drawable Icon",
              modifier = Modifier
                  .weight(1f)
                  .padding(top = 2.dp)
              )

          Image(
              painter = painterResource(id = R.drawable.ic_convo_logo),
              contentDescription = "Convo Logo",
              modifier = Modifier
                  .size(25.dp)
                  .weight(3f)
          )

          Image(
              painter = painterResource(id = R.drawable.ic_profile_circle),
              contentDescription = "Drawable Icon",
              modifier = Modifier
                  .weight(1f)
                  .padding(2.dp)
                  .clickable {
                      navigator?.navigate(LoginScreenDestination())
                  }
          )




      }


        Spacer(modifier = Modifier.height(20.dp))

        MyTabLayout()

    }



}

@Composable
fun MyTabLayout() {
    val tabItems = listOf("STAY", "OWN","CONVOS")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(
            modifier = Modifier.padding(start = 20.dp,end = 20.dp),
            selectedTabIndex = selectedTabIndex,
            backgroundColor = Color.White,
            divider = {},
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier

                        .tabIndicatorOffset(it[selectedTabIndex]),
                    color = Color(0XFFfdad02),
                  //  height = TabRowDefaults.IndicatorHeight * 1.5F
                     height = 1.dp
                )
            }
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }



        // content for the currently selected tab
        when (selectedTabIndex) {
            0 -> {
                //Text("Content for Tab 1")
                StayScreen()
            }
            1 -> {
                //Text("Content for Tab 2")
                OwnScreen()
            }
            2-> {
                //Text("Content for Tab 3")
                ConvosScreen()
            }
        }
    }
}
