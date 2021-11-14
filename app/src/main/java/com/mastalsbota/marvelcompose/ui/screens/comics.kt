package com.mastalsbota.marvelcompose.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import coil.annotation.ExperimentalCoilApi
import com.mastalsbota.marvelcompose.data.entities.Comic
import com.mastalsbota.marvelcompose.data.repositories.ComicsRepository
import com.mastalsbota.marvelcompose.ui.screens.common.MarvelItemDetailScreen
import com.mastalsbota.marvelcompose.ui.screens.common.MarvelItemsListScreen

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun ComicsScreen(onClick: (Comic) -> Unit) {
    var comicsState by remember() { mutableStateOf(emptyList<Comic>()) }
    LaunchedEffect(Unit) {
        comicsState = ComicsRepository.get()
    }
    MarvelItemsListScreen(
        items = comicsState,
        onClick = onClick
    )
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ComicDetailScreen(comicId: Int, onUpClick: () -> Unit) {
    var comicState by remember { mutableStateOf<Comic?>(null) }
    LaunchedEffect(Unit) {
        comicState = ComicsRepository.find(comicId)
    }
    comicState?.let {
        MarvelItemDetailScreen(it, onUpClick)
    }
}
