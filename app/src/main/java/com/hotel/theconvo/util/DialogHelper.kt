package com.hotel.theconvo.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(isShowingDialog: Boolean, dismissOnBackPress: Boolean = false, dismissOnClickOutside: Boolean = false) {
    if (isShowingDialog) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside
            )
        ) {
            DialogContent()
        }
    }
}

@Composable
fun DialogContent() {
    Box(
        modifier = Modifier
            .size(76.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(
                    Alignment.Center
                ),
            color = Color.Red
        )
    }
}

@Preview
@Composable
fun PreviewSomeDialogContent() {
    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(20.dp),
                contentAlignment = Alignment.Center,
            ) {
                DialogContent()
            }
        }
    )
}