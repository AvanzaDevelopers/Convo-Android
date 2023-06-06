package com.hotel.theconvo.presentation.screens

import android.util.Log
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.hotel.theconvo.MainActivity.Companion.propExtras

import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.ReservationScreenDestination
import com.hotel.theconvo.util.ContinuousSelectionHelper
import com.hotel.theconvo.util.DateSelection
import com.hotel.theconvo.util.SharedPrefsHelper
import com.hotel.theconvo.util.backgroundHighlight
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@Destination
@Composable
fun CheckoutScreen(
    navigator: DestinationsNavigator?,
    amount: String,
    imageUrl: String,
    roomImageUrl: String,
    roomName: String,
    netAmount: String,
    currencySymbol: String,
    totalTaxes: String,
    propertyID: String,
    roomID: String,
    grandTotal: String
    ) {


    //Text(text = "Checkout Screen")




    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val daysOfWeek = remember { daysOfWeek() }

    val coroutineScope = rememberCoroutineScope()


    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )

    /** These are calendar var */
    val today = remember { LocalDate.now() }
    var selection by remember { mutableStateOf(DateSelection()) }


    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)

    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }

    var start_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("start_date", "") ?: "") }
    var end_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("end_date", "") ?: "") }

    var totalAmount = remember { mutableStateOf(netAmount.toDouble()) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Log.i("start_date_checkout",start_date)
        Log.i("end_date_checkout",end_date)

        selection = DateSelection(startDate= LocalDate.parse(start_date) , endDate = LocalDate.parse(end_date))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

          Image(
              painter = rememberAsyncImagePainter(model = imageUrl.replace("\r\n","")),
              contentDescription = "Stays Screen",
              modifier = Modifier
                  .fillMaxWidth()
                  .height(250.dp),
              contentScale = ContentScale.FillBounds
              )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 130.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
                    .shadow(5.dp)
            ) {


                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(325.dp)) {

                    Row(
                        modifier = Modifier.height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CalendarNavigationIcon(
                            icon = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "Previous",
                            onClick = {

                                coroutineScope.launch {
                                    val targetMonth =
                                        state.firstVisibleMonth.yearMonth.previousMonth
                                    state.animateScrollToMonth(targetMonth)
                                }

                            },
                        )
                        Text(


                            modifier = Modifier
                                .weight(1f)
                                .testTag("MonthTitle"),
                            text = "${state.firstVisibleMonth.yearMonth.month.name} ${state.firstVisibleMonth.yearMonth.year.toString()}",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium,
                        )
                        CalendarNavigationIcon(
                            icon = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = "Next",
                            onClick = {
                                coroutineScope.launch {
                                    val targetMonth = state.firstVisibleMonth.yearMonth.nextMonth
                                    state.animateScrollToMonth(targetMonth)
                                }
                            },
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    HorizontalCalendar(
                        modifier = Modifier
                            .testTag("Calendar")
                            .fillMaxWidth()
                            .height(275.dp)
                            .background(Color(0xFFffffff)),
                        state = state,
                        dayContent = { value ->
                            Day(
                                value,
                                today = today,
                                selection = selection,
                            ) { day ->
                                if (day.position == DayPosition.MonthDate &&
                                    (day.date == today || day.date.isAfter(today))
                                ) {
                                    selection = ContinuousSelectionHelper.getSelection(
                                        clickedDate = day.date,
                                        dateSelection = selection,
                                    )
                                }
                            }
                        },
                       /** dayContent = { day ->
                            Day(day, isSelected = selections.contains(day)) { clicked ->
                                if (selections.contains(clicked)) {
                                    selections.remove(clicked)
                                } else {
                                    selections.add(clicked)
                                }
                            }
                        },*/
                        monthHeader = {
                            MonthHeader(daysOfWeek = daysOfWeek)
                        },
                    )
                }
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
            items(propExtras){


               // Text(text = "Airport Pickup")
              // ListComposableCard(it.name,it.price.toString(),it.priceType,amount)

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

                            Text(text = it.name,
                                fontSize = 20.sp,
                                modifier = Modifier.padding(top = 10.dp)
                            )

                            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                                Text(
                                    text = it.price.toString(),
                                    fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    modifier = Modifier.padding(top = 3.dp),
                                    text = currencySymbol,
                                    fontSize = 10.sp,)
                            }
                        }


                        /** This spacer will push + to the right side of the view */
                        Spacer(modifier = Modifier.weight(1f))
                        // Box(
                        //  ) {
                        Text(

                            modifier = Modifier
                                .padding(end = 10.dp)
                                .clickable {

                                    totalAmount.value += it.price


                                }
                            ,
                            textAlign = TextAlign.Right,
                            text = "+",
                            fontSize = 22.sp,
                            color = Color(0XFFfdad02)
                        )

                        // }






                    }





                }




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

               Text(
                   text = totalAmount.value.toString(),
                   fontSize = 20.sp)
               Text(
                   text = currencySymbol,
                   fontSize = 13.sp,
                   modifier = Modifier.padding(start = 5.dp, top = 7.dp)

               )

           }


           Card(
               modifier= Modifier.weight(2f),
               shape = RoundedCornerShape(4.dp)) {


               Button(
                   modifier = Modifier
                       .background(MaterialTheme.colors.primary)
                       .fillMaxWidth(),
                   //.weight(2f),
                   onClick = {

                       navigator?.navigate(
                           ReservationScreenDestination(
                               imageUrl,
                               roomImageUrl,
                               totalAmount.value.toString(),
                               roomName,
                               currencySymbol,
                               totalTaxes,
                               propertyID,
                               roomID,
                               grandTotal
                           )
                       )
                   }) {

                   Text(text = "CHECKOUT")


               }// Button ends here
           } // card ends here
       } // Row Ends here






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
            DayPosition.InDate, DayPosition.OutDate -> colorResource(androidx.appcompat.R.color.material_grey_300)
        }
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    today: LocalDate,
    selection: DateSelection,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor = Color.Transparent
    //val selectionColor = primaryColor
    //val continuousSelectionColor = MaterialTheme..yellowColor.copy(alpha = 0.3f)

    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                enabled = day.position == DayPosition.MonthDate && day.date >= today,
                //showRipple = false,
                onClick = { onClick(day) },
            )
            .backgroundHighlight(
                day = day,
                today = today,
                selection = selection,
                selectionColor = Color(0XFFfdad02),
                continuousSelectionColor = Color(0XFFfdad02).copy(alpha = 0.3f),
            ) { textColor = it },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}


@Composable
private fun CalendarNavigationIcon(
    icon: Painter,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .align(Alignment.Center),
        painter = icon,
        contentDescription = contentDescription,
    )
}