package org.frc5687.swerve.util;

import org.photonvision.*;
import org.photonvision.common.hardware.VisionLEDMode;

public class Limelight {
    private PhotonCamera camera;

    public Limelight(){
        camera = new PhotonCamera("limelight");
    }

    public void setLeds(short mode){
        switch(mode){
            case 0: 
            camera.setLED(VisionLEDMode.kOff);
            break;
            case 1:
            camera.setLED(VisionLEDMode.kOn);
            break;
        }
    }
}
