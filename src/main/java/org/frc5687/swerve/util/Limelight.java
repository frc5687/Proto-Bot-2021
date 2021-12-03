package org.frc5687.swerve.util;

import edu.wpi.first.wpilibj.util.Units;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPipelineResult;
import org.photonvision.PhotonUtils;

public class Limelight extends PhotonCamera {

    private final double CAMERA_HEIGHT_METERS = 0.6;
    private final double TARGET_HEIGHT_METERS = 1.8;

    private final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(20);

    public Limelight() {
        super("limelight");
    }
    /**
     *
     * @param latestCameraResult Latest camera result
     * @return double
     */
    public double distanceToBestTarget(PhotonPipelineResult latestCameraResult) {
        return PhotonUtils.calculateDistanceToTargetMeters(
                CAMERA_HEIGHT_METERS,
                TARGET_HEIGHT_METERS,
                CAMERA_PITCH_RADIANS,
                Units.degreesToRadians(latestCameraResult.getBestTarget().getPitch()));
    }
}
