package com.said.whatsapp.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.said.whatsapp.R
import com.said.whatsapp.model.User
import com.said.whatsapp.screen.ui.theme.greeen
import com.said.whatsapp.ui.theme.WhatsAppTheme


class LogIn : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
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
                    text = "Log In to your Account",
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
                            val reference = Firebase.database.reference.child("users")

                            reference.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val children = snapshot.children
                                    var user1 = User(
                                        username = username.value,
                                        password = password.value,
                                        photo = R.drawable.img_1
                                    )
                                    children.forEach {
                                        val user = it.getValue(User::class.java)
                                        if (user1.username.equals(user?.username) && user1.password.equals(user?.password)){
                                            val intent = Intent(this@LogIn,Contacts::class.java)
                                            intent.putExtra("uid",user?.uid)
                                            startActivity(intent)
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.d("TAG", "onCancelled: ${error.message}")
                                }

                            })



                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = greeen)
                    ) {
                        Text(text = "Sign in")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = "Do not have an account ? Sign In", color = greeen, textAlign = TextAlign.Center,modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(this@LogIn, SignUp::class.java)
                            startActivity(intent)
                        })
                }

            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        WhatsAppTheme {
            Greeting("Android")
        }
    }
}