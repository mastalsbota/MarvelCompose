package com.mastalsbota.marvelcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.mastalsbota.marvelcompose.ui.MarvelApp

@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarvelApp()
        }
    }
}
