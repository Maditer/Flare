package dev.dimension.flare.ui.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowsRotate
import compose.icons.fontawesomeicons.solid.ChevronDown
import compose.icons.fontawesomeicons.solid.Plus
import compose.icons.fontawesomeicons.solid.Sliders
import dev.dimension.flare.LocalWindowPadding
import dev.dimension.flare.Res
import dev.dimension.flare.data.datastore.model.TimelineAutoRefreshInterval
import dev.dimension.flare.data.model.TimelineDisplayMode
import dev.dimension.flare.data.model.tab.resolveTimelineAppearance
import dev.dimension.flare.model.AccountType
import dev.dimension.flare.refresh
import dev.dimension.flare.tab_settings_add_tab
import dev.dimension.flare.ui.component.FAIcon
import dev.dimension.flare.ui.component.FlareScrollBar
import dev.dimension.flare.ui.component.LocalAppSettings
import dev.dimension.flare.ui.component.LocalGlobalAppearance
import dev.dimension.flare.ui.component.LocalTimelineAppearance
import dev.dimension.flare.ui.component.platform.LocalWindowSizeClass
import dev.dimension.flare.ui.component.platform.WindowSizeClass
import dev.dimension.flare.ui.component.platform.isBigScreen
import dev.dimension.flare.ui.model.map
import dev.dimension.flare.ui.model.onSuccess
import dev.dimension.flare.ui.model.takeSuccess
import dev.dimension.flare.ui.presenter.HomeTimelineWithTabsPresenter
import dev.dimension.flare.ui.presenter.home.CanComposePresenter
import dev.dimension.flare.ui.presenter.home.DeepLinkPresenter
import dev.dimension.flare.ui.presenter.invoke
import dev.dimension.flare.ui.presenter.rememberTimelineItemPresenterWithLazyListState
import dev.dimension.flare.ui.route.Route
import dev.dimension.flare.ui.route.Router
import dev.dimension.flare.ui.theme.screenHorizontalPadding
import io.github.composefluent.FluentTheme
import io.github.composefluent.component.NavigationDefaults
import io.github.composefluent.component.ProgressBar
import io.github.composefluent.component.SubtleButton
import io.github.composefluent.component.Text
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.tlaster.precompose.molecule.producePresenter
import org.apache.commons.lang3.SystemUtils
import org.jetbrains.compose.resources.stringResource
import dev.dimension.flare.ui.component.Text as UiText

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun HomeTimelineScreen(
    accountType: AccountType,
    onAddTab: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current
    val state by producePresenter(key = "home_timeline_$accountType") {
        presenter(accountType)
    }
    val canComposeState = remember { CanComposePresenter() }.invoke()
    if (LocalGlobalAppearance.current.deckMode && isBigScreen()) {
        val lazyRowState = rememberLazyListState()
        FlareScrollBar(
            state = lazyRowState,
            isVertical = false,
        ) {
            LazyRow(
                state = lazyRowState,
            ) {
                state.tabState.onSuccess { tabs ->
                    items(
                        items = tabs,
                        key = { it.id },
                    ) { tab ->
                        val deepLinkState by producePresenter("desktop_deck_timeline_${tab.id}") {
                            val backStack =
                                remember(tab.id) {
                                    mutableStateListOf<Route>(Route.DeckTimeline(tab.id))
                                }
                            val state =
                                DeepLinkPresenter(
                                    onRoute = {
                                        Route.from(it)?.let { route ->
                                            backStack.add(route)
                                        }
                                    },
                                    onLink = {
                                        uriHandler.openUri(it)
                                    },
                                ).invoke()
                            object : DeepLinkPresenter.State by state {
                                val backStack = backStack
                            }
                        }
                        CompositionLocalProvider(
                            LocalUriHandler provides
                                remember(deepLinkState) {
                                    object : UriHandler {
                                        override fun openUri(uri: String) {
                                            deepLinkState.handle(uri)
                                        }
                                    }
                                },
                            LocalWindowSizeClass provides WindowSizeClass.Compact,
                        ) {
                            Row(
                                modifier = Modifier.clipToBounds(),
                            ) {
                                Box {
                                    Router(
                                        modifier =
                                            Modifier
                                                .fillMaxHeight()
                                                .width(360.dp),
                                        backStack = deepLinkState.backStack.toImmutableList(),
                                        navigate = { route ->
                                            when (route) {
                                                Route.TabSetting -> {
                                                    onAddTab.invoke()
                                                }

                                                else -> {
                                                    deepLinkState.backStack.add(route)
                                                }
                                            }
                                        },
                                        replace = { route ->
                                            if (deepLinkState.backStack.size > 1) {
                                                deepLinkState.backStack.removeAt(deepLinkState.backStack.lastIndex)
                                            }
                                            deepLinkState.backStack.add(route)
                                        },
                                        onBack = {
                                            if (deepLinkState.backStack.size > 1) {
                                                deepLinkState.backStack.removeAt(deepLinkState.backStack.lastIndex)
                                            }
                                        },
                                        enableDeepLinkHandler = false,
                                    )
                                    if (deepLinkState.backStack.size > 1) {
                                        NavigationDefaults.BackButton(
                                            onClick = {
                                                if (deepLinkState.backStack.size > 1) {
                                                    deepLinkState.backStack.removeAt(deepLinkState.backStack.lastIndex)
                                                }
                                            },
                                            modifier =
                                                Modifier
                                                    .let {
                                                        if (SystemUtils.IS_OS_WINDOWS) {
                                                            it.padding(top = LocalWindowPadding.current.calculateTopPadding())
                                                        } else {
                                                            it
                                                        }
                                                    }.align(Alignment.TopStart),
                                        )
                                    }
                                }
                                if (tab != tabs.last()) {
                                    Box(
                                        modifier =
                                            Modifier
                                                .fillMaxHeight()
                                                .width(1.dp)
                                                .background(FluentTheme.colors.stroke.divider.default),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        state.tabState.onSuccess { tabState ->
            state.selectedTab.onSuccess { currentTab ->
                val currentTabTimelineState =
                    key(currentTab.id) {
                        rememberTimelineItemPresenterWithLazyListState(
                            item = currentTab,
                            isHomeTimeline = true,
                            leadingItemCount = 0,
                        )
                    }
                val autoRefreshInterval = LocalAppSettings.current.homeTimelineAutoRefreshInterval
                val latestTimelineState by rememberUpdatedState(currentTabTimelineState)
                val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
                DisposableEffect(lifecycleOwner) {
                    val observer =
                        androidx.lifecycle.LifecycleEventObserver { _, event ->
                            if (event == androidx.lifecycle.Lifecycle.Event.ON_STOP) latestTimelineState.reading?.savePosition()
                        }
                    lifecycleOwner.lifecycle.addObserver(observer)
                    onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
                }
                LaunchedEffect(currentTab.id, autoRefreshInterval) {
                    if (autoRefreshInterval == TimelineAutoRefreshInterval.DISABLED) {
                        return@LaunchedEffect
                    }
                    while (true) {
                        delay(autoRefreshInterval.minutes * 60_000L)
                        if (!latestTimelineState.isRefreshing) {
                            latestTimelineState.refreshSuspend()
                        }
                    }
                }
                val timelineAppearance = LocalTimelineAppearance.current
                CompositionLocalProvider(
                    LocalTimelineAppearance provides
                        remember(
                            currentTab.appearancePatch,
                            timelineAppearance,
                        ) {
                            currentTab.resolveTimelineAppearance(timelineAppearance)
                        },
                ) {
                    Box {
                        TimelineContent(
                            state = currentTabTimelineState,
                            contentPadding =
                                PaddingValues(
                                    top = 48.dp,
                                    start = 24.dp,
                                    end = 24.dp,
                                ),
                            allowGalleryMode = true,
                        )
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .background(FluentTheme.colors.background.mica.base)
                                    .clickable {
                                        // prevent click through
                                    },
                        ) {
                            Box {
                                Box(
                                    modifier =
                                        Modifier
                                            .alpha(0.66f)
                                            .matchParentSize()
                                            .blur(16.dp)
                                            .background(
                                                if (LocalTimelineAppearance.current.timelineDisplayMode == TimelineDisplayMode.Plain &&
                                                    !FluentTheme.colors.darkMode
                                                ) {
                                                    FluentTheme.colors.background.layer.default
                                                } else {
                                                    FluentTheme.colors.background.mica.base
                                                },
                                            ),
                                )
                                val tabScrollState = rememberScrollState()
                                val tabScrollScope = rememberCoroutineScope()
                                Row(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth(1f)
                                            .padding(LocalWindowPadding.current)
                                            .padding(horizontal = screenHorizontalPadding),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    Row(
                                        modifier =
                                            Modifier
                                                .weight(1f)
                                                .horizontalScroll(tabScrollState)
                                                .onPointerEvent(PointerEventType.Scroll) { event ->
                                                    val delta =
                                                        event.changes
                                                            .firstOrNull()
                                                            ?.scrollDelta
                                                            ?.y ?: 0f
                                                    if (delta != 0f) {
                                                        tabScrollScope.launch {
                                                            tabScrollState.scrollBy(delta * 80f)
                                                        }
                                                    }
                                                }.pointerInput(Unit) {
                                                    detectDragGestures { change, dragAmount ->
                                                        change.consume()
                                                        tabScrollScope.launch {
                                                            tabScrollState.scrollBy(-dragAmount.x)
                                                        }
                                                    }
                                                },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    ) {
                                        tabState.forEachIndexed { index, tab ->
                                            val selected = tab.id == currentTab.id
                                            val underlineColor = FluentTheme.colors.text.accent.primary
                                            Box(
                                                modifier =
                                                    Modifier.drawWithContent {
                                                        drawContent()
                                                        if (selected) {
                                                            // 滚动容器里宽度约束无限，这里按自身尺寸画线
                                                            val thickness = 3.dp.toPx()
                                                            drawRect(
                                                                color = underlineColor,
                                                                topLeft = Offset(0f, size.height - thickness),
                                                                size = Size(size.width, thickness),
                                                            )
                                                        }
                                                    },
                                            ) {
                                                SubtleButton(
                                                    onClick = {
                                                        if (selected) {
                                                            if (currentTabTimelineState.lazyListState.firstVisibleItemIndex == 0) {
                                                                currentTabTimelineState.refreshSync()
                                                            } else {
                                                                currentTabTimelineState.lazyListState.requestScrollToItem(
                                                                    0,
                                                                )
                                                            }
                                                        } else {
                                                            state.setSelectedIndex(index)
                                                        }
                                                    },
                                                ) {
                                                    UiText(
                                                        tab.title,
                                                        style = FluentTheme.typography.bodyStrong,
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    SubtleButton(
                                        onClick = {
                                            onAddTab.invoke()
                                        },
                                    ) {
                                        FAIcon(
                                            imageVector = FontAwesomeIcons.Solid.Sliders,
                                            contentDescription = stringResource(Res.string.tab_settings_add_tab),
                                            modifier = Modifier.size(16.dp),
                                        )
                                    }
                                }
                            }
                        }

                        AnimatedVisibility(
                            currentTabTimelineState.isRefreshing,
                            enter = slideInVertically { -it },
                            exit = slideOutVertically { -it },
                        ) {
                            ProgressBar(
                                modifier =
                                    Modifier
                                        .align(Alignment.TopCenter)
                                        .fillMaxWidth(),
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun presenter(accountType: AccountType) =
    run {
        val state = remember { HomeTimelineWithTabsPresenter() }.invoke()
        var selectedId by remember { mutableStateOf<String?>(null) }

        state.tabState.onSuccess { tabs ->
            LaunchedEffect(tabs.map { it.id }, selectedId, state.selectionReady) {
                if (state.selectionReady) {
                    selectedId = tabs.firstOrNull { it.id == (selectedId ?: state.initialSelectedTabId) }?.id ?: tabs.firstOrNull()?.id
                    selectedId?.let(state::selectTab)
                }
            }
        }

        val selectedTab =
            remember(
                state.tabState,
                selectedId,
            ) {
                state.tabState.map { tabs -> tabs.firstOrNull { it.id == selectedId } ?: tabs.first() }
            }

        object : HomeTimelineWithTabsPresenter.State by state {
            val selectedTab = selectedTab

            fun setSelectedIndex(index: Int) {
                state.tabState.onSuccess { selectedId = it.getOrNull(index)?.id }
            }
        }
    }
