package dev.nstv.sheeptransitions.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.nstv.composablesheep.library.ComposableSheep
import dev.nstv.composablesheep.library.model.Sheep
import dev.nstv.composablesheep.library.util.SheepColor
import dev.nstv.sheeptransitions.ui.screen.ScreenType.SheepDetail

enum class ScreenType {
    Selector,
    SheepDetail,
}

data class ScreenItem(
    val title: String,
    val sheep: Sheep,
    val screenType: ScreenType,
)

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

        AnimatedContent(
            targetState = selectedScreen,
            label = "Screen Selection Animation",
        ) { screen ->
            when (screen.screenType) {
                ScreenType.Selector -> ViewSelectionScreen(
                    modifier = Modifier.fillMaxSize(),
                    screens = screens,
                    onItemClick = { selectedScreen = it },
                )

                SheepDetail -> SheepDetailScreen(
                    sheep = screen.sheep,
                )
            }
        }
    }

}

@Composable
fun ViewSelectionScreen(
    modifier: Modifier = Modifier,
    screens: List<ScreenItem>,
    onItemClick: (ScreenItem) -> Unit,
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
            )
        }
    }

}

@Composable
fun ScreenItemCard(
    screenItem: ScreenItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier
            .clickable { onClick() }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
//            if (screenItem.brush != null) {
//                ComposableSheep(
//                    sheep = screenItem.sheep,
//                    fluffBrush = screenItem.brush,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(1f),
//                )
//            } else {
            ComposableSheep(
                sheep = screenItem.sheep,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
//            }
            Text(
                text = screenItem.title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}


@Preview
@Composable
private fun SelectionScreenPreview() {
    SelectionScreen()
}