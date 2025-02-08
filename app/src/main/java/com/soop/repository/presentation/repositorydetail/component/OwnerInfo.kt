package com.soop.repository.presentation.repositorydetail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.soop.repository.R
import com.soop.repository.presentation.ui.theme.AppBlack
import com.soop.repository.presentation.ui.theme.Soop
import com.soop.repository.presentation.ui.theme.Typography

@Composable
fun OwnerInfo(
    modifier: Modifier = Modifier.fillMaxWidth(),
    imageUrl: String,
    ownerName: String,
    onMoreClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(80.dp)),
            model = imageUrl,
            contentDescription = "Image",
            placeholder = painterResource(id = R.drawable.ic_default),
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            modifier = Modifier.weight(1f),
            text = ownerName,
            style = Typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = AppBlack()
        )

        Button(
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
            onClick = onMoreClick,
            colors = ButtonDefaults.buttonColors(Soop)
        ) {
            Text(text = "more")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OwnerInfoPreview() {
    OwnerInfo(
        imageUrl = "https://avatars.githubusercontent.com/u/3650029?v=4",
        ownerName = "chaehyuns",
        onMoreClick = { /* More 버튼 클릭 로직 */ }
    )
}
