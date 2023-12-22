package com.said.whatsapp.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
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
import com.said.whatsapp.model.User
import com.said.whatsapp.screen.ui.theme.LightBlue
import com.said.whatsapp.screen.ui.theme.LightGreen
import com.said.whatsapp.screen.ui.theme.LightOrange
import com.said.whatsapp.screen.ui.theme.LightPink
import com.said.whatsapp.screen.ui.theme.LightPurple
import com.said.whatsapp.screen.ui.theme.WhatsAppTheme
import com.said.whatsapp.screen.ui.theme.greeen
import com.said.whatsapp.screen.ui.theme.grey

class SearchActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val uid = intent.getStringExtra("uid_2")
                    val userList = remember {
                        mutableStateListOf(User())
                    }


                    val colors = remember {
                        mutableStateListOf(LightBlue)
                    }
                    val text = remember {
                        mutableStateOf("")
                    }
                    colors.add(LightGreen)
                    colors.add(LightOrange)
                    colors.add(LightPurple)
                    colors.add(LightPink)


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
                    var users = userList
                    Column() {
                        CenterAlignedTopAppBar(title = {
                            Text(
                                text = "", color = Color.White, fontWeight = FontWeight.Bold
                            )
                        },
                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = greeen),
                            actions = {
                                OutlinedTextField(
                                    modifier = Modifier.fillMaxWidth(),
                                    value = text.value,
                                    onValueChange = { newText ->
                                        text.value = newText
                                        if (newText.isNotEmpty()) {
                                            for (i in userList) {
                                                if (i.username?.lowercase()!!
                                                        .contains(text.value)
                                                ) {
                                                    users.clear()
                                                    users.add(i)
                                                }
                                            }
                                        }else{
                                            users.clear()
                                            users = userList
                                        }
                                    },
                                    leadingIcon = {
                                        Icon(imageVector = Icons.Default.ArrowBack,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier
                                                .padding(10.dp)
                                                .clickable { })
                                    },
                                    shape = CircleShape,
                                    colors = TextFieldDefaults.outlinedTextFieldColors(
                                        textColor = Color.White,
                                        containerColor = Color.Transparent,
                                        focusedBorderColor = Color.Transparent,
                                        unfocusedBorderColor = Color.Transparent
                                    ), maxLines = 1
                                )
                            })
                        LazyColumn {
                            items(users) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    val random = colors.random()
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
                                                    val i = Intent(
                                                        this@SearchActivity,
                                                        PersonalChat::class.java
                                                    )
                                                    i.putExtra("uid_1", uid)
                                                    i.putExtra("user_uid", it.uid)
                                                    startActivity(i)
                                                }, verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .width(50.dp)
                                                    .height(50.dp),
                                                shape = CircleShape,
                                                colors = CardDefaults.cardColors(
                                                    containerColor = random,
                                                )
                                            ) {
                                                Row(
                                                    modifier = Modifier.fillMaxSize(),
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = it.username.toString().first()
                                                            .uppercase(),
                                                        fontWeight = FontWeight.ExtraBold,
                                                        color = Color.White,
                                                        fontSize = 24.sp,
                                                        modifier = Modifier.fillMaxWidth(),
                                                        textAlign = TextAlign.Center
                                                    )
                                                }
                                            }
                                            Column {
                                                Text(
                                                    text = it.username ?: "",
                                                    Modifier.padding(start = 12.dp),
                                                    fontSize = 22.sp,
                                                    fontFamily = FontFamily.Monospace,
                                                )
                                                Text(
                                                    text = "Hi there ...",
                                                    Modifier.padding(start = 12.dp),
                                                    fontSize = 22.sp,
                                                    fontFamily = FontFamily.Cursive,
                                                    color = Color.Gray
                                                )
                                            }
                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                Spacer(modifier = Modifier.height(20.dp))
                                                Text(text = "16:52", color = grey)
                                            }

                                        }
                                    }
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp)
                                        .height(1.dp)
                                        .background(color = Color.Gray)
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

