package com.soop.repository.presentation.profile.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soop.repository.presentation.ui.theme.AppBlack
import com.soop.repository.presentation.ui.theme.FontGray
import com.soop.repository.presentation.ui.theme.Typography

@Composable
fun ProfileStatItem(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 10.dp),
    label: String,
    value: String,
    spacerWidth: Int = 15,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            style = Typography.titleLarge,
            color = AppBlack()
        )

        Spacer(modifier = Modifier.width(spacerWidth.dp))

        Text(
            text = value,
            style = Typography.displayLarge,
            color = FontGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileStatItemPreview() {
    ProfileStatItem(
        label = "Followers",
        value = "100"
    )
}
