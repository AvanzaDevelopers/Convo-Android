package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import com.hotel.theconvo.R
import com.hotel.theconvo.util.SharedPrefsHelper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun RoomScreen(navigator: DestinationsNavigator?) {

   //Text(text = "Room Screen")

   val context = LocalContext.current
   SharedPrefsHelper.initialize(context)

   val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }



   /** This is just written for testing */
   sharedPreferences.edit {
      putBoolean("isRoomAvailable", true) // Store a string value in SharedPreferences

   }

   Column {


      Spacer(modifier = Modifier.height(10.dp))

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
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(modifier = Modifier
         .fillMaxWidth()
         .height(70.dp)
         .padding(start = 10.dp, end = 10.dp)
      ) {

         Card(
            modifier = Modifier
               .weight(1f)
               .fillMaxHeight().shadow(elevation = 5.dp)
         ) {
            Box(

            ) {
               Text(text = "Coffe Menu", modifier = Modifier.align(Alignment.Center))
            }
         }

         Spacer(modifier = Modifier.width(10.dp))

         Card(
            modifier = Modifier
               .weight(1f)
               .fillMaxHeight().shadow(elevation = 5.dp)
         ) {

            Box(

            ) {
               Text(text = "Coffe Menu", modifier = Modifier.align(Alignment.Center))
            }

         }


      }



      Spacer(modifier = Modifier.height(10.dp))


      Row(modifier = Modifier
         .fillMaxWidth()
         .height(70.dp)
         .padding(start = 10.dp, end = 10.dp)
      ) {

         Card(
            modifier = Modifier
               .weight(1f)
               .fillMaxHeight().shadow(elevation = 5.dp)
         ) {
            Box(

            ) {
               Text(text = "Coffe Menu", modifier = Modifier.align(Alignment.Center))
            }
         }

         Spacer(modifier = Modifier.width(10.dp))

         Card(
            modifier = Modifier
               .weight(1f)
               .fillMaxHeight().shadow(elevation = 5.dp)
         ) {

            Box(

            ) {
               Text(text = "Coffe Menu", modifier = Modifier.align(Alignment.Center))
            }


         }


      }






   }

   }