package org.frc5687.swerve.util;

import org.frc5687.swerve.Constants;
import org.photonvision.*;
import org.photonvision.common.hardware.VisionLEDMode;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.controller.PIDController;

public class Limelight {
    private PhotonCamera camera;
    private NetworkTable table;
    private boolean driver_mode;

    public Limelight(){
        camera = new PhotonCamera("limelight");
        table = NetworkTableInstance.getDefault().getTable("photonvision").getSubTable("limelight");
    }

    public void enableLEDS(){
        camera.setLED(VisionLEDMode.kOn);
    }

    public void disableLEDS(){
        camera.setLED(VisionLEDMode.kOff);
    }

    public void setDriverMode(boolean mode){
        disableLEDS();
        camera.setDriverMode(mode);
    }

    public boolean hasTarget(){
        return camera.getLatestResult().hasTargets();
    }

    public double getYaw(){
        return camera.getLatestResult().getBestTarget().getYaw();
    }

    public double getArea(){
        return camera.getLatestResult().getBestTarget().getArea();
    }
}
//Kilroy Was Here