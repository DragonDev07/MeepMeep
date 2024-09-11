package com.noahbres.meepmeep.colorscheme

import com.noahbres.meepmeep.colorscheme.scheme.ColorSchemeRedDark


class ColorManager {
    companion object {
        @JvmField
        val COLOR_PALETTE = ColorPalette.DEFAULT_PALETTE

        @JvmField
        val DEFAULT_THEME_LIGHT: com.noahbres.meepmeep.colorscheme.ColorScheme =
            com.noahbres.meepmeep.colorscheme.scheme.ColorSchemeRedLight()

        @JvmField
        val DEFAULT_THEME_DARK: com.noahbres.meepmeep.colorscheme.ColorScheme = ColorSchemeRedDark()
    }

    var isDarkMode = false

    private var lightTheme: com.noahbres.meepmeep.colorscheme.ColorScheme = DEFAULT_THEME_LIGHT
    private var darkTheme: com.noahbres.meepmeep.colorscheme.ColorScheme = DEFAULT_THEME_DARK

    val theme: com.noahbres.meepmeep.colorscheme.ColorScheme
        get() {
            return if (!isDarkMode) lightTheme else darkTheme
        }

    @JvmOverloads
    fun setTheme(themeLight: com.noahbres.meepmeep.colorscheme.ColorScheme, themeDark: com.noahbres.meepmeep.colorscheme.ColorScheme = themeLight) {
        lightTheme = themeLight
        darkTheme = themeDark
    }
}
