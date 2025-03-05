package com.soop.repository.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soop.repository.presentation.profile.model.ProfileUiModel
import com.soop.repository.presentation.profile.model.ProfileUiState

@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    username: String,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(username) {
        viewModel.fetchUserProfile(username)
    }

    ProfileScreen(
        modifier = modifier,
        uiState = uiState,
        onRetry = { viewModel.fetchUserProfile(username) }
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenRootPreview() {
    Column {
        ProfileScreen(
            uiState = ProfileUiState.Success(
                ProfileUiModel(
                    id = 1,
                    avatarUrl = "https://avatars.github",
                    loginName = "chaehyun",
                    publicRepos = "10",
                    followers = "20000",
                    following = "30",
                    bio = "Android Developer",
                    languages = "Kotlin, Java"
                )
            ),
            onRetry = {}
        )

        ProfileScreen(uiState = ProfileUiState.Loading, onRetry = {})

        ProfileScreen(uiState = ProfileUiState.Error(message = "에러 에러"), onRetry = {})
    }
}
