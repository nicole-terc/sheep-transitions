package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.nstv.composablesheep.library.ComposableSheep

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SheepDetailScreen(
    screenItem: ScreenItem,
    sharedTransitionScope: SharedTransitionScope,
    sharedAnimationScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        Box(modifier = modifier.fillMaxSize()) {
            ComposableSheep(
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.Center)
                    .sharedElement(
                        rememberSharedContentState(key = screenItem.SheepComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                    ),
                sheep = screenItem.sheep,
            )
        }
    }
}