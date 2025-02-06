package com.soop.repository.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soop.repository.presentation.ui.theme.AppBlack

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    message: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = AppBlack(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    EmptyScreen(
        message = "아이템을 찾을 수 없습니다."
    )
}
