package com.example.exo2campus

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exo2campus.ui.theme.Exo2CampusTheme

private const val TAG = "Campus"
//1
data class Event(
    val id: Int,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int
)

val events = listOf(
    Event(1, "Conférence Kotlin", "Tech", "Salle 204", 30),
    Event(2, "Atelier Compose", "Tech", "Salle 101", 12),
    Event(3, "Tournoi de foot", "Sport", null, 10),
    Event(4, "Soirée jeux", "Soirée", "Hall", 0),
    Event(5, "Meetup Android", "Tech", "Amphi 1", 1)
)
//2
@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = onClick, modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(event.title)
            Text(event.category)
            Text(event.location ?: "Lieu à confirmer")
            if (event.capacity == 0) {
                Text("Complet")
            } else {
                Text("${event.capacity} places")
            }
            Button(onClick = onBook, enabled = event.capacity > 0) {
                Text("Réserver")
            }
        }
    }
}
//3
@Preview(showBackground = true)
@Composable
private fun EventCardCompletePreview() {
    Exo2CampusTheme {
        EventCard(event = events[3], onClick = { }, onBook = { })
    }
}
//4
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(events: List<Event>) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Campus") })
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(events, key = { it.id }) { event ->
                EventCard(
                    event = event,
                    onClick = { Log.d(TAG, "Clic sur la carte ${event.id}") },
                    onBook = { Log.d(TAG, "Réservation de ${event.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}
//5
