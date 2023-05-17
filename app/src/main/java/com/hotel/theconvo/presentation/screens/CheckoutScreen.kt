package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CheckoutScreen(navigator: DestinationsNavigator?) {


    //Text(text = "Checkout Screen")

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

          Image(
              painter = painterResource(id = R.drawable.ic_stays),
              contentDescription = "Stays Screen")




        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Your Stay Gets Better",
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(

            verticalArrangement = Arrangement.spacedBy(10.dp),
            content = {
            items(2){

               // Text(text = "Airport Pickup")
               ListComposableCard()

            }
        })

        Spacer(modifier = Modifier.height(30.dp))

       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(start = 20.dp, end = 20.dp)
       ) {

           Row(
               modifier = Modifier.weight(1f)

           ) {

               Text(text = "400 ", fontSize = 20.sp)
               Text(text = "USD", fontSize = 13.sp)

           }


           Button(
               modifier = Modifier
                   .background(MaterialTheme.colors.primary)
                   .weight(2f),
               onClick = {

           }) {

             Text(text = "CHECK OUT")


           }

       }






    }



}


@Composable
fun ListComposableCard() {

    Card(
      modifier = Modifier
          .fillMaxWidth()
          .padding(
              start = 20.dp,
              end = 20.dp,
              top = 10.dp,
              bottom = 10.dp
          )
          .shadow(elevation = 5.dp)
    ) {

       Row(
           modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically) {

           Spacer(modifier = Modifier.width(20.dp))

           Image(painter = painterResource(id = R.drawable.ic_taxi), contentDescription = "Image")

           Spacer(modifier = Modifier.width(20.dp))

           Column {

               Text(text = "Airport Pickup",
                   fontSize = 20.sp,
                   modifier = Modifier.padding(top = 10.dp)
                   )

               Row(modifier = Modifier.padding(bottom = 10.dp)) {
                   Text(
                       text = "20",
                       fontSize = 12.sp)
                   Spacer(modifier = Modifier.width(5.dp))
                   Text(
                       modifier = Modifier.padding(top = 3.dp),
                       text = "USD",
                       fontSize = 10.sp,)
               }
           }


           Spacer(modifier = Modifier.weight(1f))
          // Box(
         //  ) {
               Text(

                   modifier = Modifier.padding(end = 10.dp),
                   textAlign = TextAlign.Right,
                   text = "+",
                   fontSize = 22.sp,
                   color = Color(0XFFfdad02)
               )

          // }






       }





    }




}