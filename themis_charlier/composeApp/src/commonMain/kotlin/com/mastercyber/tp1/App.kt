package com.mastercyber.tp1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var name by remember { mutableStateOf<String?>(null) }
        var sprite by remember { mutableStateOf<ByteArray?>(null) }
        var input by remember { mutableStateOf<String>("") }
        var guess by remember { mutableStateOf<Boolean?>(null) }
        var counter by remember { mutableStateOf<Int>(1) }
        var counterMax by remember { mutableStateOf<Int>(5) }
        var score by remember { mutableStateOf<Int>(0) }
        val coroutineScope = rememberCoroutineScope()

        suspend fun getPokemon() {
            if (counter <= counterMax) {
                val (fetchedName, fetchedSprite) = Greeting().fetchPokemon()
                name = fetchedName
                sprite = fetchedSprite
            }
        }
        fun validatePokemon() {
            counter += 1
            guess = Greeting().guessPokemon(name, input)
            if (guess == true) {
                score += 1
            }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                LaunchedEffect(showContent) {
                    if (showContent) {
                        getPokemon()
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    sprite?.toImageBitmap()?.let {
                        Image(
                            bitmap = it,
                            null,
                            colorFilter = ColorFilter.colorMatrix(
                                ColorMatrix(
                                    floatArrayOf(
                                        0.33f, 0.33f, 0.33f, 0f, 0f,
                                        0.33f, 0.33f, 0.33f, 0f, 0f,
                                        0.33f, 0.33f, 0.33f, 0f, 0f,
                                        0f, 0f, 0f, 1f, 0f
                                    )
                                )
                            )
                        )
                    }
                    Text("Compose: $name")
                    Text("$counter/5")
                    TextField(value = input, onValueChange = { input = it })
                    Button(onClick = {
                        validatePokemon()
                        coroutineScope.launch {
                            getPokemon()
                        }
                }){
                Text(text = "Deviner")
            }
                if (counter == counterMax) {
                    Text("Score : $score")
                }
            }
        }
    }
}
}