package dev.furthestdrop.meepmeep

import dev.furthestdrop.meepmeep.trajectorysequence.TrajectorySequence

fun interface AddTrajectorySequenceCallback {
    fun buildTrajectorySequence(drive: dev.furthestdrop.meepmeep.DriveShim): dev.furthestdrop.meepmeep.trajectorysequence.TrajectorySequence
}