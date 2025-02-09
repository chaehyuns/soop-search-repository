package com.soop.repository.presentation.repositorydetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.soop.repository.R
import com.soop.repository.presentation.repositorydetail.component.LinkUrl
import com.soop.repository.presentation.repositorydetail.component.OwnerInfo
import com.soop.repository.presentation.repositorydetail.component.RepositoryStats
import com.soop.repository.presentation.repositorydetail.model.RepositoryDetailUiModel
import com.soop.repository.presentation.repositorydetail.model.RepositoryDetailUiState
import com.soop.repository.presentation.ui.component.ErrorScreen
import com.soop.repository.presentation.ui.component.LoadingScreen
import com.soop.repository.presentation.ui.theme.AppBlack
import com.soop.repository.presentation.ui.theme.Typography

@Composable
fun RepositoryDetailScreen(
    owner: String,
    repo: String,
    viewModel: RepositoryDetailViewModel = hiltViewModel(),
    onMoreClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(key1 = owner, key2 = repo) {
        viewModel.fetchRepositoryDetail(owner, repo)
    }

    when (uiState) {
        is RepositoryDetailUiState.Loading -> LoadingScreen()

        is RepositoryDetailUiState.Success -> RepositoryDetailContent(
            data = uiState.data,
            onMoreClick = onMoreClick
        )

        is RepositoryDetailUiState.Error -> ErrorScreen(
            message = uiState.message ?: stringResource(id = R.string.error_repository_detail_load)
        ) {
            viewModel.fetchRepositoryDetail(owner, repo)
        }
    }
}

@Composable
fun RepositoryDetailContent(
    data: RepositoryDetailUiModel,
    onMoreClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = data.repositoryName,
            style = Typography.headlineLarge,
            color = AppBlack()
        )

        Spacer(modifier = Modifier.height(10.dp))

        LinkUrl(linkUrl = data.repositoryUrl)

        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        RepositoryStats(stars = data.stars, watchers = data.watchers, forks = data.forks)

        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        OwnerInfo(
            imageUrl = data.ownerAvatarUrl,
            ownerName = data.ownerLoginName,
            onMoreClick = onMoreClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = data.description,
            style = Typography.bodyLarge,
            color = AppBlack()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryDetailScreenPreview() {
    RepositoryDetailContent(
        data = RepositoryDetailUiModel(
            id = 1L,
            repositoryName = "Android",
            repositoryUrl = "https://github.com/chaehyuns/soop-search-repository",
            stars = "3.9k",
            watchers = "3.9k",
            forks = "3.1k",
            description = "Soop Android App",
            ownerAvatarUrl = "https://avatars.com/u/12345678?v=4",
            ownerLoginName = "chaehyuns"
        ),
        onMoreClick = { }
    )
}
