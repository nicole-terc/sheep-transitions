package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateInt
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nstv.composablesheep.library.ComposableSheep

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SheepDetailScreen(
    screenItem: ScreenItem,
    sharedTransitionScope: SharedTransitionScope,
    sharedAnimationScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {

    val textSize = sharedAnimationScope.transition.animateInt(label = "titlesize") {
        if (it == EnterExitState.Visible) 26 else 16
    }

    val textColor = sharedAnimationScope.transition.animateColor(label = "textColor") {
        if (it == EnterExitState.Visible) screenItem.sheep.fluffColor else Color.Black
    }


    with(sharedTransitionScope) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = screenItem.title,
                textAlign = TextAlign.Center,
                fontSize = textSize.value.sp,
                color = textColor.value,
                modifier = Modifier
                    .padding(16.dp)
                    .sharedElement(
                        rememberSharedContentState(key = screenItem.TitleComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                    )
                    .fillMaxWidth(),
            )
            ComposableSheep(
                modifier = Modifier
                    .size(300.dp)
                    .weight(1f)
                    .sharedElement(
                        rememberSharedContentState(key = screenItem.SheepComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                    ),
                sheep = screenItem.sheep,
            )
        }
    }
}