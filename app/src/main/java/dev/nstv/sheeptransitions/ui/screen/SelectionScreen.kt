@file:OptIn(ExperimentalSharedTransitionApi::class)

package dev.nstv.sheeptransitions.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateInt
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.nstv.composablesheep.library.ComposableSheep
import dev.nstv.composablesheep.library.model.Sheep
import dev.nstv.composablesheep.library.util.SheepColor
import dev.nstv.sheeptransitions.ui.screen.ScreenType.SheepDetail

enum class SharedComponentType {
    Sheep,
    Title,
}

data class SharedComponentKey(
    val type: SharedComponentType,
    val id: String,
)

enum class ScreenType {
    Selector,
    SheepDetail,
}

data class ScreenItem(
    val title: String,
    val sheep: Sheep,
    val screenType: ScreenType,
) {
    val SheepComponentKey = SharedComponentKey(SharedComponentType.Sheep, title)
    val TitleComponentKey = SharedComponentKey(SharedComponentType.Title, title)
}

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

    val textSize = sharedAnimationScope.transition.animateInt(label = "titlesize") {
        if (it == EnterExitState.Visible) 16 else 26
    }

    val textColor = sharedAnimationScope.transition.animateColor(label = "textColor") {
        if (it == EnterExitState.Visible) Color.Black else screenItem.sheep.fluffColor
    }

    Card(
        modifier
            .clickable { onClick() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            with(sharedTransitionScope) {
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
                    textAlign = TextAlign.Center,
                    fontSize = textSize.value.sp,
                    color = textColor.value,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = screenItem.TitleComponentKey),
                            animatedVisibilityScope = sharedAnimationScope,
                        )
                        .fillMaxWidth(),
                )
            }
        }
    }
}


@Preview
@Composable
private fun SelectionScreenPreview() {
    SelectionScreen()
}