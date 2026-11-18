package com.example.exo2campus

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exo2campus.ui.theme.Exo2CampusTheme

private const val TAG = "Campus"

//1
data class EventP2(
    val id: Int,
    val title: String,
    val category: String,
    val location: String?,
    val capacity: Int,
    val description: String
)

val eventsP2 = listOf(
    EventP2(1, "Conférence Kotlin", "Tech", "Salle 204", 30, "Découvrez Kotlin avec des exemples simples."),
    EventP2(2, "Atelier Compose", "Tech", "Salle 101", 12, "Construisez un écran Android pas à pas."),
    EventP2(3, "Tournoi de foot", "Sport", null, 10, "Tournoi amical sur le terrain de l'école."),
    EventP2(4, "Soirée jeux", "Soirée", "Hall", 0, "Une soirée jeux de société entre étudiants."),
    EventP2(5, "Meetup Android", "Tech", "Amphi 1", 1, "Rencontre avec des développeurs Android.")
)

//2
@Composable
fun EventCardP2(
    event: EventP2,
    onClick: () -> Unit,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(onClick = onClick, modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = event.title, style = MaterialTheme.typography.titleLarge)
            Text(text = event.category, style = MaterialTheme.typography.labelLarge)
            Text(text = event.location ?: stringResource(R.string.event_location_unknown))
            if (event.capacity == 0) {
                Text(text = stringResource(R.string.event_full))
            } else {
                Text(
                    text = pluralStringResource(
                        R.plurals.available_seats,
                        event.capacity,
                        event.capacity
                    )
                )
            }
            Button(onClick = onBook, enabled = event.capacity > 0) {
                Text(text = stringResource(R.string.event_book))
            }
        }
    }
}

//6
@Composable
fun EventDetailScreen(
    event: EventP2,
    onBook: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
        )
        Text(text = event.title, style = MaterialTheme.typography.headlineMedium)
        Text(
            text = event.category,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = event.location ?: stringResource(R.string.event_location_unknown))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(text = event.description)
        }

        Button(onClick = onBook, enabled = event.capacity > 0) {
            Text(text = stringResource(R.string.event_book))
        }
    }
}

//7
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreenP2(events: List<EventP2>) {
    val categories = events.map { it.category }.distinct()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = stringResource(R.string.campus_title)) })
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories) { category ->
                        FilterChip(
                            selected = false,
                            onClick = { },
                            label = { Text(text = category) }
                        )
                    }
                }
            }
            items(events, key = { it.id }) { event ->
                EventCardP2(
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

//9
@Preview(showBackground = true)
@Composable
private fun ListeClairP2Preview() {
    Exo2CampusTheme { EventListScreenP2(eventsP2) }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ListeSombreP2Preview() {
    Exo2CampusTheme { EventListScreenP2(eventsP2) }
}

@Preview(showBackground = true)
@Composable
private fun DetailClairP2Preview() {
    Exo2CampusTheme { Surface { EventDetailScreen(eventsP2[0], onBook = { }) } }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun DetailSombreP2Preview() {
    Exo2CampusTheme { Surface { EventDetailScreen(eventsP2[0], onBook = { }) } }
}