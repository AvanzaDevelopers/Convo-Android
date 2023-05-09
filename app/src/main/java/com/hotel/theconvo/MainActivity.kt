package com.hotel.theconvo

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.hotel.theconvo.destinations.SplashScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.hotel.theconvo.presentation.vm.ConvoViewModel
import com.hotel.theconvo.ui.theme.TheConvoTheme
import com.hotel.theconvo.usecase.LoginUseCase
import com.hotel.theconvo.util.UiState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
 class MainActivity : ComponentActivity() {

     @Inject
     lateinit var getUserByIdUseCase: LoginUseCase

    @Inject
    lateinit var sharedViewModel: ConvoViewModel

    lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

     companion object {
       lateinit var loginUseCase: LoginUseCase
         lateinit var mGoogleSignInClient: GoogleSignInClient
          lateinit var signInLauncher: ActivityResultLauncher<Intent>


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


       // googleSignIn()

        loginUseCase = getUserByIdUseCase

        /**Google login for testing purposes */

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("103571255332-js1ots0bs0f0pta0frr9itulmekbmk1p.apps.googleusercontent.com")
            .requestEmail()
            .build()

        // Initialize sign in client

        mGoogleSignInClient = GoogleSignIn.getClient(
            this@MainActivity,
            gso
        )


        signInLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult> { result ->
                val task: Task<GoogleSignInAccount> =
                    GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                handleSignInResult(task,this)
            })

    }

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>, context: Context) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account.idToken
            UiState.Success(account)
            Log.i("User Name:",account.displayName.toString())

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("userEmail", "userEmail")
            startActivity(intent)
//            val intent = Intent(this@LoginActivity, UserHomeActivity::class.java)
//            startActivity(intent)
        } catch (e: ApiException) {
            Log.w("Sign In Error", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(context, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun signOut(context: Context, mGoogleSignInClient: GoogleSignInClient) {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(
                //context.mainExecutor
            ) {
                Log.d("Signed Out: ", "Successful")
                Toast.makeText(context, "Signed out successfully", Toast.LENGTH_SHORT)
                    .show()
            }
    }







}

@Composable
@Destination(start = true)
fun Greeting( navigator: DestinationsNavigator?


              ) {


    val context = LocalContext.current

    if ((context as? ComponentActivity)?.intent?.extras?.getString("userEmail") != null) {
        // Handle the data URI as needed
        navigator?.navigate(TabScreenDestination())
    }


    else {
        navigator?.navigate(SplashScreenDestination())
    }


}







@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheConvoTheme {
        //Greeting("Android")
        Greeting(null)
    }
}