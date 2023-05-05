package com.hotel.theconvo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hotel.theconvo.destinations.LoginScreenDestination
import com.hotel.theconvo.destinations.SplashScreenDestination
import com.hotel.theconvo.domain.DialogCallback
import com.hotel.theconvo.presentation.vm.ConvoViewModel

import com.hotel.theconvo.ui.theme.TheConvoTheme
import com.hotel.theconvo.usecase.LoginUseCase
import com.hotel.theconvo.util.LoadingDialog
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
 class MainActivity : ComponentActivity() {

     @Inject
     lateinit var getUserByIdUseCase: LoginUseCase

    @Inject
    lateinit var sharedViewModel: ConvoViewModel


     companion object {
       lateinit var loginUseCase: LoginUseCase

     }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheConvoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //Greeting("Android")
                    DestinationsNavHost(navGraph = NavGraphs.root)

                }
            }
        }


        loginUseCase = getUserByIdUseCase

       /** lifecycleScope.launch {

            val user = getUserByIdUseCase()
            loginUseCase = getUserByIdUseCase

        }*/


    }


}

@Composable
@Destination(start = true)
fun Greeting( navigator: DestinationsNavigator?


              ) {




    navigator?.navigate(SplashScreenDestination())


}







@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheConvoTheme {
        //Greeting("Android")
        Greeting(null)
    }
}