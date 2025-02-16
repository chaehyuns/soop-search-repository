package com.soop.repository.presentation.repositorydetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soop.repository.presentation.repositorydetail.model.RepositoryDetailUiModel
import com.soop.repository.presentation.repositorydetail.model.RepositoryDetailUiState

@Composable
fun RepositoryDetailScreenRoot(
    modifier: Modifier = Modifier,
    owner: String,
    repo: String,
    viewModel: RepositoryDetailViewModel = hiltViewModel(),
    onMoreClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(owner, repo) {
        viewModel.fetchRepositoryDetail(owner, repo)
    }

    RepositoryDetailScreen(
        modifier = modifier,
        uiState = uiState,
        onRetry = { viewModel.fetchRepositoryDetail(owner, repo) },
        onMoreClick = onMoreClick
    )
}

@Preview(showBackground = true)
@Composable
fun RepositoryDetailScreenRootPreview() {
    RepositoryDetailScreen(
        uiState = RepositoryDetailUiState.Success(
            RepositoryDetailUiModel(
                id = 1,
                repositoryName = "Repository Name",
                repositoryUrl = "https://avatars.github",
                stars = "100",
                watchers = "50",
                forks = "20",
                description = "Description",
                ownerLoginName = "chaehyun",
                ownerAvatarUrl = "https://github.com"
            )
        ),
        onRetry = {},
        onMoreClick = {}
    )

    RepositoryDetailScreen(
        uiState = RepositoryDetailUiState.Loading,
        onRetry = {},
        onMoreClick = {}
    )
}
