package com.hotel.theconvo.presentation.screens

import android.location.LocationRequest
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.req.AutoCompletePageData
import com.hotel.theconvo.data.remote.dto.response.*
import com.hotel.theconvo.destinations.HotelsListScreenDestination
import com.hotel.theconvo.destinations.LocationsListScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.hotel.theconvo.presentation.composableItems.SearchBoxItem
import com.hotel.theconvo.util.UiState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*






@Destination
@Composable
fun BrowseScreen(
    navigator: DestinationsNavigator?
) {


    var suggestionsVisible = remember { mutableStateOf(true) }
    val textState = remember { mutableStateOf("") }
    val suggestions = listOf("Apple", "Banana", "Cherry", "Durian")

    var showDialog = remember{ mutableStateOf(false) }
    var uiState by remember { mutableStateOf<UiState<AutoCompleteResponse>>(UiState.Loading) }


    val focusManager = LocalFocusManager.current
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val daysOfWeek = remember { daysOfWeek() }

   // var locationExpanded: Boolean = false
    var searchText by remember { mutableStateOf("") }
    val suggestionsState = remember { mutableStateOf(emptyList<String>()) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    var countryList by remember {
        mutableStateOf<List<CountriesAndFlag>>(emptyList())
    }





    //Text(text = "Browse Screen")



    fun filterSuggestions(input: String): List<String> {
       // return suggestions.filter { it.contains(input, ignoreCase = true) }
        return suggestionsState.value.filter { it.contains(input, ignoreCase = true) }
    }



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


            Box(
                modifier = Modifier.fillMaxWidth()
            ) {


                Column( modifier =  Modifier.fillMaxWidth()) {


                    TextField(


                        leadingIcon = {
                            IconButton(
                                onClick = {

                                    // navigator?.navigate(HotelsListScreenDestination())
                                    //navigator?.navigate(TabScreenDestination(false))
                                }) {
                                Icon(

                                    painter = painterResource(R.drawable.ic_location),
                                    contentDescription = "location icon",
                                    tint = Color(0XFFfdad02)
                                )
                            }
                        },
                        label = {
                            Text(text = "Where to next?")
                        },

                        trailingIcon = {
                            IconButton(onClick = {

                                showDialog.value = true

                                focusManager.clearFocus(true)

                                GlobalScope.launch {

                                    uiState = UiState.Loading
                                    val typeData = listOf("countries_and_flags")
                                    withContext(Dispatchers.IO) {


                                        var autoCompReq = AutoCompleteReq(
                                            pageData = AutoCompletePageData(
                                                currentPageNo = "1",
                                                pageSize = "5"
                                            ),
                                            searchCriteria = AutoCompleteSearchCriteria(
                                                query = searchText,
                                                coordinates = AutoCompleteCoordinates(
                                                    latitude = "30.375321",
                                                    longitude = "69.34511599999999",
                                                    radiusinKm = 5
                                                )

                                            ),
                                            sortby = "Price low to high"


                                        )


                                        // countryList = loginUseCase.getLocations(locationReq).typeData.data.countries_and_flags
                                        var placesList =
                                            loginUseCase.getAutoCompleteLocations(autoCompReq)
                                        Log.i("Locations Are:", placesList.toString())

                                        suggestionsState.value = listOf( loginUseCase.getAutoCompleteLocations(autoCompReq).suggestedLocations.searchResult.get(0).address)
                                        uiState = UiState.Success(
                                            loginUseCase.getAutoCompleteLocations(autoCompReq)
                                        )

                                    }
                                }


                                // locationExpanded = !locationExpanded

                            }) {
                                Icon(

                                    painter = painterResource(R.drawable.ic_search),
                                    contentDescription = "search icon",
                                    tint = Color(0XFFfdad02)
                                )
                            }
                        },

                        //enabled = false,

                        modifier = Modifier
                            .clickable { }
                            .padding(start = 10.dp, end = 10.dp)
                            //.align(Alignment.BottomCenter)
                            .shadow(elevation = 5.dp, shape = textFieldShape)
                            .clip(textFieldShape)
                            .fillMaxWidth()
                            .onFocusChanged {
                                     suggestionsVisible.value = !suggestionsVisible.value
                            }
                        ,

                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFFFFFFF),
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        value = searchText,
                        onValueChange = {
                             searchText = it
                             suggestionsVisible.value = true
                            //textState.value = it
                           // fetchSuggestions(searchText)
                            GlobalScope.launch {
                                withContext(Dispatchers.IO) {
                                    var autoCompReq = AutoCompleteReq(
                                        pageData = AutoCompletePageData(
                                            currentPageNo = "1",
                                            pageSize = "5"
                                        ),
                                        searchCriteria = AutoCompleteSearchCriteria(
                                            query = it,
                                            coordinates = AutoCompleteCoordinates(
                                                latitude = "30.375321",
                                                longitude = "69.34511599999999",
                                                radiusinKm = 5
                                            )

                                        ),
                                        sortby = "Price low to high"


                                    )
                                    try {
                                    suggestionsState.value = listOf( loginUseCase.getAutoCompleteLocations(autoCompReq).suggestedLocations.searchResult.get(0).address)

                                }catch(ex: Exception) {
                                    
                                }
                                }
                            }


                            },




                    )

                    if (suggestionsVisible.value) {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth().height(50.dp)
                        ) {
                            items(filterSuggestions(searchText)) { suggestion ->
                                Text(
                                    text = suggestion,
                                    modifier = Modifier.clickable {
                                        suggestionsVisible.value = false
                                    }.padding(start = 20.dp, top = 10.dp)

                                )
                            }
                        }
                    }

                }


            }


            Spacer(modifier = Modifier.height(20.dp))


            Row(

                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            ) {


                Box(modifier = Modifier.weight(1f)) {

                    var expanded by remember { mutableStateOf(false) }


                    Card(
                        modifier = Modifier
                            // .weight(1f)
                            .clickable { expanded = !expanded }
                            .shadow(5.dp)
                    ) {

                        Row(

                            modifier = Modifier.padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 20.dp,
                                bottom = 20.dp
                            ),
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
                                text = "Adults"
                            )

                        }


                    }


                    if (expanded) {
                        DropdownMenu(
                            modifier = Modifier
                                .widthIn(max = 240.dp)
                                .padding(16.dp)
                                .align(Alignment.BottomStart),
                            onDismissRequest = { expanded = false },
                            expanded = expanded
                        ) {
                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                                /* Handle menu item click */
                            }) {

                                Text(text = "1")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                            }) {
                                Text(text = "2")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = !expanded

                            }) {
                                Text(text = "3")
                            }

                            DropdownMenuItem(onClick = {
                                expanded = !expanded

                            }) {
                                Text(text = "4")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                            }) {
                                Text(text = "5")
                            }
                        }
                    }

                }


                Box(modifier = Modifier.weight(1f)) {


                    var expanded by remember { mutableStateOf(false) }

                    Card(

                        modifier = Modifier
                            //.weight(1f)
                            .clickable { expanded = !expanded }
                            .shadow(5.dp)
                    ) {

                        Row(
                            modifier = Modifier.padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 20.dp,
                                bottom = 20.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

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

                    if (expanded) {
                        DropdownMenu(
                            modifier = Modifier
                                .widthIn(max = 240.dp)
                                .padding(16.dp)
                                .align(Alignment.BottomStart),
                            onDismissRequest = { expanded = false },
                            expanded = expanded
                        ) {
                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                                /* Handle menu item click */
                            }) {

                                Text(text = "1")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                            }) {
                                Text(text = "2")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = !expanded

                            }) {
                                Text(text = "3")
                            }

                            DropdownMenuItem(onClick = {
                                expanded = !expanded

                            }) {
                                Text(text = "4")
                            }
                            DropdownMenuItem(onClick = {
                                expanded = !expanded
                            }) {
                                Text(text = "5")
                            }
                        }
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {

                Column(modifier = Modifier.weight(1f)) {

                    Text(text = "23")

                    Text(text = "Stays Found")

                }

                Button(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .weight(2f),
                    onClick = {

                        navigator?.navigate(LocationsListScreenDestination())
                    }) {

                    Text(text = "Browse")

                }

            }

        }


    when (uiState) {



        is UiState.Loading -> {

            Log.i("In Loading State","In Loading State")
            // Display a loading dialog
            //CircularProgressIndicator()
            if (showDialog.value == true) {
                showDialog.value = true
            }
            else {
                showDialog.value = false
            }
        }
        is UiState.Success -> {
            // Display the data
            //val data = (uiState as UiState.Success<List<MyData>>).data
            // ...
            showDialog.value = false



          //  navigator?.navigate(TabScreenDestination(true))
        }
        is UiState.Error -> {
            // Display an error message
            val message = (uiState as UiState.Error).message

            Toast.makeText(LocalContext.current,message, Toast.LENGTH_LONG).show()
            showDialog.value = false

            uiState = UiState.Loading

            /**remove below line after fixation */
            //navigator?.navigate(TabScreenDestination(true))
            // ...
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

