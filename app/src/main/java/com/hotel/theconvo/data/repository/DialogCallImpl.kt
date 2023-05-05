package com.hotel.theconvo.data.repository

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.hotel.theconvo.MainActivity
import com.hotel.theconvo.domain.DialogCallback
import com.hotel.theconvo.util.LoadingDialog
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DialogCallImpl : DialogCallback {



    override fun showLoadingDialog() {

        Log.i("Show Dialog","Show Dialog")



       // LoadingDialog(isShowingDialog = true)


    }

    override fun dismissLoadingDialog() {

     Log.i("Hide Dialog","Hide Dialog")

    }
}

