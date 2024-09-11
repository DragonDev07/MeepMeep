package dev.furthestdrop.meepmeep.colorscheme

import dev.furthestdrop.meepmeep.colorscheme.scheme.ColorSchemeRedDark


class ColorManager {
    companion object {
        @JvmField
        val COLOR_PALETTE = ColorPalette.DEFAULT_PALETTE

        @JvmField
        val DEFAULT_THEME_LIGHT: dev.furthestdrop.meepmeep.colorscheme.ColorScheme =
            dev.furthestdrop.meepmeep.colorscheme.scheme.ColorSchemeRedLight()

        @JvmField
        val DEFAULT_THEME_DARK: dev.furthestdrop.meepmeep.colorscheme.ColorScheme = ColorSchemeRedDark()
    }

    var isDarkMode = false

    private var lightTheme: dev.furthestdrop.meepmeep.colorscheme.ColorScheme = DEFAULT_THEME_LIGHT
    private var darkTheme: dev.furthestdrop.meepmeep.colorscheme.ColorScheme = DEFAULT_THEME_DARK

    val theme: dev.furthestdrop.meepmeep.colorscheme.ColorScheme
        get() {
            return if (!isDarkMode) lightTheme else darkTheme
        }

    @JvmOverloads
    fun setTheme(themeLight: dev.furthestdrop.meepmeep.colorscheme.ColorScheme, themeDark: dev.furthestdrop.meepmeep.colorscheme.ColorScheme = themeLight) {
        lightTheme = themeLight
        darkTheme = themeDark
    }
}
