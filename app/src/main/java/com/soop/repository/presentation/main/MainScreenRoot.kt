package com.soop.repository.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: RepositoryViewModel = hiltViewModel(),
    onRepositoryClick: (owner: String, repo: String) -> Unit = { _, _ -> }
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MainScreen(
        modifier = modifier,
        query = query,
        uiState = uiState,
        onSearchTextChanged = viewModel::updateQuery,
        onSearch = { viewModel.searchRepositories(query) },
        onClear = { viewModel.updateQuery("") },
        onRetry = { viewModel.searchRepositories(query) },
        onRepositoryClick = onRepositoryClick
    )
}
