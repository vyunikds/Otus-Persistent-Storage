package com.otus_persistent_storage.presentation.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.otus_persistent_storage.data.room.UserEntity
import kotlinx.coroutines.delay

@Composable
fun UserScreen(
    users: List<UserEntity>,
    loading: Boolean,
    error: String?,
    onRetry: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().testTag("usersList")) {
        if (loading && users.isEmpty()) {
            var size by remember { mutableStateOf(8.dp) }
            val animatedSize by animateDpAsState(
                targetValue = size,
                animationSpec = tween(durationMillis = 1000),
                label = "progressIndicatorSize"
            )

            LaunchedEffect(Unit) {
                while (true) {
                    size = 8.dp
                    delay(200)
                    size = 12.dp
                    delay(200)
                    size = 16.dp
                    delay(200)
                    size = 12.dp
                    delay(200)
                }
            }

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                strokeWidth = animatedSize
            )
        } else if (error != null) {
            ErrorContent(
                error = error,
                onRetry = onRetry,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            UserList(
                users = users,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun UserList(users: List<UserEntity>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(users) { user ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = user.name, style = MaterialTheme.typography.headlineSmall)
                    Text(text = user.email, style = MaterialTheme.typography.bodyMedium)
                    Text(text = user.phone, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun ErrorContent(error: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Ошибка", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserScreen_Loading_Preview() {
    MaterialTheme {
        UserScreen(
            users = emptyList(),
            loading = true,
            error = null,
            onRetry = {}
        )
    }
}


@Preview(showBackground = true)
@Composable
fun UserScreen_Success_Preview() {
    MaterialTheme {
        UserScreen(
            users = listOf(
                UserEntity(
                    id = 1,
                    name = "Иван Иванов",
                    email = "ivan@example.com",
                    phone = "+7 (999) 123-45-67"
                ),
                UserEntity(
                    id = 2,
                    name = "Мария Петрова",
                    email = "maria@example.com",
                    phone = "+7 (999) 123-45-68"
                )
            ),
            loading = false,
            error = null,
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorContent_Preview() {
    MaterialTheme {
        ErrorContent(
            error = "Сетевая ошибка",
            onRetry = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserList_Preview() {
    MaterialTheme {
        UserList(
            users = listOf(
                UserEntity(
                    id = 1,
                    name = "Иван Иванов",
                    email = "ivan@example.com",
                    phone = "+7 (999) 123-45-67"
                ),
                UserEntity(
                    id = 2,
                    name = "Мария Петрова",
                    email = "maria@example.com",
                    phone = "+7 (999) 123-45-68"
                )
            )
        )
    }
}