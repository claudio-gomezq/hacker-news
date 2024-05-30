package com.cgomezq.hackernews

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cgomezq.hackernews.news.presentation.logic.NewsViewModel
import com.cgomezq.hackernews.news.presentation.ui.NewsScreen
import com.cgomezq.hackernews.news.presentation.ui.PostScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MainNavHost(
    navController: NavHostController
) {
    NavHost(
        startDestination = "news",
        navController = navController
    ) {
        composable(route = "news") {
            val viewmodel: NewsViewModel = hiltViewModel()
            val state = viewmodel.state.collectAsStateWithLifecycle().value
            NewsScreen(
                state = state,
                emitIntent = viewmodel::emitIntent,
                navigateToPost = { title, link ->
                    val encodedUrl = URLEncoder.encode(link, StandardCharsets.UTF_8.toString())
                    navController.navigate("post/${encodedUrl}/${title}")
                }
            )
        }
        composable(
            route = "post/{link}/{title}",
            arguments = listOf(
                navArgument("link") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("link") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            PostScreen(
                link = url,
                title = title,
                navigateBack = { navController.navigateUp() }
            )
        }
    }
}