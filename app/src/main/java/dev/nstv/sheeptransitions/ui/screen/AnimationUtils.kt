package dev.nstv.sheeptransitions.ui.screen

import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.ui.geometry.Rect


const val animationDurationMillis = 700

@OptIn(ExperimentalAnimationSpecApi::class, ExperimentalSharedTransitionApi::class)
val arcBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    keyframes {
        durationMillis = animationDurationMillis
        initialBounds atFraction 0f using ArcMode.ArcBelow using FastOutSlowInEasing
        targetBounds atFraction 1.0f
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
val slideOutDetailsBoundsTransform = BoundsTransform { initialBounds, targetBounds ->
    if (targetBounds.size.width < initialBounds.size.width) {
        keyframes {
            durationMillis = animationDurationMillis
            Rect(
                top = targetBounds.top,
                bottom = targetBounds.bottom,
                left = initialBounds.left,
                right = initialBounds.right,
            ) atFraction 1.0f
        }
    } else {
        spring(
            stiffness = Spring.StiffnessMediumLow,
            visibilityThreshold = Rect.VisibilityThreshold
        )
    }
}
