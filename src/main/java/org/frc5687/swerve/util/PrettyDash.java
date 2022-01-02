//BAsic dashboard functions
package org.frc5687.swerve.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PrettyDash {
    
    public PrettyDash(){
        
    }

    public void metric(String name, String value) {
        try{
            SmartDashboard.putString(getClass().getSimpleName() + "/" + name, value);
        }catch(Exception e){
            DriverStation.reportWarning(e.toString(), true);
        }
    }

    public void metric(String name, double value) {
        try{
            SmartDashboard.putNumber(getClass().getSimpleName() + "/" + name, value);
        }catch(Exception e){
            DriverStation.reportWarning(e.toString(), true);
        }
    }

    public void metric(String name, boolean value) {
        try{
            SmartDashboard.putBoolean(getClass().getSimpleName() + "/" + name, value);
        }catch(Exception e){
            DriverStation.reportWarning(e.toString(), true);
        }
    }
}
