//BAsic dashboard functions
package org.frc5687.swerve.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PrettyDash {
    
    public PrettyDash(){
        
    }

    public void metric(String name, String value) {
        SmartDashboard.putString(getClass().getSimpleName() + "/" + name, value);
    }

    public void metric(String name, double value) {
        SmartDashboard.putNumber(getClass().getSimpleName() + "/" + name, value);
    }

    public void metric(String name, boolean value) {
        SmartDashboard.putBoolean(getClass().getSimpleName() + "/" + name, value);
    }
}
