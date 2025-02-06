package com.soop.repository.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soop.repository.presentation.main.component.RepositoryItem
import com.soop.repository.presentation.ui.component.SearchBar

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp),
            searchText = "",
            onSearchTextChanged = {},
            onSearch = {}
        )

        RepositoryItem(
            avatarUrl = "https://avatars",
            loginName = "chaehyun",
            repoName = "SOOP 과제",
            repoDescription = "soop 과제 repository의 설명 soop 과제 repository의 설명 " +
                "soop 과제 repository의 설명 soop 과제 repository의 설명 soop 과제 repository의 설명",
            starCount = 9999,
            language = "Kotlin"
        )

        RepositoryItem(
            avatarUrl = "https://avatars",
            loginName = "chaehyun",
            repoName = "SOOP 과제",
            repoDescription = "soop 과제 repository의 설명 soop 과제 repository의 설명 " +
                "soop 과제 repository의 설명 soop 과제 repository의 설명 soop 과제 repository의 설명",
            starCount = 9999,
            language = "Kotlin"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
