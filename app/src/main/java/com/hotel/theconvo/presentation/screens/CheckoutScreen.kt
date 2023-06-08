package com.hotel.theconvo.presentation.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.hotel.theconvo.MainActivity.Companion.propExtras

import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.ReservationScreenDestination
import com.hotel.theconvo.util.*
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

    val textFieldShape = RoundedCornerShape(8.dp)

    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)

    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }

    var start_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("start_date", "") ?: "") }
    var end_date by rememberSaveable { mutableStateOf(sharedPreferences.getString("end_date", "") ?: "") }

    var token by rememberSaveable {mutableStateOf(sharedPreferences.getString(AllKeys.token,"") ?: "")}
    var totalAmount = remember { mutableStateOf(netAmount.toDouble()) }

    var fName = remember { mutableStateOf(TextFieldValue()) }
    var email = remember {mutableStateOf(TextFieldValue())}
    var countryCode = remember{ mutableStateOf(TextFieldValue()) }
    var phoneNumber = remember{ mutableStateOf(TextFieldValue()) }
    var location = remember{mutableStateOf(TextFieldValue())}

    var btnText by remember{ mutableStateOf("Next") }


    val countries = listOf(
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
        "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",
        "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
        "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",
        "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria",
        "Burkina Faso", "Burma (Myanmar)", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",
        "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",
        "Cocos (Keeling) Islands", "Colombia", "Comoros", "Cook Islands", "Costa Rica",
        "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",

        "Denmark", "Djibouti", "Dominica", "Dominican Republic",

        "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",

        "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia",

        "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",

        "Greenland", "Grenada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",

        "Haiti", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",

        "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica",

        "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",

        "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",

        "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia",

        "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",

        "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",

        "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",

        "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",

        "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",

        "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",

        "Portugal", "Puerto Rico", "Qatar", "Republic of the Congo", "Romania", "Russia", "Rwanda",

        "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin",

        "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino",

        "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",

        "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",

        "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland",

        "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau",

        "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",

        "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",

        "Wallis and Futuna", "West Bank", "Yemen", "Zambia", "Zimbabwe"
    )

    val countryCodes = listOf("+93", "+355", "+213", "+1 684", "+376", "+244", "+1 264", "+672", "+1 268", "+54", "+374",
        "+297", "+61", "+43", "+994", "+1 242", "+973", "+880", "+1 246", "+375", "+32", "+501",
        "+229", "+1 441", "+975", "+591", "+387", "+267", "+55", "+246", "+1 284", "+673", "+359",
        "+226", "+95", "+257", "+855", "+237", "+1", "+238", "+1 345", "+236", "+235", "+56", "+86",
        "+61", "+891", "+57", "+269", "+682", "+506", "+385", "+53", "+357", "+420", "+243", "+45",
        "+253", "+1 767", "+1 849", "+1 829", "+1 809", "+593", "+20", "+503", "+240", "+291", "+372",
        "+251", "+500", "+298", "+679", "+358", "+33", "+689", "+241", "+220", "+970", "+995", "+49",
        "+233", "+350", "+30", "+299", "+1 473", "+1 671", "+502", "+224", "+245", "+592", "+509",
        "+379", "+504", "+852", "+36", "+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972",
        "+39", "+225", "+1 876", "+81", "+44", "+962", "+7", "+254", "+686", "+381", "+965", "+996",
        "+856", "+371", "+961", "+266", "+231", "+218", "+423", "+370", "+352", "+853", "+389",
        "+261", "+265", "+60", "+960", "+223", "+356", "+692", "+222", "+230", "+262", "+52", "+691",
        "+373", "+377", "+976", "+382", "+1 664", "+212", "+258", "+264", "+674", "+977", "+31",
        "+599", "+687", "+64", "+505", "+227", "+234", "+683", "+672", "+850", "+1 670", "+47",
        "+968", "+92", "+680", "+507", "+675", "+595", "+51", "+63", "+870", "+48", "+351", "+1",
        "+974", "+242", "+40", "+7", "+250", "+590", "+290", "+1 869", "+1 758", "+1 599", "+508",
        "+1 784", "+685", "+378", "+239", "+966", "+221", "+381", "+248", "+232", "+65", "+421",
        "+386", "+677", "+252", "+27", "+82", "+34", "+94", "+249", "+597", "+268", "+46", "+41",
        "+963", "+886", "+992", "+255", "+66", "+670", "+228", "+690", "+676", "+1 868", "+216",
        "+90", "+993", "+1 649", "+688", "+256", "+380", "+971", "+44", "+1", "+598", "+1 340",
        "+998", "+678", "+58", "+84", "+681", "+970", "+967", "+260", "+263") // Replace with your own list of country codes

    //var selectedCountryCode by remember { mutableStateOf(countryCodes[0]) }

      var selectedCountryCode by remember {mutableStateOf("+1")}

    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Select Country Code") },
                buttons = {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                            .height(300.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        countryCodes.forEach { countryCode ->
                           /** Button(
                                onClick = {
                                    //onCountryCodeSelected(countryCode)
                                    showDialog.value = false
                                },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text(text = countryCode)
                            }*/
                            Text(
                                text = countryCode,
                                modifier= Modifier
                                    .padding(top = 10.dp, start = 20.dp)
                                    .clickable {
                                        selectedCountryCode = countryCode
                                        showDialog.value = false
                                    }
                            )


                            Divider(modifier = Modifier.padding(start = 25.dp, end = 25.dp,top = 5.dp))
                        }
                    }
                }
            )
        }



        Log.i("start_date_checkout",start_date)
        Log.i("end_date_checkout",end_date)

        selection = DateSelection(startDate= LocalDate.parse(start_date) , endDate = LocalDate.parse(end_date))

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {


            Image(
                painter = rememberAsyncImagePainter(model = imageUrl.replace("\r\n", "")),
                contentDescription = "Stays Screen",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.FillBounds
            )

            /**That means user isn't logged in */
            if (token.isEmpty() || btnText.equals("Next")) {

                //Log.i("Token:", "Is Emtpy")
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 220.dp)
                ) {
                    TextField(
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        shape = textFieldShape,
                        value = fName.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp)
                            .shadow(elevation = 5.dp, shape = textFieldShape)
                            .clip(textFieldShape),
                        onValueChange = {
                            fName.value = it
                        },
                        label = {
                            Text(text = "Full Name")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFFFFFFF),
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent


                        )
                    )


                  Spacer(modifier = Modifier.height(30.dp))

                    TextField(
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        shape = textFieldShape,
                        value = email.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp)
                            .shadow(elevation = 5.dp, shape = textFieldShape)
                            .clip(textFieldShape),
                        onValueChange = {
                            email.value = it
                        },
                        label = {
                            Text(text = "Email Address")
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFFFFFFF),
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent


                        )
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 30.dp, end = 30.dp)) {

                        TextField(
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            shape = textFieldShape,
                            value = countryCode.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                //.padding(start = 30.dp, end = 15.dp)
                                .shadow(elevation = 5.dp, shape = textFieldShape)
                                .clip(textFieldShape)
                                .clickable {

                                    Log.i("Country Code:", "Country code clicked!")
                                    showDialog.value = true

                                },
                            enabled = false ,
                            onValueChange = {
                                countryCode.value = it
                            },
                            label = {
                                Text(text = selectedCountryCode)
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFFFFFFF),
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent


                            )
                        )

                        Spacer(modifier = Modifier.height(30.dp))

                        TextField(
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done
                            ),
                            shape = textFieldShape,
                            value = phoneNumber.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(3f)
                                .padding(start = 15.dp)
                                .shadow(elevation = 5.dp, shape = textFieldShape)
                                .clip(textFieldShape),
                            onValueChange = {
                                phoneNumber.value = it
                            },
                            label = {
                                Text(text = "Phone Number")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFFFFFFF),
                                disabledTextColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent


                            )
                        )


                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    TextField(
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        shape = textFieldShape,
                        value = location.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 30.dp, end = 30.dp)
                            .shadow(elevation = 5.dp, shape = textFieldShape)
                            .clip(textFieldShape)


                        ,
                        onValueChange = {
                            location.value = it
                        },
                        label = {
                            Text(text = "Location")
                        },
                        trailingIcon = {
                            IconButton(onClick = { }) {
                                Icon(

                                    painter = painterResource(R.drawable.ic_drop_down),
                                    contentDescription = "search icon",
                                    tint = Color(0XFFfdad02)
                                )
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color(0xFFFFFFFF),
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent


                        )
                    )



                }

            }

            /**That means user is logged in */
            else {


                Column(modifier = Modifier.fillMaxWidth()) {


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


                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(325.dp)
                        ) {

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
                                            val targetMonth =
                                                state.firstVisibleMonth.yearMonth.nextMonth
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
                    } // Card ends here


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
                            items(propExtras) {


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
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Spacer(modifier = Modifier.width(20.dp))

                                        Image(
                                            painter = painterResource(id = R.drawable.ic_taxi),
                                            contentDescription = "Image"
                                        )

                                        Spacer(modifier = Modifier.width(20.dp))

                                        Column {

                                            Text(
                                                text = it.name,
                                                fontSize = 20.sp,
                                                modifier = Modifier.padding(top = 10.dp)
                                            )

                                            Row(modifier = Modifier.padding(bottom = 10.dp)) {
                                                Text(
                                                    text = it.price.toString(),
                                                    fontSize = 12.sp
                                                )
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(
                                                    modifier = Modifier.padding(top = 3.dp),
                                                    text = currencySymbol,
                                                    fontSize = 10.sp,
                                                )
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


                                                },
                                            textAlign = TextAlign.Right,
                                            text = "+",
                                            fontSize = 22.sp,
                                            color = Color(0XFFfdad02)
                                        )

                                        // }


                                    }


                                }


                            }
                        })  // checkout screen main contents ends here
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
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

                       if (btnText.equals("Next")) {
                           token = "token"
                           btnText = "Checkout"
                       }

                       else {
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
                       }


                   }) {

                   Text(text = btnText)


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




