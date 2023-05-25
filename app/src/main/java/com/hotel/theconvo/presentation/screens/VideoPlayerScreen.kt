package com.hotel.theconvo.presentation.screens

import android.content.pm.ActivityInfo
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

import com.hotel.theconvo.R
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@Destination
@Composable
fun VideoPlayerScreen(
    navigator: DestinationsNavigator?
) {











   /** Column(


        Modifier.fillMaxSize().background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        // Fetching the Local Context
        val mContext = LocalContext.current

        // Declaring a string value
        // that stores raw video url
        //val mVideoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val mVideoUrl = "http://23.97.138.116:7001/getUploadedFile/local/1161dab0-fa40-11ed-821c-edf1ed790bd8.mp4"

        // Declaring ExoPlayer
        val mExoPlayer = remember(mContext) {
            ExoPlayer.Builder(mContext).build().apply {
                val dataSourceFactory = DefaultDataSourceFactory(mContext, Util.getUserAgent(mContext, mContext.packageName))
                val source = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mVideoUrl))
                prepare(source)
            }
        }

        // Implementing ExoPlayer
        AndroidView(
            factory = { context ->
            PlayerView(context).apply {
                player = mExoPlayer

            }
        })
    }*/







}