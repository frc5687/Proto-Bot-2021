package org.frc5687.swerve.util;

import org.photonvision.*;
import org.photonvision.common.hardware.VisionLEDMode;

public class Limelight {
    private PhotonCamera camera;

    public Limelight(){
        camera = new PhotonCamera("limelight");
    }

    public void enableLEDS(){
        camera.setLED(VisionLEDMode.kOn);
    }

    public void disableLEDS(){
        camera.setLED(VisionLEDMode.kOff);
    }

    public void blink(){
        camera.setLED(VisionLEDMode.kBlink);
    }
}
