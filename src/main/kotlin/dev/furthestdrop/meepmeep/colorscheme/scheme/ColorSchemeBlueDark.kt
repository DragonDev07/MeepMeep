package dev.furthestdrop.meepmeep.colorscheme.scheme

import dev.furthestdrop.meepmeep.colorscheme.ColorManager
import java.awt.Color

open class ColorSchemeBlueDark : ColorSchemeBlueLight() {
    override val isDark: Boolean = true

    override val AXIS_X_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_300
    override val AXIS_Y_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_300

    override val AXIS_NORMAL_OPACITY: Double = 0.2

    override val TRAJECTORY_SLIDER_BG: Color = ColorManager.COLOR_PALETTE.GRAY_800
    override val TRAJECTORY_TEXT_COLOR: Color = ColorManager.COLOR_PALETTE.GRAY_100

}