package com.soop.repository.presentation.repositorydetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soop.repository.presentation.ui.theme.AppBlack
import com.soop.repository.presentation.ui.theme.Typography

@Composable
fun StatsItem(
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            style = Typography.titleLarge,
            color = AppBlack()
        )
        Text(
            modifier = Modifier.padding(top = 3.dp),
            text = value,
            style = Typography.bodyLarge,
            color = AppBlack()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatsItemPreview() {
    StatsItem(label = "Star", value = "100")
}
