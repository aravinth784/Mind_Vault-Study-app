package com.example.owlapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.owlapplication.ui.theme.OwlApplicationTheme

class LoginActivity : ComponentActivity() {
    private lateinit var databaseHelper: UserDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = UserDatabaseHelper(this)
        setContent {
            OwlApplicationTheme {
                LoginScreen(this, databaseHelper)
            }
        }
    }
}

@Composable
fun LoginScreen(context: Context, databaseHelper: UserDatabaseHelper) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF6A1B9A), // Deep Purple
                        Color(0xFFAB47BC), // Lighter Purple
                        Color(0xFFF06292)  // Vibrant Pink
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image at the top
            Image(
                painter = painterResource(id = R.drawable.loginowl),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp) // Make the image larger
                    .padding(top = 50.dp) // Top padding to keep some distance from the screen edge
            )

            // Spacer between the image and login form
            Spacer(modifier = Modifier.height(24.dp))

            // Login box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .background(Color.White.copy(alpha = 0.85f), RoundedCornerShape(16.dp))
                    .padding(24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Login",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive,
                        color = Color(0xFF6A1B9A)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textStyle = MaterialTheme.typography.body1.copy(
                            color = Color(0xFFF06292) // Vibrant pink color
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFAB47BC),
                            cursorColor = Color(0xFF6A1B9A),
                            unfocusedBorderColor = Color(0xFF6A1B9A)
                        )
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        textStyle = MaterialTheme.typography.body1.copy(
                            color = Color(0xFFF06292) // Vibrant pink color
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFAB47BC),
                            cursorColor = Color(0xFF6A1B9A),
                            unfocusedBorderColor = Color(0xFF6A1B9A)
                        )
                    )

                    if (error.isNotEmpty()) {
                        Text(
                            text = error,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            if (username.isNotEmpty() && password.isNotEmpty()) {
                                val user = databaseHelper.getUserByUsername(username)
                                if (user != null && user.password == password) {
                                    error = "Successfully logged in"
                                    context.startActivity(Intent(context, MainActivity::class.java))
                                } else {
                                    error = "Invalid username or password"
                                }
                            } else {
                                error = "Please fill all fields"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF6A1B9A)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Login", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(
                            onClick = {
                                context.startActivity(Intent(context, RegisterActivity::class.java))
                            },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF6A1B9A))
                        ) {
                            Text(text = "Register")
                        }
                        TextButton(
                            onClick = { /* Add forgot password functionality */ },
                            colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF6A1B9A))
                        ) {
                            Text(text = "Forgot password?")
                        }
                    }
                }
            }
        }
    }
}
