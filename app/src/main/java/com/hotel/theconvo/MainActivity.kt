package com.hotel.theconvo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hotel.theconvo.data.remote.dto.req.SocialReq
import com.hotel.theconvo.data.remote.dto.req.Token
import com.hotel.theconvo.data.remote.dto.response.Amenity
import com.hotel.theconvo.data.remote.dto.response.PropertyExtra
import com.hotel.theconvo.data.remote.dto.response.Reviews
import com.hotel.theconvo.data.remote.dto.response.SearchResult
import com.hotel.theconvo.destinations.SplashScreenDestination
import com.hotel.theconvo.destinations.TabScreenDestination
import com.hotel.theconvo.presentation.vm.ConvoViewModel
import com.hotel.theconvo.reels.Reel
import com.hotel.theconvo.ui.theme.TheConvoTheme
import com.hotel.theconvo.usecase.LoginUseCase
import com.hotel.theconvo.util.AESUtils
import com.hotel.theconvo.util.DateSelection
import com.hotel.theconvo.util.UiState
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
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

          lateinit var propList: List<SearchResult>

          lateinit var amenitiesList : List<Amenity>

          lateinit var propExtras : List<PropertyExtra>

         lateinit var reviews: List<Reviews>
         // lateinit var extrasList : List<Extras>


     }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

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
            //.requestServerAuthCode("103571255332-js1ots0bs0f0pta0frr9itulmekbmk1p.apps.googleusercontent.com", true)
            .requestServerAuthCode("103571255332-js1ots0bs0f0pta0frr9itulmekbmk1p.apps.googleusercontent.com")
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





            val serverAuthCode = account.serverAuthCode


       exchangeAuthCodeForRefreshToken(serverAuthCode.toString())

            GlobalScope.launch {


                //val socialToken =
                val soccialReq = SocialReq(
                    _token = Token(idToken = account.idToken.toString()),
                    buisnessCategory = "Dealer",
                    custodianModel = "Y",
                    custodudianModel = "Y",
                    emailAddress = account.email,
                    firstname = account.displayName!!.split(" ")[0],
                    generateNewWallet = "Y",
                    lastname = account.displayName!!.split(" ")[1],
                    mode = "NEW",
                    orgType = "INDIVIDUAL",
                    passwordHash = account.email!!.encryptCBC(),
                    registrationDate = "02/06/2022",
                    registrationMechanism = "Google",
                    userId = account.email

                )


               // loginUseCase.socialInvoke(soccialReq)

               // Log.i("Social Data  is:", loginUseCase.socialInvoke(soccialReq).toString())

                try {
                    if (loginUseCase.socialInvoke(soccialReq).responseDescription.equals("Email ID already Exists")) {
                        loginUseCase.socialReLoginInvoke(soccialReq)


                        Log.i(
                            "Social Login Success:",
                            loginUseCase.socialReLoginInvoke(soccialReq).toString()
                        )
                    } else {
                        loginUseCase.socialInvoke(soccialReq)

                        Log.i("Social Data  is:", loginUseCase.socialInvoke(soccialReq).toString())

                    }
                }
                catch (e: Exception) {
                    Log.i("Social Exception is:",e.message.toString())
                   // Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_LONG).show()
                }


            }

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

private fun String.encryptCBC(): String {
    val iv = IvParameterSpec(AESUtils.SECRET_IV.toByteArray())
    val keySpec = SecretKeySpec(AESUtils.SECRET_KEY.toByteArray(), "AES")
    val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
    cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
    val crypted = cipher.doFinal(this.toByteArray())
    val encodedByte = Base64.encode(crypted, Base64.DEFAULT)
    return String(encodedByte)
}



@Composable
@Destination(start = true)
fun Greeting( navigator: DestinationsNavigator?


              ) {




    val context = LocalContext.current

    if ((context as? ComponentActivity)?.intent?.extras?.getString("userEmail") != null) {
        // Handle the data URI as needed
        navigator?.navigate(TabScreenDestination(true,false))
    }


    else {
        navigator?.navigate(SplashScreenDestination())
    }


}





fun exchangeAuthCodeForRefreshToken(serverAuthCode: String) {
    val clientId = "103571255332-js1ots0bs0f0pta0frr9itulmekbmk1p.apps.googleusercontent.com"
    val clientSecret = "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"
    val redirectUri = "https://convo-app-e7d78.firebaseapp.com/_/auth/handler"
    val tokenEndpoint = "https://oauth2.googleapis.com/token"

    val postData = StringBuilder()
    postData.append("code=").append(URLEncoder.encode(serverAuthCode, "UTF-8"))
    postData.append("&client_id=").append(URLEncoder.encode(clientId, "UTF-8"))
    postData.append("&client_secret=").append(URLEncoder.encode(clientSecret, "UTF-8"))
    postData.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8"))
    postData.append("&grant_type=authorization_code")


    val postDataBytes = postData.toString().toByteArray(charset("UTF-8"))

    GlobalScope.launch(Dispatchers.IO) {
        val url = URL(tokenEndpoint)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        connection.setRequestProperty("Content-Length", postDataBytes.size.toString())
        connection.doOutput = true

        val outputStream = connection.outputStream
        outputStream.write(postDataBytes)
        outputStream.flush()
        outputStream.close()

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()

            val refreshToken = extractRefreshToken(response.toString())
            // Process the refresh token as needed
        } else {
            // Handle error response
        }

        connection.disconnect()
    }
}
fun extractRefreshToken(response: String): String? {
    // Extract the refresh token from the response JSON
    // Parse the JSON and extract the refresh token field
    // Return the refresh token value
    return response // Replace with your implementation
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheConvoTheme {
        //Greeting("Android")
        Greeting(null)
    }
}