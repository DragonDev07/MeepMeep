package com.noahbres.meepmeep

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.noahbres.meepmeep.entity.RoadRunnerBotEntity

class DefaultBotBuilder(private val meepMeep: com.noahbres.meepmeep.MeepMeep) {

    private var constraints = com.noahbres.meepmeep.Constraints(
        30.0, 30.0, Math.toRadians(60.0), Math.toRadians(60.0), 15.0
    )

    private var width = 18.0
    private var height = 18.0

    private var startPose = Pose2d()
    private var colorScheme: com.noahbres.meepmeep.colorscheme.ColorScheme? = null
    private var opacity = 0.8

    private var driveTrainType = com.noahbres.meepmeep.DriveTrainType.MECANUM

    fun setDimensions(width: Double, height: Double): com.noahbres.meepmeep.DefaultBotBuilder {
        this.width = width
        this.height = height

        return this
    }

    fun setStartPose(pose: Pose2d): com.noahbres.meepmeep.DefaultBotBuilder {
        this.startPose = pose

        return this
    }

    fun setConstraints(constraints: com.noahbres.meepmeep.Constraints): com.noahbres.meepmeep.DefaultBotBuilder {
        this.constraints = constraints

        return this
    }

    fun setConstraints(
        maxVel: Double,
        maxAccel: Double,
        maxAngVel: Double,
        maxAngAccel: Double,
        trackWidth: Double
    ): com.noahbres.meepmeep.DefaultBotBuilder {
        constraints = com.noahbres.meepmeep.Constraints(
            maxVel,
            maxAccel,
            maxAngVel,
            maxAngAccel,
            trackWidth
        )

        return this
    }

    fun setDriveTrainType(driveTrainType: com.noahbres.meepmeep.DriveTrainType): com.noahbres.meepmeep.DefaultBotBuilder {
        this.driveTrainType = driveTrainType

        return this
    }

    fun setColorScheme(scheme: com.noahbres.meepmeep.colorscheme.ColorScheme): com.noahbres.meepmeep.DefaultBotBuilder {
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

    fun followTrajectorySequence(trajectorySequence: com.noahbres.meepmeep.trajectorysequence.TrajectorySequence): RoadRunnerBotEntity {
        val bot = this.build()
        bot.followTrajectorySequence(trajectorySequence)

        return bot
    }

    fun followTrajectorySequence(callback: com.noahbres.meepmeep.AddTrajectorySequenceCallback): RoadRunnerBotEntity {
        return followTrajectorySequence(callback.buildTrajectorySequence(build().drive))
    }
}