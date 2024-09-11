package com.noahbres.meepmeep

import com.noahbres.meepmeep.trajectorysequence.TrajectorySequence

fun interface AddTrajectorySequenceCallback {
    fun buildTrajectorySequence(drive: DriveShim): TrajectorySequence
}