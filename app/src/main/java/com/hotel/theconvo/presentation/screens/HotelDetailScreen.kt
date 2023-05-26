package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hotel.theconvo.MainActivity.Companion.amenitiesList
import com.hotel.theconvo.MainActivity.Companion.propList
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.response.Room
import com.hotel.theconvo.destinations.CheckoutScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun HotelDetailScreen(

    navigator: DestinationsNavigator?,
    name: String,
    propertyImageUrl: String,
    roomImageUrl: String,
    roomType: String,
    amount: String,
    netAmount: String,
    currencySymbol: String


) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {



        Box(
            modifier = Modifier
                .fillMaxWidth()
                //.weight(2f)
                .height(300.dp)

        ) {

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                painter = rememberAsyncImagePainter(model = propertyImageUrl),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Slider Image"
            )

            Image(

                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_convo_logo),
                contentDescription = "Convo Logo")

            Image(

                modifier= Modifier
                    .fillMaxWidth()
                    .padding(start = 50.dp, end = 50.dp)
                    .align(Alignment.BottomCenter)
                    .padding(top = 100.dp),
                painter = rememberAsyncImagePainter(model = roomImageUrl),
                contentDescription = "Stay Image"

            )

        }
        
      
      Spacer(modifier = Modifier.height(10.dp))
      
      Row(
          modifier = Modifier
              .height(130.dp)
              .fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly
         // horizontalArrangement = Arrangement.spacedBy(10.dp)

      ) {
          
          Column(Modifier.weight(1f)) {



                  Text(

                      text = name,
                      maxLines = 2,
                      fontSize = 32.sp,
                      modifier = Modifier
                          .padding(start = 20.dp, top = 5.dp)
                          //.width(180.dp)
                          //.weight(1f)
                      //.padding(start = 20.dp).weight(1f).fillMaxWidth()
                  )

              Spacer(modifier = Modifier.height(5.dp))
              Row {
                  Image(
                      modifier = Modifier
                          .height(50.dp)
                          .width(50.dp)
                          .padding(start = 10.dp, end = 10.dp),
                      contentScale = ContentScale.Fit ,
                      painter = painterResource(id = R.drawable.ic_bed),
                      contentDescription =  "Bed Icon")

                  Spacer(modifier = Modifier.width(2.dp))
                 Text(
                     text = roomType,
                     fontSize = 17.sp,
                     modifier = Modifier.padding(top = 8.dp)
                 )
              }

          }


          Box(
                     modifier = Modifier
                         .width(300.dp)
                         .weight(1f)
             // .weight(1f).fillMaxWidth()

          )

          {

         Image(

             contentScale = ContentScale.FillBounds,
             modifier= Modifier
                 .padding(end = 10.dp)
                 .fillMaxSize(),

             painter = painterResource(id = R.drawable.room_background),
             contentDescription = "Image")

          }
          
      }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "Amenities", fontSize = 20.sp, modifier = Modifier.padding(start = 10.dp))

        Spacer(modifier = Modifier.height(10.dp))
         LazyRow(
             modifier = Modifier.padding(start = 10.dp),
             horizontalArrangement = Arrangement.spacedBy(10.dp),content = {

             items(amenitiesList) {


                // Text(text = "Silent Rooms")
               AmentiesCard(it.name)




             }

         })
        
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {



                Text(
                    text = "${amount}\n${currencySymbol}/Night",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 5.dp))


            Spacer(modifier = Modifier.width(20.dp))


            Button(

                modifier = Modifier
                    .weight(2f)
                    .height(60.dp)
                    .background(color = MaterialTheme.colors.primary)

                ,onClick = {

                    navigator?.navigate(CheckoutScreenDestination(
                        amount,
                        propertyImageUrl.toString(),
                                roomImageUrl.toString(),
                        roomType,
                        netAmount,
                        currencySymbol
                    )

                    )


                }) {

                Text(text = "Book My Stay")

            }

        }
        
    }
}


@Composable
fun AmentiesCard(title : String) {

    Card(modifier = Modifier
        .shadow(elevation = 5.dp)
        .width(120.dp)
        .height(140.dp)) {

        Box(modifier = Modifier.fillMaxSize()) {
               Image(
                painter = painterResource(id = R.drawable.ic_silent_rooms),
                contentDescription = "Silent Room Icon",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 10.dp)
                   )

        Text(
            text = title,
            maxLines = 2 ,
            textAlign = TextAlign.Center,
            fontSize = 13.sp ,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp),


        )

        }


    }



}

/**@Preview(showBackground = true)
@Composable

fun HotelDetailPreview() {
    HotelDetailScreen(navigator = null)
}*/