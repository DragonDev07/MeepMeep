package com.noahbres.meepmeep

import com.acmerobotics.roadrunner.trajectory.Trajectory

fun interface AddTrajectoryCallback {
    fun buildTrajectory(drive: DriveShim): List<Trajectory>
}