package dev.furthestdrop.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint
import com.acmerobotics.roadrunner.trajectory.constraints.TankVelocityConstraint
import dev.furthestdrop.meepmeep.trajectorysequence.TrajectorySequenceBuilder

class DriveShim(
    driveTrainType: dev.furthestdrop.meepmeep.DriveTrainType,
    private val constraints: dev.furthestdrop.meepmeep.Constraints,
    var poseEstimate: Pose2d
) {
    private val velConstraint = when (driveTrainType) {
        dev.furthestdrop.meepmeep.DriveTrainType.MECANUM -> MinVelocityConstraint(
            listOf(
                AngularVelocityConstraint(constraints.maxAngVel),
                MecanumVelocityConstraint(constraints.maxVel, constraints.trackWidth)
            )
        )

        dev.furthestdrop.meepmeep.DriveTrainType.TANK -> MinVelocityConstraint(
            listOf(
                AngularVelocityConstraint(constraints.maxAngVel),
                TankVelocityConstraint(constraints.maxVel, constraints.trackWidth)
            )
        )
    }

    private val accelConstraint = ProfileAccelerationConstraint(constraints.maxAccel)

    fun trajectorySequenceBuilder(startPose: Pose2d): dev.furthestdrop.meepmeep.trajectorysequence.TrajectorySequenceBuilder {
        return dev.furthestdrop.meepmeep.trajectorysequence.TrajectorySequenceBuilder(
            startPose,
            velConstraint,
            accelConstraint,
            constraints.maxAngVel,
            constraints.maxAngAccel,
        )
    }
}