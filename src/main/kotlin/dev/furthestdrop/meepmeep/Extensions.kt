package dev.furthestdrop.meepmeep

import com.acmerobotics.roadrunner.geometry.Vector2d
import dev.furthestdrop.meepmeep.util.FieldUtil

fun Vector2d.toScreenCoord() = FieldUtil.fieldCoordsToScreenCoords(this)

fun Double.scaleInToPixel() = FieldUtil.scaleInchesToPixel(this)
fun Double.toDegrees() = Math.toDegrees(this)
fun Double.toRadians() = Math.toRadians(this)

val <T> T.exhaustive: T
    get() = this
