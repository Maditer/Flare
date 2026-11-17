package dev.dimension.flare.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

// 底栏图标，路径来自设计稿 SVG（viewBox 1024x1024），统一尺寸后由 Icon 的 tint 上色
public object BottomBarIcons {
    public val Home: ImageVector =
        ImageVector
            .Builder(
                name = "Home",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 1024f,
                viewportHeight = 1024f,
            ).apply {
                path(fill = SolidColor(Color.Black)) {
                    moveTo(946.5000f, 505.0000f)
                    lineTo(560.1000f, 118.8000f)
                    lineTo(534.2000f, 92.9000f)
                    curveTo(521.9000f, 80.7000f, 502.1000f, 80.7000f, 489.8000f, 92.9000f)
                    lineTo(77.5000f, 505.0000f)
                    curveTo(65.2000f, 517.3000f, 58.6000f, 533.6000f, 58.7000f, 551.0000f)
                    curveTo(59.1000f, 586.2000f, 88.4000f, 614.3000f, 123.6000f, 614.3000f)
                    lineTo(166.1000f, 614.3000f)
                    lineTo(166.1000f, 940.0000f)
                    lineTo(857.9000f, 940.0000f)
                    lineTo(857.9000f, 614.3000f)
                    lineTo(901.3000f, 614.3000f)
                    curveTo(918.4000f, 614.3000f, 934.5000f, 607.6000f, 946.6000f, 595.5000f)
                    curveTo(958.7000f, 583.4000f, 965.3000f, 567.3000f, 965.3000f, 550.2000f)
                    curveTo(965.3000f, 533.2000f, 958.6000f, 517.1000f, 946.5000f, 505.0000f)
                    close()
                    moveTo(568.0000f, 868.0000f)
                    lineTo(456.0000f, 868.0000f)
                    lineTo(456.0000f, 664.0000f)
                    lineTo(568.0000f, 664.0000f)
                    lineTo(568.0000f, 868.0000f)
                    close()
                    moveTo(785.9000f, 542.3000f)
                    lineTo(785.9000f, 868.0000f)
                    lineTo(632.0000f, 868.0000f)
                    lineTo(632.0000f, 640.0000f)
                    curveTo(632.0000f, 617.9000f, 614.1000f, 600.0000f, 592.0000f, 600.0000f)
                    lineTo(432.0000f, 600.0000f)
                    curveTo(409.9000f, 600.0000f, 392.0000f, 617.9000f, 392.0000f, 640.0000f)
                    lineTo(392.0000f, 868.0000f)
                    lineTo(238.1000f, 868.0000f)
                    lineTo(238.1000f, 542.3000f)
                    lineTo(142.1000f, 542.3000f)
                    lineTo(512.1000f, 172.6000f)
                    lineTo(535.2000f, 195.7000f)
                    lineTo(882.0000f, 542.3000f)
                    lineTo(785.9000f, 542.3000f)
                    close()
                }
            }.build()

    public val Notification: ImageVector =
        ImageVector
            .Builder(
                name = "Notification",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 1024f,
                viewportHeight = 1024f,
            ).apply {
                path(fill = SolidColor(Color.Black)) {
                    moveTo(880.0000f, 112.0000f)
                    curveTo(876.2000f, 112.0000f, 872.3000f, 112.7000f, 868.4000f, 114.3000f)
                    lineTo(292.0000f, 345.9000f)
                    lineTo(128.0000f, 345.9000f)
                    curveTo(119.2000f, 345.9000f, 112.0000f, 353.3000f, 112.0000f, 362.5000f)
                    lineTo(112.0000f, 661.5000f)
                    curveTo(112.0000f, 670.7000f, 119.2000f, 678.1000f, 128.0000f, 678.1000f)
                    lineTo(229.7000f, 678.1000f)
                    curveTo(226.0000f, 689.7000f, 224.0000f, 702.0000f, 224.0000f, 714.5000f)
                    curveTo(224.0000f, 780.4000f, 277.8000f, 834.0000f, 344.0000f, 834.0000f)
                    curveTo(399.4000f, 834.0000f, 446.1000f, 796.4000f, 459.9000f, 745.6000f)
                    lineTo(868.5000f, 909.8000f)
                    curveTo(872.4000f, 911.3000f, 876.3000f, 912.1000f, 880.1000f, 912.1000f)
                    curveTo(897.0000f, 912.1000f, 912.1000f, 897.9000f, 912.1000f, 878.9000f)
                    lineTo(912.1000f, 145.2000f)
                    curveTo(912.0000f, 126.2000f, 897.0000f, 112.0000f, 880.0000f, 112.0000f)
                    close()
                    moveTo(344.0000f, 762.3000f)
                    curveTo(317.5000f, 762.3000f, 296.0000f, 740.9000f, 296.0000f, 714.5000f)
                    curveTo(296.0000f, 703.3000f, 299.9000f, 692.6000f, 307.0000f, 684.1000f)
                    lineTo(391.9000f, 718.2000f)
                    curveTo(389.9000f, 742.8000f, 369.2000f, 762.3000f, 344.0000f, 762.3000f)
                    close()
                    moveTo(840.0000f, 820.7000f)
                    lineTo(318.8000f, 611.3000f)
                    lineTo(305.9000f, 606.1000f)
                    lineTo(184.0000f, 606.1000f)
                    lineTo(184.0000f, 417.9000f)
                    lineTo(305.9000f, 417.9000f)
                    lineTo(318.8000f, 412.7000f)
                    lineTo(840.0000f, 203.3000f)
                    lineTo(840.0000f, 820.7000f)
                    close()
                }
            }.build()

    public val Search: ImageVector =
        ImageVector
            .Builder(
                name = "Search",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 1024f,
                viewportHeight = 1024f,
            ).apply {
                path(fill = SolidColor(Color.Black)) {
                    moveTo(909.6000f, 854.5000f)
                    lineTo(649.9000f, 594.8000f)
                    curveTo(690.2000f, 542.7000f, 712.0000f, 479.0000f, 712.0000f, 412.0000f)
                    curveTo(712.0000f, 331.8000f, 680.7000f, 256.6000f, 624.1000f, 199.9000f)
                    curveTo(567.5000f, 143.2000f, 492.1000f, 112.0000f, 412.0000f, 112.0000f)
                    curveTo(331.9000f, 112.0000f, 256.5000f, 143.3000f, 199.9000f, 199.9000f)
                    curveTo(143.2000f, 256.5000f, 112.0000f, 331.8000f, 112.0000f, 412.0000f)
                    curveTo(112.0000f, 492.1000f, 143.3000f, 567.5000f, 199.9000f, 624.1000f)
                    curveTo(256.5000f, 680.8000f, 331.8000f, 712.0000f, 412.0000f, 712.0000f)
                    curveTo(479.0000f, 712.0000f, 542.6000f, 690.2000f, 594.7000f, 650.0000f)
                    lineTo(854.4000f, 909.6000f)
                    curveTo(857.6000f, 912.8000f, 862.8000f, 912.8000f, 866.0000f, 909.6000f)
                    lineTo(909.6000f, 866.1000f)
                    curveTo(912.8000f, 862.9000f, 912.8000f, 857.7000f, 909.6000f, 854.5000f)
                    close()
                    moveTo(570.4000f, 570.4000f)
                    curveTo(528.0000f, 612.7000f, 471.8000f, 636.0000f, 412.0000f, 636.0000f)
                    curveTo(352.2000f, 636.0000f, 296.0000f, 612.7000f, 253.6000f, 570.4000f)
                    curveTo(211.3000f, 528.0000f, 188.0000f, 471.8000f, 188.0000f, 412.0000f)
                    curveTo(188.0000f, 352.2000f, 211.3000f, 295.9000f, 253.6000f, 253.6000f)
                    curveTo(296.0000f, 211.3000f, 352.2000f, 188.0000f, 412.0000f, 188.0000f)
                    curveTo(471.8000f, 188.0000f, 528.1000f, 211.2000f, 570.4000f, 253.6000f)
                    curveTo(612.7000f, 296.0000f, 636.0000f, 352.2000f, 636.0000f, 412.0000f)
                    curveTo(636.0000f, 471.8000f, 612.7000f, 528.1000f, 570.4000f, 570.4000f)
                    close()
                }
            }.build()

    public val ArrowLeft: ImageVector =
        ImageVector
            .Builder(
                name = "ArrowLeft",
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 1024f,
                viewportHeight = 1024f,
            ).apply {
                path(fill = SolidColor(Color.Black)) {
                    moveTo(872f, 474f)
                    lineTo(286.9f, 474f)
                    lineTo(637.1f, 170f)
                    curveTo(642.7f, 165.1f, 639.3f, 156f, 631.9f, 156f)
                    lineTo(543.4f, 156f)
                    curveTo(539.5f, 156f, 535.8f, 157.4f, 532.9f, 159.9f)
                    lineTo(155f, 487.8f)
                    curveTo(140.3f, 500.6f, 140.3f, 523.4f, 155f, 536.1f)
                    lineTo(535.1f, 866f)
                    curveTo(536.6f, 867.3f, 538.4f, 868f, 540.3f, 868f)
                    lineTo(631.8f, 868f)
                    curveTo(639.2f, 868f, 642.6f, 858.8f, 637f, 854f)
                    lineTo(286.9f, 550f)
                    lineTo(872f, 550f)
                    curveTo(876.4f, 550f, 880f, 546.4f, 880f, 542f)
                    lineTo(880f, 482f)
                    curveTo(880f, 477.6f, 876.4f, 474f, 872f, 474f)
                    close()
                }
            }.build()
}
