package com.hotel.theconvo.presentation.composableItems

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R

@Composable
fun OurStaysItem(
    title: String
) {

    Card(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .shadow(elevation = 5.dp)
    ) {

        Row {
            Image(

                painter = painterResource(id = R.drawable.ic_stays),
                contentDescription = "",
                modifier = Modifier.weight(3f),
                contentScale = ContentScale.FillBounds ,
                alignment = Alignment.TopStart

            )

            Column(
                modifier = Modifier.weight(2f)
            ) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        modifier = Modifier
                            .size(15.dp)
                            .padding(top = 1.dp),
                        contentDescription = "Location Icon")

                    Text(text = title, color = Color(0XFFfdad02), fontSize = 10.sp,modifier = Modifier.padding( start = 10.dp))

                }


                Text(text = "Executive \nSuite", fontSize = 18.sp, modifier = Modifier.padding(start = 10.dp, top = 10.dp))


                Row(modifier = Modifier
                    .padding(top = 15.dp, start = 10.dp)
                    .fillMaxWidth()) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_bed),
                        modifier = Modifier
                            .size(15.dp)
                            .padding(top = 2.dp),
                        contentDescription = "Bed Icon")

                    Text(text = "2 GUESTS",
                        fontSize = 12.sp,
                        modifier = Modifier
                            .padding(start = 10.dp)

                    )
                }

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        Text(text = "100 ")
                        Text(text = "USD/NIGHT", fontSize = 10.sp)

                    }

                    Image(painter = painterResource(id = R.drawable.ic_forward_arrow),
                        modifier = Modifier.padding(end = 5.dp, top = 20.dp),
                        contentDescription = "Arrow")


                }

            }


        }

    }



}