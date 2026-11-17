# Flare

## 本 fork 的 Compose Desktop 修改

本仓库 fork 自 [flaresocialapp/Flare](https://github.com/flaresocialapp/Flare)。本轮调整集中在 `desktopApp/`，用于 Compose Desktop 的 macOS、Windows 和 Linux 界面。`appleApp/` 中的原生 SwiftUI macOS/iOS 客户端、Android 客户端、Web 客户端及共享业务逻辑没有修改。下方商店和上游发布链接仍指向原版，不包含本 fork 的界面调整。

### 窗口与信息流

- 普通时间轴、发现、通知、搜索、个人资料和本地缓存的帖子列表固定单列；拉宽窗口时不再自动增加列数。用户主动开启的 Deck 多时间轴模式仍保留。
- 主窗口默认尺寸保持 `520 × 840`，最小尺寸设为 `460 × 620`（AWT 窗口逻辑单位）。启动时检查屏幕可用区域，避免窗口超出屏幕。
- 首页移除内嵌发布区，直接显示信息流；发布入口移到头像菜单。
- 首页信息流额外增加左右各 `24dp` 留白。
- 帖子详情页移除原来的 `480dp` 最大宽度限制，随窗口扩展；统一页面底色，并采用与首页一致的左右留白。

### 顶部标签栏

- 顶栏固定显示，滚动信息流时不再收起。
- 标签选择改为横向平铺的单行 tab，窄窗口下也保留直接点击切换。
- tab 只显示粗体文字，隐藏图标；选中项使用 `3dp` 主题强调色下划线，不使用选中底色。
- 支持鼠标滚轮横向浏览和拖拽左右平移；这里的拖拽用于浏览标签，不改变标签顺序。排序仍在“编辑标签页”中完成。
- 移除独立刷新按钮，保留当前标签的回顶/刷新交互；右侧保留标签页编辑入口。

### 底部导航与头像菜单

- 原左侧竖向导航改为底部导航：返回按钮在最左侧，主页、通知、搜索在中间居中，头像在最右侧。
- 将二级页原标题栏返回按钮移到底栏。
- 返回、主页、通知、搜索替换为本 fork 提供的 SVG 图形，转换为 Compose `ImageVector`，保留原始路径及 `1024 × 1024` 画布比例。实现位于 `desktopApp/src/main/kotlin/dev/dimension/flare/ui/BottomBarIcons.kt`。
- 四个导航按钮复用同一个图标组件和按钮布局。隐藏文字时，按钮区域统一为 `48 × 48dp`，图标区域统一为 `24 × 24dp`；移除按钮库默认内边距与自定义内边距的叠加，修复返回图标被容器压缩的问题。
- 头像缩小到 `26dp`；“发布”和“设置”收入头像菜单，账号切换与账号快捷入口继续保留。
- 头像菜单改用 `MenuFlyout`，修复菜单闪现后关闭的问题；移除菜单内部滚动条，避免无界固有高度测量引发 `Size(4 x 2147483647) is out of range` 崩溃。

### 添加标签页与弹窗

- 添加标签页弹窗左侧先列账号，“订阅”放到最后；有账号时默认选中第一个账号。
- 账号右侧内容改为一条长列表，原分组 tab 改为 section 标题，减少二次切换。
- 添加或删除条目时保持当前账号及列表位置，不再因数据刷新跳回“订阅”。
- 添加图标使用主题强调色，删除图标使用红色。
- 点击弹窗外部区域可以关闭弹窗。当前实现作用于桌面端 `FluentDialogSceneStrategy` 承载的所有路由弹窗；发布草稿的外点关闭仍需留意，尚未增加草稿保存确认拦截。

### 构建与验证

在项目配置要求的 JDK 环境中，从仓库根目录运行：

```sh
./gradlew :desktopApp:compileKotlin
./gradlew :desktopApp:createReleaseDistributable
```

macOS 应用产物位于 `desktopApp/build/compose/binaries/main-release/app/Flare.app`。本轮已在 macOS 上编译、打包并安装为 `/Applications/Flare Compose.app`，使用独立 bundle ID `dev.dimension.flare.compose` 与 ad-hoc 签名进行对比验收。这些安装处理没有改动仓库中的正式发行签名或商店配置。

Windows/Linux 共用 `desktopApp/` 中的 UI 代码，但本轮未在这两个系统上打包或运行验收；对应安装包应在目标系统或 CI runner 上构建。安装包、账号数据和本地 SwiftUI 临时工程不纳入这次源码提交。

---

## 上游项目介绍

[![license](https://img.shields.io/github/license/flaresocialapp/Flare)](https://github.com/flaresocialapp/Flare/blob/master/LICENSE)
[![Crowdin](https://badges.crowdin.net/flareapp/localized.svg)](https://crowdin.com/project/flareapp)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/flaresocialapp/Flare)
[![Telegram](https://img.shields.io/badge/-telegram-blue?logo=telegram&color=white)](https://t.me/+fEJ2UYnOlVM0NmM1)
[![Discord](https://img.shields.io/badge/-discord-blue?logo=discord&color=white)](https://discord.gg/De9NhXBryT)

![badge-Android](https://img.shields.io/badge/Android-8.0-3DDC84)
![badge-iOS](https://img.shields.io/badge/iOS-17.0-black)
![badge-Windows](https://img.shields.io/badge/Windows_10-1809-blue)
![badge-macOS](https://img.shields.io/badge/macOS-Sonoma-black)
![badge-Linux](https://img.shields.io/badge/Linux-AppImage-black)
![badge-Web](https://img.shields.io/badge/Web-Svelte-black)

Flare is an open-source, privacy-first social client that brings Mastodon, Misskey, Bluesky, X, Pixiv, Fanbox, Nostr, and RSS into one unified timeline. It supports cross-posting, lists, feeds, DMs, RSS management, and AI-powered features such as translation and agents. Built with Kotlin Multiplatform, Flare shares its core logic across Android, iOS, macOS, Windows, and Linux, turning fragmented social feeds into a personal information hub.

<a href="https://apps.microsoft.com/detail/9NLRN0BKZ357?referrer=appbadge&mode=direct">
	<img src="https://get.microsoft.com/images/en-us%20dark.svg" width="190"/>
</a>
<a href='https://apps.apple.com/us/app/flare-social-network-client/id6476077738'><img alt='Get it on Google Play' src='https://developer.apple.com/app-store/marketing/guidelines/images/badge-example-preferred_2x.png' width=150/></a>
<a href='https://play.google.com/store/apps/details?id=dev.dimension.flare&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='docs/GetItOnGooglePlay_Badge_Web_color_English.svg' width=170/></a>
<a href='https://f-droid.org/packages/dev.dimension.flare'><img alt='Get it on F-Droid' src='https://f-droid.org/badge/get-it-on.svg' width=150/></a>
<a href='https://github.com/flaresocialapp/Flare/releases/latest'><img alt='Download AppImage' src='docs/appimage_badge.svg' width=150/></a>

## Features
 - Unified social inbox: Flare brings Mastodon, Misskey, Bluesky, X, Pixiv, Fanbox, Nostr and RSS together in one place, so users can follow fragmented communities through a single timeline.
 - Mixed timeline experience: It merges content from multiple accounts and platforms into a coherent feed, reducing context switching between apps.
 - Cross-platform by design: Built with Kotlin Multiplatform, Flare shares core logic across Android, iOS, macOS, Windows, and Linux.
 - Rich platform support: Beyond basic timelines, it supports features such as polls, lists, bookmarks/favorites, Misskey antennas, Bluesky feeds and DMs, and RSS management.
 - Cross-posting workflow: Users can publish to multiple platforms at once, making it practical for creators and heavy social media users.
 - Built in AI Agent: Configure OpenAI-compatible or on-device AI, enable summaries, or ask for post or profile insights, chat with Flare’s agent, and query local cache/history.
 - Privacy-first approach: As a FOSS client, it emphasizes user control with features like anonymous mode, local filtering, local history, and transparent data handling.

## Plugins (Beta)

Connect additional websites with social plugins on Android, Desktop, iOS, and
native macOS. Use a Flare build that includes Plugins in Settings; plugin support
is not available in the Web client.

Flare builds with Plugin API v2 support text translation, plugin-owned storage
and offline settings pages independently of social capabilities. Settings pages
support iOS, Android and native macOS. See the
[v2 developer contract](feature/plugin/sdk/skill/flare-plugin/references/storage-and-settings.md)
for storage and the webpage SDK.
Existing-platform actions can use explicitly authorized built-in accounts and run
manually or once per day when Flare enters the foreground. The first native
authentication adapter is Weibo; see the [extension contract](feature/plugin/sdk/skill/flare-plugin/references/platform-extensions.md).

- **Install a plugin:** open Settings → Plugins, add its public GitHub repository
  URL, and review its source and permissions before confirming. For a plugin in
  a subdirectory, use `https://github.com/OWNER/REPO?path=/plugins/example`.
  One repository can contain several plugins with independent versions; its
  latest stable tag selects the repository snapshot. See the
  [installation and update guide](feature/plugin/sdk/skill/flare-plugin/references/publishing.md).
- **Create with Flare AI:** ask the configured AI assistant to build a plugin
  for your requested features. See [creating plugins in Flare](feature/plugin/AI_AUTHORING.md).
- **Develop a plugin:** [match Flare and the SDK](feature/plugin/sdk/skill/flare-plugin/README.md#match-flare-and-the-sdk),
  then follow the [SDK quick start](feature/plugin/sdk/README.md) through local debugging and
  recommended GitHub publication (with local `.fpp` packaging as an alternative),
  then use the [complete developer guide](feature/plugin/sdk/skill/flare-plugin/README.md)
  for authentication, capabilities, data types, desktop development, and examples.

## Roadmap
Here're some features we're planning to implement in the future.
 - [x] Grouped Mixed timeline
 - [ ] Showing instance's announcement
 - [x] Crossposting for repost
 - [ ] Auto thread
 - [ ] AI powered features
   - [ ] Personal trends of the day
   - [ ] Quick reply
 - [ ] Support for Meta Threads
 - [ ] Support for Discourse forum
 - [x] Support for Nostr
 - [x] Desktop Client
 - [x] Web Client

## Contributing
See [CONTRIBUTING.md](CONTRIBUTING.md) for more information.

## License
This project is licensed under the [AGPL-3.0](LICENSE) license.
