package com.tuempresa.miapp.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun HotelPlaceField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Hotel / Dirección"
) {
    val context = LocalContext.current
    var query by remember { mutableStateOf(value) }
    var suggestions by remember { mutableStateOf<List<AutocompletePrediction>>(emptyList()) }
    var showSuggestions by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()) {

        // --- Caja de texto principal ---
        OutlinedTextField(
            value = query,
            onValueChange = { text ->
                query = text
                onValueChange(text)         // actualiza el estado del ViewModel
                showSuggestions = text.length >= 3

                if (showSuggestions) {
                    scope.launch {
                        // pequeño debounce
                        delay(250)
                        suggestions = searchPlaces(context, text)
                    }
                } else {
                    suggestions = emptyList()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(label) },
            singleLine = true
        )

        // --- Lista de sugerencias debajo, tipo Google Maps ---
        if (showSuggestions && suggestions.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            ) {
                items(suggestions) { prediction ->
                    val fullText = prediction.getFullText(null).toString()

                    Text(
                        text = fullText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Usuario selecciona un lugar:
                                query = fullText
                                onValueChange(fullText)   // se guarda en el TextField / ViewModel
                                showSuggestions = false
                                suggestions = emptyList()
                            }
                            .padding(8.dp)
                    )
                    Divider()
                }
            }
        }
    }
}

private suspend fun searchPlaces(
    context: Context,
    query: String
): List<AutocompletePrediction> {
    if (query.length < 3) return emptyList()

    val placesClient = Places.createClient(context)
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .build()

    return try {
        val response = placesClient.findAutocompletePredictions(request).await()
        response.autocompletePredictions
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}
