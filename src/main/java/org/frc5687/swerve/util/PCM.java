package org.frc5687.swerve.util;

import org.frc5687.swerve.RobotMap;

import edu.wpi.first.wpilibj.Compressor;

public class PCM {
    Compressor comp;
    
    public PCM(){
        comp = new Compressor();
        comp.setClosedLoopControl(true);
    }

    public boolean getPressure(){
        return comp.getPressureSwitchValue();
    }
}
