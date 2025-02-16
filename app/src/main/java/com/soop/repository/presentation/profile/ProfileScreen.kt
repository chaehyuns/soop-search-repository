package com.soop.repository.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.soop.repository.R
import com.soop.repository.presentation.profile.component.ProfileInfo
import com.soop.repository.presentation.profile.component.ProfileStatItem
import com.soop.repository.presentation.profile.model.ProfileUiModel
import com.soop.repository.presentation.profile.model.ProfileUiState
import com.soop.repository.presentation.ui.component.ErrorScreen
import com.soop.repository.presentation.ui.component.LoadingScreen

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    username: String,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(username) {
        viewModel.fetchUserProfile(username)
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 300.dp)
    ) {
        when (uiState) {
            is ProfileUiState.Loading -> LoadingScreen(modifier = modifier)

            is ProfileUiState.Success -> ProfileContent(uiState.data)

            is ProfileUiState.Error -> ErrorScreen(
                modifier = modifier,
                message = uiState.message ?: stringResource(id = R.string.error_profile_load)
            ) {
                viewModel.fetchUserProfile(username)
            }
        }
    }
}

@Composable
fun ProfileContent(
    data: ProfileUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
    ) {
        ProfileInfo(
            imageUrl = data.avatarUrl,
            ownerName = data.loginName
        )

        Spacer(modifier = Modifier.height(15.dp))

        ProfileStatItem(label = stringResource(id = R.string.followers), value = data.followers)

        ProfileStatItem(label = stringResource(id = R.string.following), value = data.following)

        ProfileStatItem(label = stringResource(id = R.string.languages), value = data.languages)

        ProfileStatItem(
            label = stringResource(id = R.string.repositories),
            value = data.publicRepos
        )

        ProfileStatItem(label = stringResource(id = R.string.bio), value = data.bio)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileContent() {
    ProfileContent(
        data = ProfileUiModel(
            id = 1,
            avatarUrl = "https://avatars.githubusercontent.com/u/1?v=4",
            loginName = "Chaehyuns",
            publicRepos = "168",
            followers = "954",
            following = "0",
            languages = "Java, Kotlin, Swift, Python, JavaScript, TypeScript",
            bio = "ownCloud, a Kiteworks Company, offers file sharing and coll" +
                "aboration trusted by 200+ million users worldwide regardless of device or location."
        )
    )
}
