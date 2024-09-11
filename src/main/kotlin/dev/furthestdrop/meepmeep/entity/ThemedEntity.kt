package dev.furthestdrop.meepmeep.entity

import dev.furthestdrop.meepmeep.colorscheme.ColorScheme

interface ThemedEntity : Entity {
    fun switchScheme(scheme: dev.furthestdrop.meepmeep.colorscheme.ColorScheme)
}
