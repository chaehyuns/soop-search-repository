package com.soop.repository.presentation.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.soop.repository.R
import com.soop.repository.presentation.ui.theme.AppBlack
import com.soop.repository.presentation.ui.theme.FontGray
import com.soop.repository.presentation.ui.theme.Typography

@Composable
fun RepositoryItem(
    avatarUrl: String,
    loginName: String,
    repoName: String,
    repoDescription: String,
    starCount: Int,
    language: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(30.dp)
                    .clip(RoundedCornerShape(60.dp))
                    .background(Color.LightGray),
                model = avatarUrl,
                contentDescription = stringResource(id = R.string.avatar),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_default)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = loginName,
                color = FontGray,
                style = Typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = repoName,
            color = AppBlack(),
            style = Typography.titleLarge
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = repoDescription,
            color = AppBlack(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = Typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(7.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Star Icon",
                contentScale = ContentScale.Crop

            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = starCount.toString(),
                color = AppBlack(),
                style = Typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.size(13.dp),
                painter = painterResource(id = R.drawable.ic_language),
                contentDescription = "Language Icon",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = language,
                color = AppBlack(),
                style = Typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepositoryItemPreview() {
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
