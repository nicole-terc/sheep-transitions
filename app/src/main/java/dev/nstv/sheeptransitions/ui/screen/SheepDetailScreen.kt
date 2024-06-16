package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.nstv.composablesheep.library.ComposableSheep
import dev.nstv.composablesheep.library.model.Sheep

@Composable
fun SheepDetailScreen(
    sheep: Sheep,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        ComposableSheep(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center),
            sheep = sheep,
        )
    }
}