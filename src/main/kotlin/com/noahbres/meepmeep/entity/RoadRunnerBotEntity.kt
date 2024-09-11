package com.noahbres.meepmeep.entity

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.noahbres.meepmeep.MeepMeep
import com.noahbres.meepmeep.exhaustive
import com.noahbres.meepmeep.Constraints
import com.noahbres.meepmeep.DriveShim
import com.noahbres.meepmeep.DriveTrainType
import com.noahbres.meepmeep.trajectorysequence.TrajectorySequence
import com.noahbres.meepmeep.trajectorysequence.sequencesegment.SequenceSegment
import com.noahbres.meepmeep.trajectorysequence.sequencesegment.TrajectorySegment
import com.noahbres.meepmeep.trajectorysequence.sequencesegment.TurnSegment
import com.noahbres.meepmeep.trajectorysequence.sequencesegment.WaitSegment
import com.noahbres.meepmeep.ui.TrajectoryProgressSliderMaster
import kotlin.math.min

class RoadRunnerBotEntity(
    meepMeep: MeepMeep,
    private var constraints: com.noahbres.meepmeep.Constraints,

    width: Double, height: Double,
    pose: Pose2d,

    val colorScheme: com.noahbres.meepmeep.colorscheme.ColorScheme,
    opacity: Double,

    private var driveTrainType: com.noahbres.meepmeep.DriveTrainType = com.noahbres.meepmeep.DriveTrainType.MECANUM,

    var listenToSwitchThemeRequest: Boolean = false
) : BotEntity(meepMeep, width, height, pose, colorScheme, opacity), EntityEventListener {
    companion object {
        const val SKIP_LOOPS = 2
    }

    override val tag = "RR_BOT_ENTITY"

    override var zIndex: Int = 0

    var drive = com.noahbres.meepmeep.DriveShim(driveTrainType, constraints, pose)

    var currentTrajectorySequence: com.noahbres.meepmeep.trajectorysequence.TrajectorySequence? = null

    private var trajectorySequenceEntity: TrajectorySequenceEntity? = null

    var looping = true
    private var running = false

    private var trajectorySequenceElapsedTime = 0.0
        set(value) {
            trajectorySequenceEntity?.trajectoryProgress = value
            field = value
        }

    var trajectoryPaused = false

    private var skippedLoops = 0

    private var sliderMaster: TrajectoryProgressSliderMaster? = null
    private var sliderMasterIndex: Int? = null

    override fun update(deltaTime: Long) {
        if (!running) return

        if (skippedLoops++ < SKIP_LOOPS) return

        if (!trajectoryPaused) trajectorySequenceElapsedTime += deltaTime / 1e9

        when {
            trajectorySequenceElapsedTime <= currentTrajectorySequence!!.duration() -> {
                var segment: com.noahbres.meepmeep.trajectorysequence.sequencesegment.SequenceSegment? = null
                var segmentOffsetTime = 0.0

                var currentTime = 0.0

                for (i in 0 until currentTrajectorySequence!!.size()) {
                    val seg = currentTrajectorySequence!!.get(i)

                    if (currentTime + seg.duration > trajectorySequenceElapsedTime) {
                        segmentOffsetTime = trajectorySequenceElapsedTime - currentTime
                        segment = seg

                        break
                    } else {
                        currentTime += seg.duration
                    }
                }

                pose = when (segment) {
                    is com.noahbres.meepmeep.trajectorysequence.sequencesegment.WaitSegment -> segment.startPose
                    is com.noahbres.meepmeep.trajectorysequence.sequencesegment.TurnSegment -> segment.startPose.copy(heading = segment.motionProfile[segmentOffsetTime].x)
                    is com.noahbres.meepmeep.trajectorysequence.sequencesegment.TrajectorySegment -> segment.trajectory[segmentOffsetTime]
                    else -> currentTrajectorySequence!!.end()
                }

                drive.poseEstimate = pose

                trajectorySequenceEntity!!.markerEntityList.forEach { if (trajectorySequenceElapsedTime >= it.time) it.passed() }

                sliderMaster?.reportProgress(sliderMasterIndex ?: -1, trajectorySequenceElapsedTime)

                Unit
            }

            looping -> {
                trajectorySequenceEntity!!.markerEntityList.forEach {
                    it.reset()
                }
                trajectorySequenceElapsedTime = 0.0

                sliderMaster?.reportDone(sliderMasterIndex ?: -1)
            }

            else -> {
                trajectorySequenceElapsedTime = 0.0
                running = false
//                currentTrajectorySequence = null

                sliderMaster?.reportDone(sliderMasterIndex ?: -1)
            }
        }.exhaustive
    }

    fun start() {
        running = true
        trajectorySequenceElapsedTime = 0.0
    }

    fun resume() {
        running = true
    }

    fun pause() {
        trajectoryPaused = true
    }

    fun unpause() {
        trajectoryPaused = false
    }

    fun setTrajectoryProgressSeconds(seconds: Double) {
        if (currentTrajectorySequence != null)
            trajectorySequenceElapsedTime = min(seconds, currentTrajectorySequence!!.duration())
    }

    fun followTrajectorySequence(sequence: com.noahbres.meepmeep.trajectorysequence.TrajectorySequence) {
        currentTrajectorySequence = sequence

        trajectorySequenceEntity = TrajectorySequenceEntity(meepMeep, sequence, colorScheme)
    }

    fun setConstraints(constraints: com.noahbres.meepmeep.Constraints) {
        this.constraints = constraints

        drive = com.noahbres.meepmeep.DriveShim(driveTrainType, constraints, pose)
    }

    fun setDriveTrainType(driveTrainType: com.noahbres.meepmeep.DriveTrainType) {
        this.driveTrainType = driveTrainType

        drive = com.noahbres.meepmeep.DriveShim(driveTrainType, constraints, pose)
    }

    override fun switchScheme(scheme: com.noahbres.meepmeep.colorscheme.ColorScheme) {
        if (listenToSwitchThemeRequest)
            super.switchScheme(scheme)
    }

    fun setTrajectoryProgressSliderMaster(master: TrajectoryProgressSliderMaster, index: Int) {
        sliderMaster = master
        sliderMasterIndex = index
    }

    override fun onAddToEntityList() {
        if (trajectorySequenceEntity != null)
            meepMeep.requestToAddEntity(trajectorySequenceEntity!!)
    }

    override fun onRemoveFromEntityList() {
        if (trajectorySequenceEntity != null)
            meepMeep.requestToRemoveEntity(trajectorySequenceEntity!!)
    }
}