package com.hotel.theconvo.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotel.theconvo.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun TermsCondition(
    navigator: DestinationsNavigator?
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Row(modifier = Modifier.fillMaxWidth().padding(top = 20.dp)) {

            Text(text = "Terms & Conditions", fontSize = 25.sp,modifier = Modifier.padding(start = 20.dp), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "X", fontSize = 25.sp, modifier = Modifier.padding(end = 20.dp).clickable {
                navigator?.popBackStack()
            })
        }


        Spacer(modifier = Modifier.height(20.dp))

       Text(text = stringResource(id = R.string.terms_condition), modifier = Modifier.padding(start = 10.dp, end = 10.dp))



    }




}