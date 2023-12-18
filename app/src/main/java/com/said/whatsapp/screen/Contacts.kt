package com.said.whatsapp.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.said.whatsapp.R
import com.said.whatsapp.model.User
import com.said.whatsapp.screen.ui.theme.WhatsAppTheme
import com.said.whatsapp.screen.ui.theme.greeen
import com.said.whatsapp.screen.ui.theme.grey

class Contacts : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting3("Android")
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Greeting3(name: String, modifier: Modifier = Modifier) {
        val uid = intent.getStringExtra("uid")

        val userList = remember {
            mutableStateListOf(User())
        }
        val reference = Firebase.database.reference.child("users")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                userList.clear()
                children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user != null && user.uid != uid) {
                        userList.add(user ?: User())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAG", "onCancelled: ${error.message}")
            }

        })
        Surface(color = Color.Cyan) {
            Column(){
                CenterAlignedTopAppBar(
                    title = { Text(text = "Contacts", color = Color.White, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = greeen),
                    navigationIcon = { Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Color.White, modifier = Modifier
                        .padding(10.dp)
                        .clickable { })
                    },
                    actions = { Row {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.White, modifier = Modifier
                            .padding(10.dp)
                            .clickable { })
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, tint = Color.White, modifier = Modifier
                            .padding(10.dp)
                            .clickable { })
                    }
                    })
                LazyColumn {
                    items(userList) {
                        Card(
                            shape = RoundedCornerShape(0.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(vertical = 0.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .clickable {
//                            val i = Intent(
//                                this,
//                                MessageActivity::class.java
//                            )
//                            i.putExtra("uid", uid)
//                            i.putExtra("useruid", it.uid)
//                            startActivity(i)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(R.drawable.img_1)
                                        .crossfade(true)
                                        .build(),
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .height(45.dp)
                                        .width(45.dp)
                                        .border(width = 2.dp, color = greeen, shape = CircleShape),
                                    placeholder = painterResource(R.drawable.img_1),
                                    contentDescription = ("no image"),
                                    contentScale = ContentScale.Crop,

                                    colorFilter = ColorFilter.tint(greeen))
                                Column {
                                    Text(
                                        text = it.username ?: "",
                                        Modifier.padding(start = 12.dp),
                                        fontSize = 22.sp,
                                        fontFamily = FontFamily.Monospace,
//                                        color = Color.White
                                    )
                                    Text(
                                        text = "Hi there ...",
                                        Modifier.padding(start = 12.dp),
                                        fontSize = 22.sp,
                                        fontFamily = FontFamily.Cursive,
                                        color = Color.Gray
                                    )
                                }
                                Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End ){
                                    Spacer(modifier = Modifier.height(20.dp))
                                    Text(text = "16:52", color = grey)
                                }

                            }
                        }
                    }
                }
            }
        }


    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview3() {
        WhatsAppTheme {
            Greeting3(name = "")
        }
    }
}