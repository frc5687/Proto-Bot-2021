package org.frc5687.swerve.util;

import org.frc5687.swerve.RobotMap;
import edu.wpi.first.wpilibj.Compressor;

public class PCM {

    private Compressor comp;
    private boolean enabled;
    private boolean pressureSwitch;
    private double current;
    private boolean running = false;
    
    public PCM(){
        comp = new Compressor(RobotMap.PCM.PCM);
        comp.setClosedLoopControl(true);
        enabled = comp.enabled();
        pressureSwitch = comp.getPressureSwitchValue();
        current = comp.getCompressorCurrent();
    }

    public void run(){
        //Start compressor
        comp.start();
    }

    public void stop(){
        //Stop compressor
        comp.stop();
    }
}
