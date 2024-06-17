package dev.nstv.sheeptransitions.ui.screen

import dev.nstv.composablesheep.library.model.Sheep

data class ScreenItem(
    val title: String,
    val sheep: Sheep,
    val screenType: ScreenType,
) {
    val sheepComponentKey = SharedComponentKey(SharedComponentType.Sheep, title)
    val titleComponentKey = SharedComponentKey(SharedComponentType.Title, title)
    val boundsComponentKey = SharedComponentKey(SharedComponentType.Bounds, title)
}

enum class ScreenType {
    Selector,
    SheepDetail,
}

enum class SharedComponentType {
    Sheep,
    Title,
    Bounds,
}

data class SharedComponentKey(
    val type: SharedComponentType,
    val id: String,
)