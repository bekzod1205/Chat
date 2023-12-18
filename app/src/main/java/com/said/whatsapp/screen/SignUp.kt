package com.said.whatsapp.screen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.said.whatsapp.R
import com.said.whatsapp.model.User
import com.said.whatsapp.screen.ui.theme.WhatsAppTheme
import com.said.whatsapp.screen.ui.theme.greeen

class SignUp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2("Android")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Greeting2(name: String, modifier: Modifier = Modifier) {
        var username = remember {
            mutableStateOf("")
        }
        var password = remember {
            mutableStateOf("")
        }
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                )
                Text(
                    text = "Sign In your Account",
                    fontStyle = FontStyle.Normal,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = username.value,
                        onValueChange = {
                            username.value = it
                        },
                        label = { Text(text = "Your Username") },
                        placeholder = { Text(text = "Username") },
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = password.value,
                        onValueChange = {
                            password.value = it
                        },
                        label = { Text(text = "Your Password") },
                        placeholder = { Text(text = "Password") },
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            var user = User(
                                username = username.value,
                                password = password.value,
                                photo = R.drawable.img_1
                            )
                            setUser(user)

                            val intent = Intent(this@SignUp,Contacts::class.java)
                            intent.putExtra("uid",user.uid)
                            startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = greeen)
                    ) {
                        Text(text = "Sign in")

                    }
                }

            }

        }
    }


    fun setUser(user: User) {
        val reference = Firebase.database.reference.child("users").child(user.uid?:"")
        reference.setValue(user)
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview2() {
        com.said.whatsapp.ui.theme.WhatsAppTheme {
            Greeting2("Android")
        }
    }
}