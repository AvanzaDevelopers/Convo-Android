package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.hotel.theconvo.R
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@Destination
@Composable
fun CheckoutScreen(navigator: DestinationsNavigator?) {


    //Text(text = "Checkout Screen")




    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val daysOfWeek = remember { daysOfWeek() }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )




    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

          Image(
              painter = painterResource(id = R.drawable.ic_stays),
              contentDescription = "Stays Screen")

            Card(
                modifier = Modifier.fillMaxWidth().padding(top = 200.dp,
                start = 20.dp,
                end = 20.dp).shadow(5.dp)
            ) {


                HorizontalCalendar(
                    modifier = Modifier
                        .testTag("Calendar")
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(Color(0xFFffffff)),
                    state = state,
                    dayContent = { day ->
                        Day(day, isSelected = selections.contains(day)) { clicked ->
                            if (selections.contains(clicked)) {
                                selections.remove(clicked)
                            } else {
                                selections.add(clicked)
                            }
                        }
                    },
                    monthHeader = {
                        MonthHeader(daysOfWeek = daysOfWeek)
                    },
                )
            }


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


               /** This spacer will push + to the right side of the view */
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


private val dateFormatter = DateTimeFormatter.ofPattern("dd")



@Composable
private fun MonthHeader(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("MonthHeader"),
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 15.sp,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH),
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .testTag("MonthDay")
            .padding(6.dp)
            .clip(CircleShape)
            .background(color = if (isSelected) colorResource(R.color.pmColor) else Color.Transparent)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                // showRipple = !isSelected,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        val textColor = when (day.position) {
            // Color.Unspecified will use the default text color from the current theme
            DayPosition.MonthDate -> if (isSelected) Color.White else Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> colorResource(R.color.pmColor)
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}