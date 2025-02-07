package com.soop.repository.presentation.repositorydetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RepositoryStats(
    modifier: Modifier = Modifier.fillMaxWidth(),
    stars: String,
    watchers: String,
    forks: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatsItem(label = "Star", value = stars)
        StatsItem(label = "Watchers", value = watchers)
        StatsItem(label = "Forks", value = forks)
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryStatsPreview() {
    RepositoryStats(stars = "3.9k", watchers = "3.9k", forks = "3.1k")
}
