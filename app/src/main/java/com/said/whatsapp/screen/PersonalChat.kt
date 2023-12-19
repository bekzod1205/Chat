package com.said.whatsapp.screen

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.database.values
import com.said.whatsapp.R
import com.said.whatsapp.model.Message
import com.said.whatsapp.model.User
import com.said.whatsapp.screen.ui.theme.LightGreen
import com.said.whatsapp.ui.theme.WhatsAppTheme

class PersonalChat : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting2()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Greeting2(modifier: Modifier = Modifier) {
        val uid = intent.getStringExtra("uid_1")
        val user_id = intent.getStringExtra("user_uid")
        val messages = remember {
            mutableStateListOf(Message())
        }
        val text = remember {
            mutableStateOf("")
        }

        val reference = com.google.firebase.ktx.Firebase.database.reference
            .child("users")
            .child(uid!!)
            .child("message")
            .child(user_id!!)




        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                messages.clear()
                children.forEach {
                    val message = it.getValue(Message::class.java)
                    messages.add(message ?: Message())
                    Log.d("TAG", "onCreate: ${message?.text}")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAG", "onCancelled: ${error.message}")
            }
        })


        val message = Message(uid, user_id, text.value, "15.12")
        val reference_1 = Firebase.database.reference.child("users")
        val key = reference_1.push().key.toString()
        val key1 = reference_1.push().key.toString()
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp)
            )
            {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = ""
                    )
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        contentDescription = "avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = "Ali",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .height(2.dp)
                    .background(color = Color.Gray)
            )
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(bottom = 65.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    items(messages) {
                        MessageRow(uid = uid.toString(), message = it)
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        value = text.value,
                        onValueChange = { newText ->
                            text.value = newText
                        },
                        label = {
                            Text(text = "Message")
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                if (text.value != "") {
                                    reference_1.child(uid)
                                        .child("message")
                                        .child(user_id)
                                        .child(key)
                                        .setValue(message)
                                    reference_1.child(user_id)
                                        .child("message")
                                        .child(uid)
                                        .child(key1)
                                        .setValue(message)
                                    text.value = ""
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = ""
                                )
                            }
                        },
                        maxLines = 3
                    )
                }
            }
        }
    }

    @Composable
    fun MessageRow(uid: String, message: Message) {
        if (message.from.toString() != uid) {
            Card(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(vertical = 5.dp)
                    .padding(start = 10.dp)
                    .padding(end = 40.dp)
            )
            {
                Text(
                    modifier = Modifier
                        .padding(10.dp),
                    text = message.text.toString()
                )
            }
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(vertical = 5.dp)
                        .padding(start = 40.dp)
                        .padding(end = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = LightGreen
                    ),
                )
                {
                    Text(
                        modifier = Modifier
                            .padding(10.dp),
                        text = message.text.toString()
                    )
                }
            }
        }
    }

    fun getUser(uid: String): User? {
        val reference = com.google.firebase.ktx.Firebase.database.reference.child("users")

        var result: User? = null

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val children = snapshot.children
                children.forEach {
                    val user = it.getValue(User::class.java)
                    if (user?.uid.toString() == uid) {
                        result = user!!
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAG", "onCancelled: ${error.message}")
            }

        })
        return result
    }

}
