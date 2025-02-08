package com.soop.repository.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soop.repository.presentation.profile.component.ProfileInfo
import com.soop.repository.presentation.profile.component.ProfileStatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    imageUrl: String,
    ownerName: String,
    followers: String,
    following: String,
    languages: String,
    repositories: String,
    bio: String
) {
    ModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            ProfileContent(
                imageUrl = imageUrl,
                ownerName = ownerName,
                followers = followers,
                following = following,
                languages = languages,
                repositories = repositories,
                bio = bio
            )
        }
    }
}

@Composable
fun ProfileContent(
    imageUrl: String,
    ownerName: String,
    followers: String,
    following: String,
    languages: String,
    repositories: String,
    bio: String
) {
    Column {
        Spacer(modifier = Modifier.height(10.dp))

        ProfileInfo(
            imageUrl = imageUrl,
            ownerName = ownerName
        )

        Spacer(modifier = Modifier.height(10.dp))

        ProfileStatItem(label = "Followers", value = followers)

        ProfileStatItem(label = "Following", value = following)

        ProfileStatItem(label = "Languages", value = languages)

        ProfileStatItem(label = "Repositories", value = repositories)

        ProfileStatItem(label = "Bio", value = bio)

        Spacer(modifier = Modifier.height(10.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewProfileContent() {
    ProfileContent(
        imageUrl = "https://avatars.githubusercontent.com/u/3650029?v=4",
        ownerName = "chaehyuns",
        followers = "954",
        following = "0",
        languages = "Java, Kotlin, Swift, Python, JavaScript, TypeScript",
        repositories = "168",
        bio = "ownCloud, a Kiteworks Company, offers file sharing and collaboration trusted by 200+ million users worldwide regardless of device or location."
    )
}
