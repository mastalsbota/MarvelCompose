package com.mastalsbota.marvelcompose.ui.screens.common

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ShareCompat
import com.mastalsbota.marvelcompose.data.entities.MarvelItem
import com.mastalsbota.marvelcompose.data.entities.Url

@ExperimentalMaterialApi
@Composable
fun MarvelItemDetailScaffold(
    marvelItem: MarvelItem,
    content: @Composable (PaddingValues) -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        floatingActionButton = {
            if (marvelItem.urls.isNotEmpty()) {
                FloatingActionButton(
                    onClick = { shareCharacter(context, marvelItem.title, marvelItem.urls.first()) }
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = null)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape
            ) {
                AppBarIcon(imageVector = Icons.Default.Menu, onClick = { })
                Spacer(modifier = Modifier.weight(1f))
                AppBarIcon(imageVector = Icons.Default.Favorite, onClick = { })
            }
        },
        content = content
    )
}

private fun shareCharacter(context: Context, name: String, url: Url) {
    val intent = ShareCompat
        .IntentBuilder(context)
        .setType("text/plain")
        .setSubject(name)
        .setText(url.destination)
        .intent
    context.startActivity(intent)
}
