package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.HotelsListScreenDestination
import com.hotel.theconvo.presentation.composableItems.SearchBoxItem
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*


@Destination
@Composable
fun BrowseScreen(
    navigator: DestinationsNavigator?
) {


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
    //Text(text = "Browse Screen")


    Column(modifier = Modifier.fillMaxSize()) {


        Row(modifier = Modifier.fillMaxWidth()) {

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.ic_convo_inverted_coma),
                contentDescription = "Inverted comma at top",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(end = 10.dp)
            )

        }

        Spacer(modifier = Modifier.height(10.dp))

        val textFieldShape = RoundedCornerShape(8.dp)




        SearchBoxItem(
            modifier = Modifier


                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)
                //.align(Alignment.BottomCenter)
                .shadow(elevation = 5.dp, shape = textFieldShape)
                .clip(textFieldShape),
            textFieldShape,
            navigator,
            onClick = {

                navigator?.navigate(HotelsListScreenDestination())
            }

        )


        Spacer(modifier = Modifier.height(20.dp))


        Row(

            horizontalArrangement = Arrangement.spacedBy(10.dp) ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp)

        ) {

            Card(
                modifier = Modifier
                    .weight(1f)
                    .shadow(5.dp)) {

                Row(

                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp) ,
                    verticalAlignment = Alignment.CenterVertically,


                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_adults),
                        contentDescription = "Adults Image",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        contentScale = ContentScale.Inside

                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp),
                        textAlign = TextAlign.Center,
                        text = "Adults")

                }


            }


            Card(

                modifier = Modifier
                    .weight(1f)
                    .shadow(5.dp)) {

                Row(
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp) ,
                    verticalAlignment = Alignment.CenterVertically) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_kids),
                        contentDescription = "Kids Image",
                        modifier = Modifier
                            .width(25.dp)
                            .height(25.dp),
                        contentScale = ContentScale.Inside
                        )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 10.dp),
                        text = "Kids",
                        textAlign = TextAlign.Center
                        )

                }



            }


        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp
                )
                .shadow(5.dp)
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


        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)
        ) {

          Column(modifier = Modifier.weight(1f)) {

              Text(text = "23")

              Text(text = "Stays Found")

          }

        Button(
            modifier = Modifier.background(MaterialTheme.colors.primary).weight(2f),
            onClick = {  }) {

            Text(text = "Browse")

        }

        }

    }


}

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

