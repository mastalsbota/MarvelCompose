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
import com.mastalsbota.marvelcompose.data.entities.Character
import com.mastalsbota.marvelcompose.data.repositories.CharactersRepository
import com.mastalsbota.marvelcompose.ui.screens.common.MarvelItemDetailScreen
import com.mastalsbota.marvelcompose.ui.screens.common.MarvelItemsListScreen

@ExperimentalCoilApi
@ExperimentalFoundationApi
@Composable
fun CharactersScreen(onClick: (Character) -> Unit) {
    var charactersState by remember() { mutableStateOf(emptyList<Character>()) }
    LaunchedEffect(Unit) {
        charactersState = CharactersRepository.get()
    }
    MarvelItemsListScreen(
        items = charactersState,
        onClick = onClick
    )
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun CharacterDetailScreen(characterId: Int, onUpClick: () -> Unit) {
    var characterState by remember { mutableStateOf<Character?>(null) }
    LaunchedEffect(Unit) {
        characterState = CharactersRepository.find(characterId)
    }
    characterState?.let {
        MarvelItemDetailScreen(it, onUpClick)
    }
}
