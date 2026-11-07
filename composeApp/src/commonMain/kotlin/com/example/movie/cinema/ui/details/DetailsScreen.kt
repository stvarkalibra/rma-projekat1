package com.example.movie.cinema.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movie.app.common.UiState
import com.example.movie.app.common.ImageUrlBuilder
import com.example.movie.cinema.model.CastMember
import com.example.movie.cinema.model.MovieDetails
import kotlinx.coroutines.flow.Flow

@Composable
fun DetailScreen(
    movieId: String,
    state: DetailsState,
    effect: Flow<DetailsEffect>? = null,
    onIntent: (DetailsIntent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val localUriHandler = LocalUriHandler.current

    LaunchedEffect(movieId) {
        onIntent(DetailsIntent.FetchDetails(movieId))
    }

    LaunchedEffect(effect) {
        effect?.collect { currentEffect ->
            when (currentEffect) {
                DetailsEffect.GoBack -> onBackClick()
                is DetailsEffect.LaunchTrailer -> {
                    localUriHandler.openUri(currentEffect.trailerUrl)
                }
            }
        }
    }

    MovieDetailsContent(
        state = state,
        onIntent = onIntent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailsContent(
    state: DetailsState,
    onIntent: (DetailsIntent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Movie Details")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onIntent(DetailsIntent.OnBackClick)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        when (val currentState = state.screenState) {
            UiState.Idle -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Preparing details...")
                }
            }

            UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = currentState.description,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                onIntent(DetailsIntent.RetryLoad)
                            }
                        ) {
                            Text(text = "Retry")
                        }
                    }
                }
            }

            UiState.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No details found.")
                }
            }

            is UiState.Success -> {
                val detailsBundle = state.bundle ?: return@Scaffold

                MovieDetailsBody(
                    detailsBundle = detailsBundle,
                    imageHost = state.imageHost,
                    onTrailerTap = { trailerKey ->
                        onIntent(DetailsIntent.OnTrailerClick(trailerKey))
                    },
                    outerPadding = innerPadding
                )
            }
        }
    }
}

@Composable
private fun MovieDetailsBody(
    detailsBundle: MovieDetails,
    imageHost: String,
    onTrailerTap: (String) -> Unit,
    outerPadding: PaddingValues
) {
    val selectedMovie = detailsBundle.selectedMovie

    val backdropImageUrl = ImageUrlBuilder.buildBackdropUrl(
        imageHost = imageHost,
        imagePath = selectedMovie.backdropUrl
    )

    val posterImageUrl = ImageUrlBuilder.buildPosterUrl(
        imageHost = imageHost,
        imagePath = selectedMovie.posterUrl
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(outerPadding),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            ) {
                AsyncImage(
                    model = backdropImageUrl,
                    contentDescription = selectedMovie.movieTitle,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if (!detailsBundle.youtubeTrailerKey.isNullOrBlank()) {
                    FilledIconButton(
                        onClick = {
                            onTrailerTap(detailsBundle.youtubeTrailerKey)
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(64.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play trailer",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                AsyncImage(
                    model = posterImageUrl,
                    contentDescription = selectedMovie.movieTitle,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 16.dp)
                        .offset(y = 60.dp)
                        .width(130.dp)
                        .height(190.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(60.dp))
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = selectedMovie.movieTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                val releaseYearLabel = selectedMovie.releaseDateYear?.toString() ?: "Unknown year"
                val durationLabel = selectedMovie.durationMinutes?.let { "$it min" } ?: "Unknown runtime"

                Text(
                    text = "$releaseYearLabel • $durationLabel",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "IMDb: %.1f".format(selectedMovie.imdbScore),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Votes: ${selectedMovie.imdbVotesCount}",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "TMDB: ${selectedMovie.tmdbScore?.let { "%.1f".format(it) } ?: "N/A"}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        if (detailsBundle.movieGenres.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Genres",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        detailsBundle.movieGenres.forEach { genreItem ->
                            Text(
                                text = genreItem.genreName,
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(MaterialTheme.colorScheme.secondaryContainer)
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Overview",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = selectedMovie.movieOverview.ifBlank { "No overview available." },
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Info",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        InfoChip(
                            label = "Budget",
                            value = formatMoney(selectedMovie.productionBudget)
                        )
                    }
                    item {
                        InfoChip(
                            label = "Revenue",
                            value = formatMoney(selectedMovie.boxOfficeRevenue)
                        )
                    }
                    item {
                        InfoChip(
                            label = "Language",
                            value = selectedMovie.languageCode ?: "N/A"
                        )
                    }
                    item {
                        InfoChip(
                            label = "Popularity",
                            value = "%.1f".format(selectedMovie.trendingScore)
                        )
                    }
                }
            }
        }

        if (detailsBundle.backdropImages.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Images",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(detailsBundle.backdropImages.take(3)) { backdropPath ->
                            val fullBackdropUrl = ImageUrlBuilder.buildBackdropUrl(
                                imageHost = imageHost,
                                imagePath = backdropPath
                            )

                            Card(
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                            ) {
                                AsyncImage(
                                    model = fullBackdropUrl,
                                    contentDescription = "Movie image",
                                    modifier = Modifier
                                        .width(220.dp)
                                        .height(130.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }

        if (detailsBundle.movieCast.isNotEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Top Cast",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    detailsBundle.movieCast.take(10).forEach { actorItem ->
                        CastItem(
                            castMember = actorItem,
                            imageHost = imageHost
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoChip(
    label: String,
    value: String
) {
    ElevatedCard(
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun CastItem(
    castMember: CastMember,
    imageHost: String
) {
    val profileImageUrl = ImageUrlBuilder.buildProfileUrl(
        imageHost = imageHost,
        imagePath = castMember.imagePath
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = castMember.fullName,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = castMember.fullName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = castMember.roleName ?: "Unknown role",
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun formatMoney(amount: Long?): String {
    if (amount == null || amount <= 0L) return "N/A"
    return "$%,d".format(amount)
}