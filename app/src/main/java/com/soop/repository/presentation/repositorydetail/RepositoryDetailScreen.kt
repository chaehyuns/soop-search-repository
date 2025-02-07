package com.soop.repository.presentation.repositorydetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soop.repository.presentation.repositorydetail.component.LinkUrl
import com.soop.repository.presentation.repositorydetail.component.OwnerInfo
import com.soop.repository.presentation.repositorydetail.component.RepositoryStats
import com.soop.repository.presentation.ui.theme.AppBlack
import com.soop.repository.presentation.ui.theme.Typography

@Composable
fun RepositoryDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
    ) {
        Text(
            text = "android",
            style = Typography.headlineLarge,
            color = AppBlack()
        )

        Spacer(modifier = Modifier.height(10.dp))

        LinkUrl(linkUrl = "https://github.com/soop")

        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        RepositoryStats(stars = "3.9k", watchers = "3.9k", forks = "3.1k")

        HorizontalDivider(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            color = Color.LightGray,
            thickness = 1.dp
        )

        OwnerInfo(
            imageUrl = "https://avatars.githubusercontent.com/u/3650029?v=4",
            repoName = "owncloud",
            onMoreClick = { /* More 버튼 클릭 로직 */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "📱 The ownCloud Android App",
            style = Typography.bodyLarge,
            color = AppBlack()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryDetailScreenPreview() {
    RepositoryDetailScreen()
}
