package org.frc5687.swerve.util;

import org.frc5687.swerve.Constants;
import org.frc5687.swerve.RobotMap;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class PDP {

    PowerDistributionPanel pdp;

    public PDP(){
        pdp = new PowerDistributionPanel(RobotMap.PDP.PDP_ID);
    }

    public boolean brownout(){
        //Detect a brownout
        if(getVoltage() <= Constants.PDP.BROWOUT_VOLTAGE){
            //There is a brownout
            return true;
        }
        else{
            //There is no brownout
            return false;
        }
    }

    public void getCurrent(){
        //Get the currents for each port
        SmartDashboard.putNumber("PDP/Current0", pdp.getCurrent(0));
        SmartDashboard.putNumber("PDP/Current1", pdp.getCurrent(1));
        SmartDashboard.putNumber("PDP/Current2", pdp.getCurrent(2));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(3));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(4));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(5));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(6));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(7));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(8));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(9));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(10));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(11));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(12));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(13));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(14));
        SmartDashboard.putNumber("PDP/Current3", pdp.getCurrent(15));
    }

    public boolean checkChannels(){
        for(int i = 0; i < 15; i++){
            if(excessiveCurrent(i) != true){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    public boolean excessiveCurrent(int channel){
        //0-3 12-15
        for(int start = 12; start < 15; start++){
            if(pdp.getCurrent(start) >= Constants.PDP.CURRENT_THRES){
                //If the currents exceds what ever
                return true;
            }
            else{
                return false;
            }
        }
        for(int i = 0; i < 3; i++){
            if(pdp.getCurrent(i) >= Constants.PDP.CURRENT_THRES){
                //If the currents exceds what ever
                return true;
            }
            else{
                return false;
            }
        }
        return false;
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