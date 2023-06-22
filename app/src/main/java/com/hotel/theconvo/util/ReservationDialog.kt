package com.hotel.theconvo.util

import android.app.Dialog
import android.graphics.fonts.FontFamily
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Money
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hotel.theconvo.R


@Composable
fun ReservationDialog(onDismiss: () -> Unit, onPositiveClick: () -> Unit) {



    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {
            Box(

                contentAlignment = Alignment.Center,



                ) {

                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {


                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Reservation Successful!",
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 3.dp)

                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically ,
                        modifier = Modifier
                            .fillMaxWidth()
                            // .padding(start = 5.dp, end = 5.dp)
                            .background(
                                color = Color(0xFF04113B).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {



                        Image(
                            modifier= Modifier.padding(start = 10.dp, top = 20.dp, bottom = 20.dp),
                            painter = painterResource(id = R.drawable.ic_reservation_email),

                            contentDescription = "email icon")

                         Spacer(modifier = Modifier.width(20.dp))


                        Text(
                            //text = "Your reservation details have been sent by email",
                            text = "Reservation details have been sent to your email",
                            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, end = 20.dp)
                        )


                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(onClick = {
                      onPositiveClick()
                      //  setShowDialog(false)




                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .shadow(shape = RoundedCornerShape(8.dp), elevation = 0.dp)
                        ) {

                        Text(text = "Done")


                    }

                    Spacer(modifier = Modifier.height(2.dp))
                    
                }


            }
        }
    }
}
