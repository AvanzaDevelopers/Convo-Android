package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R

@Composable
fun StayScreen() {

    Spacer(modifier = Modifier.height(20.dp))

    Column(
        
        modifier = Modifier.fillMaxWidth()
    
    ) {
        
        Text(
            text = "Happening Around You", 
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .padding(start = 10.dp)
        ) {
            items(10) {

                Spacer(modifier = Modifier.width(10.dp))



                Card(modifier = Modifier
                    .height(200.dp)
                    .width(140.dp)
                    .shadow(elevation = 5.dp)) {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_happening),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )
                    }


                }

            }
        }
        
        Spacer(modifier = Modifier.height(20.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)) {
            
            Text(
                text = "Our Stays",
                fontSize = 20.sp,
                modifier = Modifier.weight(3f),
                textAlign = TextAlign.Left
                )

            Text(
                text = "View All",
                fontSize = 17.sp,
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        Log.i("Item Clicked:", "Item Clicked")
                    }
                ,
                textAlign = TextAlign.Right,
                color = Color(0XFFfdad02)
            )
            
            
        }
        
        Spacer(modifier = Modifier.height(15.dp))
        
        Card(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .shadow(elevation = 5.dp)) {
            
            Image(

                painter = painterResource(id = R.drawable.ic_stays),
                contentDescription = "",
                modifier = Modifier.width(100.dp),
                alignment = Alignment.TopStart

            )

            Column(
                modifier = Modifier.width(100.dp)
            ) {

                Text(text = "Executive Room")

            }


            
        }
        


        
    }


}