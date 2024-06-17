package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nstv.composablesheep.library.ComposableSheep

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
@Composable
fun SheepDetailScreen(
    screenItem: ScreenItem,
    sharedTransitionScope: SharedTransitionScope,
    sharedAnimationScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    val sheepDescription =
        "This is a composable sheep, it goes 'bah!', but also 'baah bah' when it gets angry. \nIt has a very fluffly fluff, really cool glasses and tiny cute little legs. Don't tell it that though, it's very proud."

    with(sharedTransitionScope) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .sharedBounds(
                    rememberSharedContentState(key = screenItem.boundsComponentKey),
                    animatedVisibilityScope = sharedAnimationScope,
                    enter = fadeIn(tween(animationDurationMillis)) + slideInVertically(
                        animationSpec = tween(animationDurationMillis),
                        initialOffsetY = { it },
                    ),
                    exit = fadeOut(tween(animationDurationMillis)) + slideOutVertically(
                        animationSpec = tween(animationDurationMillis),
                        targetOffsetY = { it },
                    ),
                    boundsTransform = slideOutDetailsBoundsTransform,
                )
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = screenItem.sheep.fluffColor.copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ComposableSheep(
                modifier = Modifier
                    .size(300.dp)
                    .weight(1f)
                    .sharedElement(
                        rememberSharedContentState(key = screenItem.sheepComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                        boundsTransform = arcBoundsTransform,
                    ),
                sheep = screenItem.sheep,
            )
            Text(
                text = screenItem.title,
                color = screenItem.sheep.fluffColor,
                fontSize = 26.sp,
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = screenItem.titleComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                        boundsTransform = arcBoundsTransform
                    )
            )

            Text(
                text = sheepDescription,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(color = screenItem.sheep.fluffColor.copy(alpha = 0.5f))
                    .weight(2f)
                    .padding(16.dp)
            )
        }
    }
}

