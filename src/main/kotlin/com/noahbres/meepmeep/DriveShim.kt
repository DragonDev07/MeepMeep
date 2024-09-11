package com.noahbres.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.TankVelocityConstraint
import com.noahbres.meepmeep.trajectorysequence.TrajectorySequenceBuilder

class DriveShim(
    driveTrainType: DriveTrainType,
    private val constraints: Constraints,
    var poseEstimate: Pose2d
) {
    private val velConstraint = when (driveTrainType) {
        DriveTrainType.MECANUM -> MinVelocityConstraint(
            listOf(
                AngularVelocityConstraint(constraints.maxAngVel),
                MecanumVelocityConstraint(constraints.maxVel, constraints.trackWidth)
            )
        )

        DriveTrainType.TANK -> MinVelocityConstraint(
            listOf(
                AngularVelocityConstraint(constraints.maxAngVel),
                TankVelocityConstraint(constraints.maxVel, constraints.trackWidth)
            )
        )
    }

    private val accelConstraint = ProfileAccelerationConstraint(constraints.maxAccel)

    fun trajectorySequenceBuilder(startPose: Pose2d): TrajectorySequenceBuilder {
        return TrajectorySequenceBuilder(
            startPose,
            velConstraint,
            accelConstraint,
            constraints.maxAngVel,
            constraints.maxAngAccel,
        )
    }
}