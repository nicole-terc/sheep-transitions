package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nstv.composablesheep.library.ComposableSheep

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        ComposableSheep(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}