package com.hotel.theconvo.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.hotel.theconvo.R
import com.hotel.theconvo.destinations.*
import com.hotel.theconvo.ui.theme.darkCardColor
import com.hotel.theconvo.util.AllKeys

import com.hotel.theconvo.util.ReservationDialog
import com.hotel.theconvo.util.SharedPrefsHelper
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun TabScreen(

    navigator: DestinationsNavigator?,
    isStay: Boolean,
    isReservation: Boolean

) {


    var expanded by remember { mutableStateOf(false) }


    var showDialog = remember { mutableStateOf(true) }

    val context = LocalContext.current
    SharedPrefsHelper.initialize(context)

    val sharedPreferences = remember { SharedPrefsHelper.sharedPreferences }

    var token by rememberSaveable {mutableStateOf(sharedPreferences.getString(AllKeys.token,"") ?: "")}

    var firstName by rememberSaveable{
        mutableStateOf(sharedPreferences.getString(AllKeys.firstName,"") ?: "")
    }

    var lastName by rememberSaveable{
        mutableStateOf(sharedPreferences.getString(AllKeys.lastName,"") ?: "")
    }

    if(isReservation) {


       if (showDialog.value) {
           ReservationDialog(onDismiss = {
            showDialog.value = !showDialog.value
           },
               onPositiveClick = {



                   showDialog.value = !showDialog.value
                   Log.i("Ok Button Clicked", "Ok Button Clicked!")


               })
       }

    }








    Column {

      Row(
          modifier = Modifier
              .fillMaxWidth()
              .padding(top = 5.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween

      ) {

          Image(
              painter = painterResource(id = R.drawable.ic_burger_menu),
              contentDescription = "Drawable Icon",
              modifier = Modifier
                  .weight(1f)
                  .padding(top = 2.dp)
                  .clickable {
                      navigator?.navigate(NavDrawerScreenDestination())
                  }
              )

          Image(
              painter = if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_convo_logo_white) else painterResource(id = R.drawable.ic_convo_logo),
              contentDescription = "Convo Logo",
              modifier = Modifier
                  .size(25.dp)
                  .weight(3f)
          )


          Box(
              modifier = Modifier.weight(1f)
          ) {





             /** ProfileImageWithInitials(

                  imageUrl = null,
                  name = "${firstName} ${lastName}",
                  size = 50.dp,

                  textBackgroundColor = darkCardColor ,
                  textColor = Color.White,

              )*/

              val painter = null



              Box(
                  modifier = Modifier.size(40.dp),
                  contentAlignment = Alignment.Center
              ) {


                  var initials = getInitialsFromName("${firstName} ${lastName}")

                  Box(
                      modifier = Modifier
                          .clickable {
                              expanded = true
                          }
                          .size(50.dp)
                          .background(darkCardColor, CircleShape),
                      contentAlignment = Alignment.Center
                  ) {
                      Text(
                          text = if (initials.isEmpty()) "C" else initials,
                          color = Color.White,
                          fontWeight = FontWeight.Bold,
                          //fontSize = size * 0.4f,
                      )
                  }



              }//Box ends here

             /** Image(
                  painter = painterResource(id = R.drawable.ic_prof),
                  contentDescription = "Drawable Icon",
                  modifier = Modifier
                      //.weight(1f)
                      .padding(2.dp)
                      .clickable {
                          expanded = true
                          // navigator?.navigate(LoginScreenDestination())
                      }
              )*/

              if(expanded) {
                  DropdownMenu(
                      modifier = Modifier
                          .widthIn(max = 240.dp)
                          .padding(16.dp)
                          .align(Alignment.BottomStart),
                      onDismissRequest = { expanded = false },
                      expanded = expanded
                  ){

                      if(token.isEmpty() ) {




                          DropdownMenuItem(onClick = {

                              navigator?.navigate(LoginScreenDestination())
                              expanded = !expanded

                          }) {

                              Text(text = "Login")


                          }

                          DropdownMenuItem(onClick = {

                              navigator?.navigate(RegistrationScreenDestination())
                              expanded = !expanded

                          }) {

                              Text(text = "Signup")


                          }

                      }

                      else {

                          DropdownMenuItem(onClick = {

                              navigator?.navigate(ManageAccountsTabDestination())
                              expanded = !expanded

                          }) {

                              Text(text = "Manage Account")


                          }

                          DropdownMenuItem(onClick = {

                              navigator?.navigate(MyTripsScreenDestination())
                              expanded = !expanded

                          }) {

                              Text(text = "My trips")


                          }

                          DropdownMenuItem(onClick = {


                              sharedPreferences.edit {
                                  putString(
                                      AllKeys.token,
                                      ""
                                  )

                                  putString(
                                      AllKeys.firstName,
                                      ""
                                  )
                                  putString(
                                      AllKeys.lastName,
                                      ""
                                  )

                                  putString(
                                      AllKeys.email,
                                      ""
                                  )
                                  putString(
                                      AllKeys.mobileNumber,""
                                  )
                              }
                              expanded = !expanded

                              navigator?.navigate(
                                  TabScreenDestination(
                                      isStay = true,
                                      isReservation = false
                                  )
                              )
                              Toast.makeText(context, "Logout Successfully", Toast.LENGTH_LONG)
                                  .show()


                          }) {

                              Text(text = "Logout")


                          }

                      }

                  }
              }


          }



      }


        Spacer(modifier = Modifier.height(5.dp))

        MyTabLayout(navigator,isStay)

    }



}

@Composable
fun MyTabLayout(navigator: DestinationsNavigator?, isStay: Boolean) {
    val tabItems = listOf("STAY", "OWN","CONVOS")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column {
        TabRow(
            modifier = Modifier.padding(start = 20.dp,end = 20.dp),
            selectedTabIndex = selectedTabIndex,
            backgroundColor = if(isSystemInDarkTheme()) darkCardColor  else Color.White,
            divider = {},
            indicator = {
                TabRowDefaults.Indicator(
                    modifier = Modifier

                        .tabIndicatorOffset(it[selectedTabIndex]),
                    color = Color(0XFFfdad02),
                  //  height = TabRowDefaults.IndicatorHeight * 1.5F
                     height = 1.dp
                )
            }
        ) {
            tabItems.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                       // Text(title )

                        Text(text = title, color =  if (isSystemInDarkTheme()) Color.White else Color.Black)
                    }
                )
            }
        } //Tab Row ends here



        // content for the currently selected tab
        when (selectedTabIndex) {
            0 -> {
                //Text("Content for Tab 1")
                if (isStay)
                StayScreen(isStay,navigator)
                else
                    BrowseScreen(navigator)
            }
            1 -> {
                //Text("Content for Tab 2")
                OwnScreen()
            }
            2-> {
                //Text("Content for Tab 3")
                ConvosScreen()
            }
        }
    }// column ends here
}

@Composable
fun ProfileImageWithInitials(

    imageUrl: String?,
    name: String,
    size: Dp,
    // placeholderImage: ImageVector,
    // placeholderBackgroundColor: Color,
    textBackgroundColor: Color,
    textColor: Color,
    transformation: Transformation? = CircleCropTransformation(),
    onClick: () -> Unit
) {

}

// Helper function to get initials from a name
fun getInitialsFromName(name: String): String {
    val names = name.split(" ")
    return if (names.size >= 2) {
        val firstName = names[0].firstOrNull()?.toString() ?: ""
        val lastName = names[names.size - 1].firstOrNull()?.toString() ?: ""
        "$firstName$lastName"
    } else {
        names.getOrNull(0)?.firstOrNull()?.toString() ?: ""
    }
}
