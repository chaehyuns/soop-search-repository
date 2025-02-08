package com.soop.repository.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.soop.repository.presentation.main.MainScreen
import com.soop.repository.presentation.repositorydetail.RepositoryDetailScreen

@Composable
fun RepositoryNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRoutes.REPOSITORY_LIST) {
        composable(NavRoutes.REPOSITORY_LIST) {
            MainScreen(
                onRepositoryClick = { owner, repo ->
                    navController.navigate("${NavRoutes.REPOSITORY_DETAIL}/$owner/$repo")
                }
            )
        }
        composable(
            route = "${NavRoutes.REPOSITORY_DETAIL}/{${NavArgs.OWNER}}/{${NavArgs.REPO}}",
            arguments = listOf(
                navArgument(NavArgs.OWNER) { type = NavType.StringType },
                navArgument(NavArgs.REPO) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val owner = backStackEntry.arguments?.getString(NavArgs.OWNER) ?: ""
            val repo = backStackEntry.arguments?.getString(NavArgs.REPO) ?: ""
            RepositoryDetailScreen(owner = owner, repo = repo)
        }
    }
}
