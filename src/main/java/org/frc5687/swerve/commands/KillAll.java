/***
 * This kills all functions on the robot, it's still enable and can still move
 * Written By Gabriel Tower Dec 9 2021
 */
package org.frc5687.swerve.commands;

import org.frc5687.swerve.RobotMap;
import org.frc5687.swerve.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class KillAll extends OutliersCommand{
    
    private PowerDistributionPanel pdp;
    private DriveTrain driveTrain;
    private boolean finished = false;

    public KillAll(DriveTrain _driveTrain, PowerDistributionPanel pdp){
        pdp = new PowerDistributionPanel(RobotMap.PDP.PDP_ID);
    }

    @Override
    public void initialize(){
        error("Killing");
        driveTrain.startModules();
        finished = true;
    }

    @Override
    public boolean isFinished(){
        return finished;
    }

    @Override
    public void end(boolean interputed){
        error("End kill all");
    }
}
