package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
    with(sharedTransitionScope) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .sharedBounds(
                    rememberSharedContentState(key = screenItem.BoundsComponentKey),
                    animatedVisibilityScope = sharedAnimationScope,
                    enter = fadeIn(tween(animationDurationMillis)),
                    exit = fadeOut(tween(animationDurationMillis)),
                    resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds(),
                )
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = screenItem.sheep.fluffColor.copy(alpha = 0.1f)
                )
                .clip(RoundedCornerShape(16.dp))
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ComposableSheep(
                modifier = Modifier
                    .size(300.dp)
                    .weight(1f)
                    .sharedElement(
                        rememberSharedContentState(key = screenItem.SheepComponentKey),
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
                        rememberSharedContentState(key = screenItem.TitleComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                        boundsTransform = arcBoundsTransform
                    )
            )
        }
    }
}