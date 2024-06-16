package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes


val animationDurationMillis = 500

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
val arcBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = animationDurationMillis
        initialBounds atFraction 0f using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds atFraction 1.0f
    }
}