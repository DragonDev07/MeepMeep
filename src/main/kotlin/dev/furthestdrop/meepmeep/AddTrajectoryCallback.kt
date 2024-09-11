package dev.furthestdrop.meepmeep

import com.acmerobotics.roadrunner.trajectory.Trajectory

fun interface AddTrajectoryCallback {
    fun buildTrajectory(drive: dev.furthestdrop.meepmeep.DriveShim): List<Trajectory>
}