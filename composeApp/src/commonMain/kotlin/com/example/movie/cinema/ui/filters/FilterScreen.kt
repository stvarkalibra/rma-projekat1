package com.example.movie.cinema.ui.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ClearAll
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.movie.app.common.UiState
import com.example.movie.cinema.model.Genre
import com.example.movie.cinema.ui.start_screen.StartScreenIntent
import com.example.movie.cinema.ui.start_screen.StartScreenState
import kotlin.math.roundToInt

@Composable
fun FilterScreen(
    state: StartScreenState,
    onIntent: (StartScreenIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        onIntent(StartScreenIntent.FilterScreenOpened)
    }

    FilterMoviesContent(
        state = state,
        onIntent = onIntent,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterMoviesContent(
    state: StartScreenState,
    onIntent: (StartScreenIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val localDraftFilters = state.pendingFilters
    val selectedRating = localDraftFilters.ratingFrom ?: 0.0

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing.union(WindowInsets.navigationBars).union(WindowInsets.ime),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Discover filters",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "${state.activeFilters.activeFiltersCount()} active filters",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onIntent(StartScreenIntent.DraftClearAll)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ClearAll,
                            contentDescription = "Clear filters"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            Surface(
                shadowElevation = 10.dp,
                tonalElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onIntent(StartScreenIntent.DraftClearAll)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Reset")
                    }

                    Button(
                        onClick = {
                            onIntent(StartScreenIntent.ApplyDraftFilters)
                            onBackClick()
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Apply filters")
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            FilterSectionCard(
                title = "Search",
                subtitle = "Find a movie by title"
            ) {
                OutlinedTextField(
                    value = localDraftFilters.searchText,
                    onValueChange = { newValue: String ->
                        onIntent(StartScreenIntent.DraftQueryChanged(newValue))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    placeholder = {
                        Text("Type movie name...")
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(18.dp)
                )
            }

            FilterSectionCard(
                title = "Genre",
                subtitle = "Choose one genre"
            ) {
                when (val currentGenreState = state.genreState) {
                    UiState.Idle -> {
                        Text(
                            text = "Genres are preparing...",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    UiState.Loading -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp),
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text("Loading genres...")
                        }
                    }

                    is UiState.Error -> {
                        Text(
                            text = currentGenreState.description,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    UiState.Empty -> {
                        Text(
                            text = "No genres found.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    is UiState.Success<*> -> {
                        val availableGenres: List<Genre> = state.genreItems

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = localDraftFilters.pickedGenreId == null,
                                onClick = {
                                    onIntent(StartScreenIntent.DraftGenreSelected(null))
                                },
                                label = {
                                    Text("Any")
                                }
                            )

                            availableGenres.forEach { genreItem: Genre ->
                                FilterChip(
                                    selected = localDraftFilters.pickedGenreId == genreItem.genreId,
                                    onClick = {
                                        onIntent(
                                            StartScreenIntent.DraftGenreSelected(
                                                genreItem.genreId
                                            )
                                        )
                                    },
                                    label = {
                                        Text(genreItem.genreName)
                                    }
                                )
                            }
                        }
                    }
                }
            }

            FilterSectionCard(
                title = "Release year",
                subtitle = "Limit the release period"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = localDraftFilters.yearFrom?.toString().orEmpty(),
                        onValueChange = { typedValue: String ->
                            onIntent(
                                StartScreenIntent.DraftMinYearChanged(
                                    typedValue.toIntOrNull()
                                )
                            )
                        },
                        modifier = Modifier.weight(1f),
                        label = { Text("From") },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp)
                    )

                    OutlinedTextField(
                        value = localDraftFilters.yearTo?.toString().orEmpty(),
                        onValueChange = { typedValue: String ->
                            onIntent(
                                StartScreenIntent.DraftMaxYearChanged(
                                    typedValue.toIntOrNull()
                                )
                            )
                        },
                        modifier = Modifier.weight(1f),
                        label = { Text("To") },
                        singleLine = true,
                        shape = RoundedCornerShape(18.dp)
                    )
                }
            }

            FilterSectionCard(
                title = "Minimum IMDb rating",
                subtitle = "Show only stronger rated movies"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "0.0",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Slider(
                        value = selectedRating.toFloat(),
                        onValueChange = { sliderValue: Float ->
                            val roundedRating = (sliderValue * 10).roundToInt() / 10.0
                            onIntent(
                                StartScreenIntent.DraftMinRatingChanged(
                                    roundedRating
                                )
                            )
                        },
                        modifier = Modifier.weight(1f),
                        valueRange = 0f..10f,
                        steps = 99
                    )

                    RatingBubble(
                        text = "%.1f".format(selectedRating)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun FilterSectionCard(
    title: String,
    subtitle: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            HorizontalDivider()

            content()
        }
    }
}

@Composable
private fun RatingBubble(
    text: String
) {
    Box(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = CircleShape
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
    }
}