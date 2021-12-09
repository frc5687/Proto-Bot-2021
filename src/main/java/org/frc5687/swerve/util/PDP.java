package org.frc5687.swerve.util;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
public class PDP {
    PowerDistributionPanel pdp;

    public PDP(){
        pdp = new PowerDistributionPanel(RobotMap.PDP.PDP_ID);
    }

    public void brownout(){
        if(getVoltage() <= Constants.PDP.BROWOUT_VOLTAGE){
            
        }
    }
    
    public double getVoltage(){
        return pdp.getVoltage();
    }

    public double getTemp(){
        return pdp.getTemperature();
    }

    public double getTotalCurrent(){
        return pdp.getTotalCurrent();
    }

    public double getPower(){
        return pdp.getTotalPower();
    }

    public double getEnergy(){
        return pdp.getTotalEnergy();
    }
}