/***
 * This kills all functions on the robot, it's still enable and can still move
 * Written By Gabriel Tower Dec 9 2021
 */

package org.frc5687.swerve.commands;

import org.frc5687.swerve.subsystems.DriveTrain;

public class KillAll extends OutliersCommand{

    DriveTrain driveTrain;
    private boolean finished = false;

    public KillAll(DriveTrain _driveTrain){
        driveTrain = _driveTrain;
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
