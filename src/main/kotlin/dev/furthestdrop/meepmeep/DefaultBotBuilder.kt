package dev.furthestdrop.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import dev.furthestdrop.meepmeep.entity.RoadRunnerBotEntity

class DefaultBotBuilder(private val meepMeep: dev.furthestdrop.meepmeep.MeepMeep) {

    private var constraints = dev.furthestdrop.meepmeep.Constraints(
        30.0, 30.0, Math.toRadians(60.0), Math.toRadians(60.0), 15.0
    )

    private var width = 18.0
    private var height = 18.0

    private var startPose = Pose2d()
    private var colorScheme: dev.furthestdrop.meepmeep.colorscheme.ColorScheme? = null
    private var opacity = 0.8

    private var driveTrainType = dev.furthestdrop.meepmeep.DriveTrainType.MECANUM

    fun setDimensions(width: Double, height: Double): dev.furthestdrop.meepmeep.DefaultBotBuilder {
        this.width = width
        this.height = height

        return this
    }

    fun setStartPose(pose: Pose2d): dev.furthestdrop.meepmeep.DefaultBotBuilder {
        this.startPose = pose

        return this
    }

    fun setConstraints(constraints: dev.furthestdrop.meepmeep.Constraints): dev.furthestdrop.meepmeep.DefaultBotBuilder {
        this.constraints = constraints

        return this
    }

    fun setConstraints(
        maxVel: Double,
        maxAccel: Double,
        maxAngVel: Double,
        maxAngAccel: Double,
        trackWidth: Double
    ): dev.furthestdrop.meepmeep.DefaultBotBuilder {
        constraints = dev.furthestdrop.meepmeep.Constraints(
            maxVel,
            maxAccel,
            maxAngVel,
            maxAngAccel,
            trackWidth
        )

        return this
    }

    fun setDriveTrainType(driveTrainType: dev.furthestdrop.meepmeep.DriveTrainType): dev.furthestdrop.meepmeep.DefaultBotBuilder {
        this.driveTrainType = driveTrainType

        return this
    }

    fun setColorScheme(scheme: dev.furthestdrop.meepmeep.colorscheme.ColorScheme): dev.furthestdrop.meepmeep.DefaultBotBuilder {
        this.colorScheme = scheme

        return this
    }

    fun build(): RoadRunnerBotEntity {
        return RoadRunnerBotEntity(
            meepMeep,
            constraints,
            width, height,
            startPose, colorScheme ?: meepMeep.colorManager.theme, opacity,
            driveTrainType, false
        )
    }

    fun followTrajectorySequence(trajectorySequence: dev.furthestdrop.meepmeep.trajectorysequence.TrajectorySequence): RoadRunnerBotEntity {
        val bot = this.build()
        bot.followTrajectorySequence(trajectorySequence)

        return bot
    }

    fun followTrajectorySequence(callback: dev.furthestdrop.meepmeep.AddTrajectorySequenceCallback): RoadRunnerBotEntity {
        return followTrajectorySequence(callback.buildTrajectorySequence(build().drive))
    }
}