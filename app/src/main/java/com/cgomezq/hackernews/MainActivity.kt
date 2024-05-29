package com.cgomezq.hackernews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cgomezq.hackernews.common.ui.theme.HackerNewsTheme
import com.cgomezq.hackernews.news.presentation.logic.NewsViewModel
import com.cgomezq.hackernews.news.presentation.ui.NewsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackerNewsTheme {
                val viewmodel: NewsViewModel = viewModel()
                val state = viewmodel.state.collectAsStateWithLifecycle().value
                NewsScreen(state, viewmodel::emitIntent)
            }
        }
    }
}