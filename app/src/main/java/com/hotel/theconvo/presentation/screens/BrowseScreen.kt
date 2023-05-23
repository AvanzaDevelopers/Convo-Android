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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.MainActivity.Companion.loginUseCase
import com.hotel.theconvo.MainActivity.Companion.propList
import com.hotel.theconvo.R
import com.hotel.theconvo.data.remote.dto.req.*
import com.hotel.theconvo.data.remote.dto.req.AutoCompletePageData
import com.hotel.theconvo.data.remote.dto.req.PageData
import com.hotel.theconvo.data.remote.dto.response.*
import com.hotel.theconvo.destinations.HotelsListScreenDestination
import com.hotel.theconvo.destinations.LocationsListScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.hotel.theconvo.presentation.composableItems.SearchBoxItem
import com.hotel.theconvo.util.ContinuousSelectionHelper.getSelection
import com.hotel.theconvo.util.DateSelection
import com.hotel.theconvo.util.LoadingDialog
import com.hotel.theconvo.util.UiState
import com.hotel.theconvo.util.backgroundHighlight
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*






@Destination
@Composable
fun BrowseScreen(
    navigator: DestinationsNavigator?
) {



    val focusRequester = remember { FocusRequester() }
    var suggestionsVisible = remember { mutableStateOf(true) }
    val textState = remember { mutableStateOf("") }
    val suggestions = listOf("Apple", "Banana", "Cherry", "Durian")

    var showDialog = remember{ mutableStateOf(false) }
    var uiState by remember { mutableStateOf<UiState<List<SearchResult>>>(UiState.Loading) }


    val focusManager = LocalFocusManager.current
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    val selections = remember { mutableStateListOf<CalendarDay>() }
    val daysOfWeek = remember { daysOfWeek() }

   // var locationExpanded: Boolean = false
    var searchText by remember { mutableStateOf("") }
    val suggestionsState = remember { mutableStateOf(emptyList<AutoCompleteSearchResult>()) }

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = daysOfWeek.first(),
    )
    var countryList by remember {
        mutableStateOf<List<CountriesAndFlag>>(emptyList())
    }

    val coroutineScope = rememberCoroutineScope()


    var propertyList by remember {
        mutableStateOf<List<SearchResult>>(emptyList())
    }

    var latitude by remember {
        mutableStateOf("")
    }

    var longitude by remember {
        mutableStateOf("")
    }

    var query by remember {
        mutableStateOf("")
    }

    var adults by remember {
        mutableStateOf("0")
    }

    var childrens by remember {
        mutableStateOf("0")
    }

    //Text(text = "Browse Screen")

     val primaryColor = Color.Black.copy(alpha = 0.9f)

    /** These are calendar var */
    val today = remember { LocalDate.now() }
    var selection by remember { mutableStateOf(DateSelection()) }



    fun filterSuggestions(input: String): List<AutoCompleteSearchResult> {
        return suggestionsState.value.filter {

            it.address.contains(input, ignoreCase = true) || it.location.contains(input, ignoreCase = true)
            //it.contains(input,
              //  ignoreCase = true)
        }
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

                                   // uiState = UiState.Loading
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

                                     //   suggestionsState.value = listOf( loginUseCase.getAutoCompleteLocations(autoCompReq).suggestedLocations.searchResult.get(0).address)


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
                            .focusRequester(focusRequester)
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

                                       // suggestionsState.value = emptyList<String>()
                                        //for( i in  loginUseCase.getAutoCompleteLocations(autoCompReq).suggestedLocations.searchResult.indices) {
                                            suggestionsState.value = loginUseCase.getAutoCompleteLocations(autoCompReq).suggestedLocations.searchResult
                                           /** suggestionsState.value +=
                                                loginUseCase.getAutoCompleteLocations(autoCompReq).suggestedLocations.searchResult.get(
                                                    i
                                                ).location*/

                                      //  }
                                }catch(ex: Exception) {
                                    
                                }
                                }
                            }


                            },




                    )

                    if (suggestionsVisible.value) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            items(filterSuggestions(searchText)) { suggestion ->



                               Row(modifier = Modifier.fillMaxWidth()) {


                                   if (suggestion.label.equals("airport")) {
                                       Image(
                                           modifier = Modifier.size(30.dp)
                                               .padding(top = 15.dp, start = 15.dp),
                                           painter = painterResource(id = R.drawable.ic_airplane),
                                           contentDescription = "location icon"
                                       )
                                   }
                                   else if(suggestion.label.equals("location")) {
                                       Image(
                                           modifier = Modifier.size(30.dp)
                                               .padding(top = 15.dp, start = 15.dp),
                                           painter = painterResource(id = R.drawable.ic_location),
                                           contentDescription = "location icon"
                                       )
                                   }

                                   else if(suggestion.label.equals("hotel")) {
                                       Image(
                                           modifier = Modifier.size(30.dp)
                                               .padding(top = 15.dp, start = 15.dp),
                                           painter = painterResource(id = R.drawable.ic_bed),
                                           contentDescription = "location icon"
                                       )
                                   }

                                   Column {


                                       Text(
                                           text = suggestion.location,
                                           modifier = Modifier
                                               .clickable {

                                                   query = suggestion.address
                                                   latitude = suggestion.latitude.toString()
                                                   longitude = suggestion.longitude.toString()
                                                   focusManager.clearFocus()
                                                   suggestionsVisible.value = false
                                               }
                                               .padding(start = 20.dp, top = 10.dp)

                                       )

                                       Text(
                                           text = suggestion.address,
                                           modifier =
                                           Modifier.padding(start = 20.dp, top = 5.dp),
                                           fontSize = 10.sp
                                       )

                                   }
                               }
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
                                adults = "1"
                                expanded = !expanded
                                /* Handle menu item click */
                            }) {

                                Text(text = "1")
                            }
                            DropdownMenuItem(onClick = {
                                adults = "2"
                                expanded = !expanded
                            }) {
                                Text(text = "2")
                            }
                            DropdownMenuItem(onClick = {
                                adults = "3"
                                expanded = !expanded

                            }) {
                                Text(text = "3")
                            }

                            DropdownMenuItem(onClick = {
                                adults = "4"
                                expanded = !expanded

                            }) {
                                Text(text = "4")
                            }
                            DropdownMenuItem(onClick = {
                                adults = "5"
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
                                childrens = "1"
                                expanded = !expanded
                                /* Handle menu item click */
                            }) {

                                Text(text = "1")
                            }
                            DropdownMenuItem(onClick = {
                                childrens = "2"
                                expanded = !expanded
                            }) {
                                Text(text = "2")
                            }
                            DropdownMenuItem(onClick = {
                                childrens = "3"
                                expanded = !expanded

                            }) {
                                Text(text = "3")
                            }

                            DropdownMenuItem(onClick = {
                                childrens = "4"
                                expanded = !expanded

                            }) {
                                Text(text = "4")
                            }
                            DropdownMenuItem(onClick = {
                                childrens = "5"
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


                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)) {


                    Row(
                        modifier = Modifier.height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CalendarNavigationIcon(
                            icon = painterResource(id = R.drawable.ic_chevron_left),
                            contentDescription = "Previous",
                            onClick = {

                                coroutineScope.launch {
                                    val targetMonth = state.firstVisibleMonth.yearMonth.previousMonth
                                    state.animateScrollToMonth(targetMonth)
                                }

                                      },
                        )
                        Text(



                        modifier = Modifier
                            .weight(1f)
                            .testTag("MonthTitle"),
                            text = "${state.firstVisibleMonth.yearMonth.month.name} ${state.firstVisibleMonth.yearMonth.year.toString()}"  ,
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
                            .height(250.dp)
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
                                    selection = getSelection(
                                        clickedDate = day.date,
                                        dateSelection = selection,
                                    )
                                }
                            }
                        },
                        /**dayContent = { day ->
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


            Spacer(modifier = Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
            ) {

                Column(modifier = Modifier.weight(1f)
                    .clickable {
                        Log.i("start date", selection.startDate.toString())
                        Log.i("end date", selection.endDate.toString())

                    }
                ) {

                    Text(text = "0")

                    Text(text = "Locations Found")

                }

                Button(
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .weight(2f),
                    onClick = {


                        uiState = UiState.Loading
                        showDialog.value = true


                        var getPropertyReq = GetPropertyReq(
                            PageData(currentPageNo = 0, pageSize = "5"),
                            SearchCriteria(Coordinates(latitude = latitude, longitude = longitude, radiusinKm = 5),"",true,query)
                        )

                        GlobalScope.launch {
                            val users = withContext(Dispatchers.IO) {
                                //userListUseCase.execute()
                              Log.i("Get Prop Req",  loginUseCase.getProperties(getPropertyReq).toString())

                                propertyList = loginUseCase.getProperties(getPropertyReq).searchProperties.searchResult

                                propList = loginUseCase.getProperties(getPropertyReq).searchProperties.searchResult
                                uiState = UiState.Success(loginUseCase.getProperties(getPropertyReq).searchProperties.searchResult)


                            }
                        }


                        //navigator?.navigate(LocationsListScreenDestination())
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
            val data = (uiState as UiState.Success<List<SearchResult>>).data
            // ...
            showDialog.value = false

            navigator?.navigate(LocationsListScreenDestination(noOfRooms = 2, adults = adults, childrens = childrens))


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


    if (showDialog.value) {
        LoadingDialog(isShowingDialog = showDialog.value)

        uiState = UiState.Loading


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