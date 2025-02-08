package com.soop.repository.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.soop.repository.presentation.main.MainScreen
import com.soop.repository.presentation.navigation.RepositoryNavGraph
import com.soop.repository.presentation.ui.theme.SearchRepositoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchRepositoryTheme {
                RepositoryNavGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SearchRepositoryTheme {
        MainScreen()
    }
}
