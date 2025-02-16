package com.soop.repository.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.soop.repository.R
import com.soop.repository.presentation.main.component.RepositoryItem
import com.soop.repository.presentation.main.model.RepositoryUiState
import com.soop.repository.presentation.ui.component.EmptyScreen
import com.soop.repository.presentation.ui.component.ErrorScreen
import com.soop.repository.presentation.ui.component.LoadingScreen
import com.soop.repository.presentation.ui.component.SearchBar

@Composable
fun MainScreen(
    query: String,
    uiState: RepositoryUiState,
    onSearchTextChanged: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    onRetry: () -> Unit,
    onRepositoryClick: (owner: String, repo: String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            searchText = query,
            onSearchTextChanged = onSearchTextChanged,
            onSearch = onSearch,
            onClear = onClear
        )
        Spacer(modifier = Modifier.height(8.dp))

        RepositoryContent(uiState = uiState, onRetry = onRetry, onRepositoryClick = onRepositoryClick)
    }
}

@Composable
fun RepositoryContent(
    uiState: RepositoryUiState,
    onRetry: () -> Unit,
    onRepositoryClick: (owner: String, repo: String) -> Unit
) {
    when (uiState) {
        is RepositoryUiState.Initial -> EmptyScreen(message = stringResource(id = R.string.search_hint))
        is RepositoryUiState.Loading -> LoadingScreen()
        is RepositoryUiState.Success -> RepositoryList(uiState, onRepositoryClick)
        is RepositoryUiState.Error -> ErrorScreen(
            message = uiState.message ?: stringResource(id = R.string.error_repository),
            onRetry = onRetry
        )
    }
}

@Composable
fun RepositoryList(uiState: RepositoryUiState.Success, onRepositoryClick: (owner: String, repo: String) -> Unit) {
    val items = uiState.data.collectAsLazyPagingItems()

    when (val state = items.loadState.refresh) {
        is LoadState.Loading -> LoadingScreen()
        is LoadState.Error -> ErrorScreen(
            message = state.error.message ?: stringResource(id = R.string.error_repository),
            onRetry = { items.refresh() }
        )
        else -> {
            if (items.itemCount == 0) {
                EmptyScreen(message = stringResource(id = R.string.empty_repository))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        count = items.itemCount,
                        key = { index -> items[index]?.id ?: index }
                    ) { index ->
                        items[index]?.let { item ->
                            RepositoryItem(item = item, onClick = {
                                onRepositoryClick(item.ownerLoginName, item.repositoryName)
                            })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        query = "soop",
        uiState = RepositoryUiState.Loading,
        onSearchTextChanged = {},
        onSearch = {},
        onClear = {},
        onRetry = {},
        onRepositoryClick = { _, _ -> }
    )
}
