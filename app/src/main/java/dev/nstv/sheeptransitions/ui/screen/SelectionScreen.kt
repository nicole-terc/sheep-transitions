@file:OptIn(ExperimentalSharedTransitionApi::class)

package dev.nstv.sheeptransitions.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nstv.composablesheep.library.ComposableSheep
import dev.nstv.composablesheep.library.model.Sheep
import dev.nstv.composablesheep.library.util.SheepColor
import dev.nstv.sheeptransitions.ui.screen.ScreenType.SheepDetail

@Composable
fun SelectionScreen(
    modifier: Modifier = Modifier,
) {
    val selectionScreenItem = ScreenItem(
        title = "Screen Selection",
        sheep = Sheep(fluffColor = SheepColor.Green),
        screenType = ScreenType.Selector,
    )

    val screens = remember {
        listOf(
            ScreenItem(
                title = "Green Sheep",
                sheep = Sheep(fluffColor = SheepColor.Green),
                screenType = SheepDetail,
            ),
            ScreenItem(
                title = "Purple sheep",
                sheep = Sheep(fluffColor = SheepColor.Purple),
                screenType = SheepDetail,
            ),
            ScreenItem(
                title = "Blue Sheep",
                sheep = Sheep(fluffColor = SheepColor.Blue),
                screenType = SheepDetail,
            ),
            ScreenItem(
                title = "Orange Sheep",
                sheep = Sheep(fluffColor = SheepColor.Orange),
                screenType = SheepDetail,
            ),
            ScreenItem(
                title = "Magenta Sheep",
                sheep = Sheep(fluffColor = SheepColor.Magenta),
                screenType = SheepDetail,
            ),
            ScreenItem(
                title = "Black Sheep",
                sheep = Sheep(fluffColor = SheepColor.Black, glassesColor = SheepColor.Fluff),
                screenType = SheepDetail,
            ),
        )
    }

    var selectedScreen by remember { mutableStateOf(selectionScreenItem) }


    Surface(modifier = modifier) {

        BackHandler(selectedScreen.screenType != ScreenType.Selector) {
            selectedScreen = selectionScreenItem
        }

        SharedTransitionLayout {

            AnimatedContent(
                targetState = selectedScreen,
                label = "Screen Selection Animation",
            ) { screenItem ->
                when (screenItem.screenType) {
                    ScreenType.Selector -> ViewSelectionScreen(
                        modifier = Modifier.fillMaxSize(),
                        screens = screens,
                        onItemClick = { selectedScreen = it },
                        sharedTransitionScope = this@SharedTransitionLayout,
                        sharedAnimationScope = this@AnimatedContent,
                    )

                    SheepDetail -> SheepDetailScreen(
                        screenItem = screenItem,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        sharedAnimationScope = this@AnimatedContent,
                    )
                }
            }
        }
    }

}

@Composable
fun ViewSelectionScreen(
    screens: List<ScreenItem>,
    onItemClick: (ScreenItem) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    sharedAnimationScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        modifier = modifier.padding(8.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(screens) { screenItem ->
            ScreenItemCard(
                screenItem = screenItem,
                onClick = { onItemClick(screenItem) },
                sharedTransitionScope = sharedTransitionScope,
                sharedAnimationScope = sharedAnimationScope,
            )
        }
    }

}

@Composable
fun ScreenItemCard(
    screenItem: ScreenItem,
    onClick: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    sharedAnimationScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
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
                    color = MaterialTheme.colorScheme.surfaceVariant,
                )
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClick,
                )
                .padding(8.dp)

        ) {
            ComposableSheep(
                sheep = screenItem.sheep,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .sharedElement(
                        rememberSharedContentState(key = screenItem.SheepComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                    ),
            )
            Text(
                text = screenItem.title,
                fontSize = 16.sp,
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = screenItem.TitleComponentKey),
                        animatedVisibilityScope = sharedAnimationScope,
                        boundsTransform = arcBoundsTransform,
                    )
            )
        }
    }
}


@Preview
@Composable
private fun SelectionScreenPreview() {
    SelectionScreen()
}